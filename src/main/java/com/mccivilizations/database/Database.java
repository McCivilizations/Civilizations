package com.mccivilizations.database;

import com.mccivilizations.database.event.GatherDatabaseSupportEvent;
import com.mccivilizations.database.support.IDBSupport;
import com.mccivilizations.database.support.SQLiteDB;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Database implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger(Database.class);

    private static List<IDBSupport> databaseSupport;
    private static Database instance;
    private static boolean setup;

    private final DataSource dataSource;
    private final QueryRunner queryRunner;

    public Database(DataSource dataSource) {
        this.dataSource = dataSource;
        this.queryRunner = new QueryRunner(dataSource);
    }

    public CompletableFuture<Void> insert(String sql, Object... args) {
        return CompletableFuture.runAsync(() -> {
            try {
                this.queryRunner.update(sql, args);
            } catch (SQLException e) {
                LOGGER.error("Failed to run sql: " + sql, e);
            }
        });
    }

    public CompletableFuture<Integer> update(String sql, Object... args) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return this.queryRunner.update(sql, args);
            } catch (SQLException e) {
                LOGGER.error("Failed to run sql: " + sql);
                return 0;
            }
        });
    }

    public <T> CompletableFuture<Optional<T>> query(String sql, ResultSetHandler<T> handler, Object... args) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return Optional.ofNullable(this.queryRunner.query(sql, handler, args));
            } catch (SQLException e) {
                LOGGER.error("Failed to Query: " + sql, e);
                return Optional.empty();
            }
        });
    }

    @Override
    public void close() throws SQLException {
        dataSource.getConnection().close();
    }

    public static Database getInstance() {
        if (!setup) {
            throw new IllegalStateException("Called getInstance() without setup");
        }
        if (instance == null) {
            throw new IllegalStateException("No Active Database");
        }
        return instance;
    }

    private static Database createDatabase(MinecraftServer server) {
        IDBSupport dbSupport = databaseSupport.stream()
                .filter(support -> support.getName().equals(DBConfig.instance.databaseType.get()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Failed to Find Database Support for: " + DBConfig.instance.databaseType.get()));
        try {
            dbSupport.initializeDriver();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Failed to Initialize Database Driver");
        }

        DataSource dataSource = dbSupport.createDataSource(handleConnectionReplace(server,
                DBConfig.instance.connectionInfo.get()));

        Flyway.configure()
                .dataSource(dataSource)
                .locations(new Location(dbSupport.getPathToMigrations()))
                .load()
                .migrate();

        return new Database(dataSource);
    }

    private static String handleConnectionReplace(MinecraftServer server, String connectionInfo) {
        connectionInfo = connectionInfo.replace("${world_folder}",
                server.getActiveAnvilConverter()
                        .getFile(server.getFolderName(), ".")
                        .getParentFile()
                        .getAbsolutePath());
        return connectionInfo;
    }

    public static void setup() {
        if (!setup) {
            MinecraftForge.EVENT_BUS.addListener(Database::handleServerStart);
            MinecraftForge.EVENT_BUS.addListener(Database::handleServerStop);
            ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, DBConfig.initialize());
            GatherDatabaseSupportEvent event = new GatherDatabaseSupportEvent();
            event.addDatabaseSupport(new SQLiteDB());
            MinecraftForge.EVENT_BUS.post(event);
            databaseSupport = event.getDatabaseSupport();
            setup = true;
        }
    }

    private static void handleServerStart(FMLServerAboutToStartEvent event) {
        if (instance != null) {
            closeDatabase();
        }
        instance = createDatabase(event.getServer());
    }

    private static void handleServerStop(FMLServerStoppingEvent event) {
        closeDatabase();
    }

    private static void closeDatabase() {
        try {
            instance.close();
            instance = null;
        } catch (SQLException exception) {
            LOGGER.error("Failed to close Database", exception);
        }
    }
}
