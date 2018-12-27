package com.mccivilizations.civilizations.api.database;

import com.mccivilizations.civilizations.database.operation.ISQLOperation;

public interface IDatabaseClient extends AutoCloseable {
    void submitOperation(ISQLOperation operation);
}
