package com.mccivilizations.civilizations.database;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.api.database.IDatabaseClient;
import com.mccivilizations.civilizations.database.operation.ISQLOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DatabaseClient extends Thread implements AutoCloseable, IDatabaseClient {
    private final ExecutorService service;
    private final Database database;

    public DatabaseClient(final Database database) {
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
