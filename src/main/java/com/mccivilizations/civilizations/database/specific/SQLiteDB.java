package com.mccivilizations.civilizations.database.specific;

import com.mccivilizations.civilizations.Civilizations;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import javax.sql.DataSource;

@DBSpecific("SQLite")
public class SQLiteDB implements IDBSpecific {
    @Override
    public void initializeDriver() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    @Override
    public DataSource createDataSource(String connectionInfo) {
        SQLiteConnectionPoolDataSource dataSource = new SQLiteConnectionPoolDataSource();
        connectionInfo = connectionInfo.replace("${minecraft}",
                Civilizations.instance.getMinecraftFolder().getAbsolutePath());
        dataSource.setUrl("jdbc:sqlite:" + connectionInfo);
        return dataSource;
    }

    @Override
    public String getPathToMigrations() {
        return "classpath:assets/civilizations/database/migrations/sqlite";
    }
}
