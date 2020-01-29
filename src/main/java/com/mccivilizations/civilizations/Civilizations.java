package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.content.CivBlocks;
import com.mccivilizations.database.Database;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Civilizations.MODID)
public class Civilizations {
    public static final String MODID = "civilizations";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static Civilizations instance;

    public Civilizations() {
        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CivBlocks.register(modEventBus);

        Database.setup();

        MinecraftForge.EVENT_BUS.addListener(this::onPlayerLogin);
    }

    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> Database.getInstance().insert(
                "insert into player(name, uuid) values(?, ?)", event.getPlayer().getName().getFormattedText(),
                event.getPlayer().getUniqueID().toString()
        ));
    }
}