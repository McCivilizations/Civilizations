package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.content.CivBlocks;
import com.mccivilizations.civilizations.repository.civilization.ClientCivilizationRepository;
import com.mccivilizations.civilizations.repository.civilization.ServerCivilizationRepository;
import com.mccivilizations.database.Database;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;
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

        CivilizationsAPI.getInstance().setCivilizationRepository(
                DistExecutor.runForDist(() -> ClientCivilizationRepository::new, () -> ServerCivilizationRepository::new)
        );
    }
}