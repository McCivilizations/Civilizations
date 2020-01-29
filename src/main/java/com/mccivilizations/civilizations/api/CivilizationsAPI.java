package com.mccivilizations.civilizations.api;

import com.mccivilizations.civilizations.api.database.IDatabaseClient;

import java.util.Objects;

public class CivilizationsAPI {
    private static CivilizationsAPI instance;

    public static CivilizationsAPI getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CivilizationsAPI();
        }
        return instance;
    }

    private IDatabaseClient databaseClient;

    public void setDatabaseClient(IDatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    //DO NOT CACHE THIS, IT CHANGES ON WORLD START
    public IDatabaseClient getDatabaseClient() {
        return this.databaseClient;
    }
}
