package com.mccivilizations.civilizations.proxy;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.database.DBConfig;
import com.mccivilizations.civilizations.database.Database;
import com.mccivilizations.civilizations.database.ServerDatabase;
import com.mccivilizations.civilizations.database.IDatabase;

public class ServerProxy implements IProxy {
    private ServerDatabase database;

    @Override
    public void setupDatabase() {
        database = new ServerDatabase(Database.create(DBConfig.databaseType,
                DBConfig.connectionInfo, Civilizations.instance.getLogger().getLogger()));
    }

    @Override
    public IDatabase getDatabase() {
        return database;
    }
}
