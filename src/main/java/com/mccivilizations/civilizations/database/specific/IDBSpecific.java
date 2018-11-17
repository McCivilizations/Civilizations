package com.mccivilizations.civilizations.database.specific;

import javax.sql.DataSource;

public interface IDBSpecific {
    void initializeDriver();

    DataSource createDataSource(String connectionInfo);
}
