package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.database.Database;
import com.mccivilizations.civilizations.proxy.IProxy;
import com.teamacronymcoders.base.BaseModFoundation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

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
    }

    @Override
    public void beforeModuleHandlerInit(FMLPreInitializationEvent event) {
        proxy.setupDatabase();
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
    public void serverStopped(FMLServerStoppingEvent event) {
        try {
            proxy.getDatabase().close();
        } catch (Exception e) {
            this.getLogger().getLogger().error("Failed to Close Database", e);
        }
    }

    @Override
    public Civilizations getInstance() {
        return instance;
    }
}