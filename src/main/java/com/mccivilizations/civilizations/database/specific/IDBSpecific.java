package com.mccivilizations.civilizations.database.specific;

import javax.sql.DataSource;

public interface IDBSpecific {
    void initializeDriver() throws ClassNotFoundException;

    DataSource createDataSource(String connectionInfo);

    String getPathToMigrations();
}
