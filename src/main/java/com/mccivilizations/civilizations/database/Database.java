package com.mccivilizations.civilizations.database;

import com.google.common.collect.Maps;
import com.mccivilizations.civilizations.database.specific.DBSpecific;
import com.mccivilizations.civilizations.database.specific.IDBSpecific;
import com.mccivilizations.civilizations.functional.attempt.Attempt;
import com.teamacronymcoders.base.util.ClassLoading;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;

import javax.sql.DataSource;
import java.util.Map;

public class Database {
    private final static Map<String, IDBSpecific> DB_IMPLEMENTATIONS = Maps.newHashMap();

    private final DataSource dataSource;

    private Database(DataSource dataSource) {
        this.dataSource = dataSource;
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(new Location("classpath:assets/civilizations/database/migrations"))
                .load();
        flyway.migrate();
    }

    public static Attempt<Database> create(String databaseName, String connectionUrl) {
        IDBSpecific db = DB_IMPLEMENTATIONS.get(databaseName);
        if (db != null) {
            return Attempt.success(new Database(db.createDataSource(connectionUrl)));
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
