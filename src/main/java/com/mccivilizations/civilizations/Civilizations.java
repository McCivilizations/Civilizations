package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.block.BlockCityMarker;
import com.mccivilizations.civilizations.civilization.network.CivilizationRequest;
import com.mccivilizations.civilizations.civilization.network.CivilizationResponse;
import com.mccivilizations.civilizations.network.reactive.ReactiveNetwork;
import com.mccivilizations.civilizations.proxy.IProxy;
import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Civilizations.MODID, name = Civilizations.NAME, version = Civilizations.VERSION)
public class Civilizations extends BaseModFoundation<Civilizations> {
    public static final String MODID = "civilizations";
    public static final String NAME = "Civilizations";
    public static final String VERSION = "##VERSION##";

    @Mod.Instance
    public static Civilizations INSTANCE;

    @SidedProxy(clientSide = "com.mccivilizations.civilizations.proxy.ClientProxy",
            serverSide = "com.mccivilizations.civilizations.proxy.ServerProxy")
    public static IProxy proxy;

    private ReactiveNetwork<> civilizationNetwork;

    public Civilizations() {
        super(MODID, NAME, VERSION, null);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        civilizationNetwork = new ReactiveNetwork<>(this, CivilizationRequest.class,
                CivilizationRequest::new, CivilizationResponse.class, true);
        CivilizationsAPI.createInstance(proxy.getCivilizationHandler(), proxy.getLocalizationHandler());
        super.preInit(event);

    }


    @Override
    public void registerBlocks(BlockRegistry registry) {
        registry.register(new BlockCityMarker());
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

    public ReactiveNetwork getCivilizationNetwork() {
        return civilizationNetwork;
    }
}