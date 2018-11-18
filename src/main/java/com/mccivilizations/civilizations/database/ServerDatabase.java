package com.mccivilizations.civilizations.database;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.database.operation.ISQLOperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerDatabase extends Thread implements IDatabase {
    private final ExecutorService service;
    private final Database database;

    public ServerDatabase(final Database database) {
        this.database = database;
        this.service = Executors.newSingleThreadExecutor();
    }

    @Override
    public void submitOperation(ISQLOperation operation) {
        service.submit(() -> operation.handle(database));
    }

    @Override
    public void close() throws InterruptedException {
        Civilizations.instance.getLogger().warning("Attempting to Close Database");
        service.awaitTermination(1, TimeUnit.MINUTES);
        Civilizations.instance.getLogger().warning("Finished Closing Database");
    }


}
