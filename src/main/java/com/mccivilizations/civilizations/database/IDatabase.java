package com.mccivilizations.civilizations.database;

import com.mccivilizations.civilizations.database.operation.ISQLOperation;

public interface IDatabase extends AutoCloseable {
    void submitOperation(ISQLOperation operation);
}
