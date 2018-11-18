package com.mccivilizations.civilizations.proxy;

import com.mccivilizations.civilizations.database.ClientDatabase;
import com.mccivilizations.civilizations.database.IDatabase;

public class ClientProxy implements IProxy {
    private final IDatabase database = new ClientDatabase();

    @Override
    public void setupDatabase() {

    }

    @Override
    public IDatabase getDatabase() {
        return database;
    }
}
