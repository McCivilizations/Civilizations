package com.mccivilizations.civilizations.database.specific;

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
        dataSource.setUrl("jdbc:sqlite:" + connectionInfo);
        return dataSource;
    }

    @Override
    public String getPathToMigrations() {
        return "classpath:assets/civilizations/database/migrations/sqlite";
    }
}
