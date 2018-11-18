package com.mccivilizations.civilizations.proxy;

import com.mccivilizations.civilizations.database.IDatabase;

public interface IProxy {
    void setupDatabase();

    IDatabase getDatabase();
}
