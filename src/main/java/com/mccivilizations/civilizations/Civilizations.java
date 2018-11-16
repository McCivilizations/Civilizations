package com.mccivilizations.civilizations;

import com.teamacronymcoders.base.BaseModFoundation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
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

    public Civilizations() {
        super(MODID, NAME, VERSION, null);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

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