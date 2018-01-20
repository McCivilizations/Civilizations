package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.network.reactive.ReactiveNetwork;
import com.mccivilizations.civilizations.proxy.IProxy;
import com.teamacronymcoders.base.BaseModFoundation;
import net.minecraft.creativetab.CreativeTabs;
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
public class Civilizations extends BaseModFoundation<Civilizations> {
    public static final String MODID = "civilizations";
    public static final String NAME = "Civilizations";
    public static final String VERSION = "1.0";

    @Mod.Instance
    public static Civilizations INSTANCE;

    @SidedProxy(clientSide = "com.mccivilizations.civilizations.proxy.ClientProxy",
            serverSide = "com.mccivilizations.civilizations.proxy.ServerProxy")
    public static IProxy proxy;
    public static Logger logger;

    public Civilizations() {
        super(MODID, NAME, VERSION, null);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CivilizationsAPI.createInstance(proxy.getCivilizationHandler(), proxy.getLocalizationHandler());
        super.preInit(event);

        ReactiveNetwork.setup(event.getAsmData());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public Civilizations getInstance() {
        return INSTANCE;
    }
}