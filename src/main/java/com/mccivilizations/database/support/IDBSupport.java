package com.mccivilizations.database.support;

import javax.sql.DataSource;

public interface IDBSupport {
    void initializeDriver() throws ClassNotFoundException;

    DataSource createDataSource(String connectionInfo);

    String getPathToMigrations();

    String getName();
}
