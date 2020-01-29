package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.content.CivBlocks;
import com.mccivilizations.civilizations.database.DBConfig;
import com.mccivilizations.civilizations.database.Database;
import com.mccivilizations.civilizations.database.DatabaseClient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(Civilizations.MODID)
public class Civilizations {
    public static final String MODID = "civilizations";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static Civilizations instance;

    public Civilizations() {
        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CivBlocks.register(modEventBus);


    }

    public void serverStarting(FMLServerStartingEvent event) {
        IDatabaseClient databaseClient = new DatabaseClient(Database.create(DBConfig.databaseType,
                handleConnectionReplace(event.getServer().getDataDirectory(), DBConfig.connectionInfo),
                LOGGER));
        CivilizationsAPI.getInstance().setDatabaseClient(databaseClient);
    }

    private String handleConnectionReplace(File mcWorldDirect, String connectionInfo) {
        connectionInfo = connectionInfo.replace("${minecraft}",
                mcWorldDirect.getAbsolutePath());
        return connectionInfo;
    }

    public void serverStopped(FMLServerStoppingEvent event) {
        try {
            CivilizationsAPI.getInstance().getDatabaseClient().close();
        } catch (Exception e) {
            LOGGER.error("Failed to Close Database", e);
        }
        CivilizationsAPI.getInstance().setDatabaseClient(null);
    }
}