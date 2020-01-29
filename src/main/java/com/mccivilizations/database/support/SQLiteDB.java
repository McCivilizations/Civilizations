package com.mccivilizations.database.support;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import javax.sql.DataSource;

public class SQLiteDB implements IDBSupport {
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
        return "classpath:migrations/civilizations/sqlite";
    }

    @Override
    public String getName() {
        return "SQLite";
    }
}
