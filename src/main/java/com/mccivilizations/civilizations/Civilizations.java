package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.network.reactive.ReactiveNetwork;
import com.mccivilizations.civilizations.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

@Mod(modid = Civilizations.MODID, name = Civilizations.NAME, version = Civilizations.VERSION)
public class Civilizations {
    public static final String MODID = "civilizations";
    public static final String NAME = "Civilizations";
    public static final String VERSION = "1.0";

    @Mod.Instance
    public static Civilizations INSTANCE;

    @SidedProxy(clientSide = "com.mccivilizations.civilizations.proxy.ClientProxy",
            serverSide = "com.mccivilizations.civilizations.proxy.ServerProxy")
    public static IProxy proxy;
    public static Logger logger;

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        CivilizationsAPI.createInstance(proxy.getCivilizationHandler(), proxy.getLocalizationHandler());
        ReactiveNetwork.setup(event.getAsmData());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}