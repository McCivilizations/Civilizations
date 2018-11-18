package com.mccivilizations.civilizations.database;

import com.google.common.collect.Maps;
import com.mccivilizations.civilizations.database.specific.DBSpecific;
import com.mccivilizations.civilizations.database.specific.IDBSpecific;
import com.mccivilizations.civilizations.functional.attempt.Attempt;
import com.teamacronymcoders.base.util.ClassLoading;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.function.Consumer;

public class Database {
    private final static Map<String, IDBSpecific> DB_IMPLEMENTATIONS = Maps.newHashMap();

    private final Logger logger;
    private final DataSource dataSource;

    private Database(DataSource dataSource, String migrationLocation, Logger logger) {
        this.dataSource = dataSource;
        this.logger = logger;
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(new Location(migrationLocation))
                .load();
        flyway.migrate();
    }

    public void create(String createStatement, Consumer<Statement> statementSetter) {
        update(createStatement, statementSetter);
    }

    public void update(String updateStatement, Consumer<Statement> statementSetter) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateStatement)) {
            statementSetter.accept(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to Update with Statement: " + updateStatement, e);
        }
    }

    public static Attempt<Database> create(String databaseName, String connectionUrl, Logger logger) {
        IDBSpecific db = DB_IMPLEMENTATIONS.get(databaseName);
        if (db != null) {
            try {
                db.initializeDriver();
                return Attempt.success(new Database(db.createDataSource(connectionUrl),
                        db.getPathToMigrations(), logger));
            } catch (ClassNotFoundException exception) {
                return Attempt.failure(new IllegalStateException("Failed to Create DataSource", exception));
            }

        } else {
            return Attempt.failure(new IllegalArgumentException("No Database found for Name" + databaseName));
        }
    }

    public static void initializeImplementations(ASMDataTable asmDataTable) {
        ClassLoading.getInstances(asmDataTable, DBSpecific.class, IDBSpecific.class, tClass -> true,
                DBSpecific::value)
                .forEach(DB_IMPLEMENTATIONS::put);
    }
}
