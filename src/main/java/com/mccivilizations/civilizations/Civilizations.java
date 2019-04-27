package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.database.IDatabaseClient;
import com.mccivilizations.civilizations.block.BlockCivilizationMarker;
import com.mccivilizations.civilizations.database.DBConfig;
import com.mccivilizations.civilizations.database.Database;
import com.mccivilizations.civilizations.database.DatabaseClient;
import com.mccivilizations.civilizations.proxy.IProxy;
import com.mccivilizations.civilizations.repository.RepositoryHolder;
import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

import java.io.File;

@Mod(modid = Civilizations.MODID, name = Civilizations.NAME, version = Civilizations.VERSION)
public class Civilizations extends BaseModFoundation<Civilizations> {
    public static final String MODID = "civilizations";
    public static final String NAME = "Civilizations";
    public static final String VERSION = "##VERSION##";

    @Instance
    public static Civilizations instance;

    @SidedProxy(clientSide = "com.mccivilizations.civilizations.proxy.ClientProxy",
            serverSide = "com.mccivilizations.civilizations.proxy.ServerProxy")
    public static IProxy proxy;

    public Civilizations() {
        super(MODID, NAME, VERSION, null);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Database.initializeImplementations(event.getAsmData());
        super.preInit(event);
        CivilizationsAPI.getInstance().setRepositoryHolder(new RepositoryHolder());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        IDatabaseClient databaseClient = new DatabaseClient(Database.create(DBConfig.databaseType,
                handleConnectionReplace(proxy.getSaveFolder(event.getServer()), DBConfig.connectionInfo),
                this.getLogger().getLogger()));
        CivilizationsAPI.getInstance().setDatabaseClient(databaseClient);
    }

    private String handleConnectionReplace(File mcWorldDirect, String connectionInfo) {
        connectionInfo = connectionInfo.replace("${minecraft}",
                mcWorldDirect.getAbsolutePath());
        return connectionInfo;
    }

    @EventHandler
    public void serverStopped(FMLServerStoppingEvent event) {
        try {
            CivilizationsAPI.getInstance().getDatabaseClient().close();
        } catch (Exception e) {
            this.getLogger().getLogger().error("Failed to Close Database", e);
        }
        CivilizationsAPI.getInstance().setDatabaseClient(null);
    }

    @Override
    public void registerBlocks(BlockRegistry registry) {
        registry.register(new BlockCivilizationMarker());
    }

    @Override
    public Civilizations getInstance() {
        return instance;
    }
}