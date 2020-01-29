package com.mccivilizations.civilizations.database;

import com.mccivilizations.civilizations.Civilizations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DatabaseClient extends Thread implements AutoCloseable {
    private final ExecutorService service;
    private final Database database;

    public DatabaseClient(final Database database) {
        this.database = database;
        this.service = Executors.newSingleThreadExecutor();
    }

    @Override
    public void close() throws InterruptedException {
        Civilizations.LOGGER.warn("Attempting to Close Database");
        service.awaitTermination(1, TimeUnit.MINUTES);
        Civilizations.LOGGER.warn("Finished Closing Database");
    }
}
