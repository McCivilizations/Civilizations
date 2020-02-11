package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.civilization.data.CivilizationData;
import com.mccivilizations.civilizations.api.civilization.data.ICivilizationData;
import com.mccivilizations.civilizations.content.CivContainers;
import com.mccivilizations.civilizations.content.CivEnchants;
import com.mccivilizations.civilizations.nbt.NBTStorage;
import com.mccivilizations.civilizations.network.CreateCivilizationPacket;
import com.mccivilizations.civilizations.network.LeaveCivilizationPacket;
import com.mccivilizations.civilizations.network.NetworkHandler;
import com.mccivilizations.civilizations.screen.CreateCivilizationScreen;
import com.mccivilizations.civilizations.screen.ManageCivilizationScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Civilizations.ID)
public class Civilizations {
    public static final String ID = "civilizations";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static Civilizations instance;

    public NetworkHandler networkHandler;

    public Civilizations() {
        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CivEnchants.register(modEventBus);
        CivContainers.register(modEventBus);

        networkHandler = new NetworkHandler();
        networkHandler.register(CreateCivilizationPacket.class, CreateCivilizationPacket::encode,
                CreateCivilizationPacket::decode, CreateCivilizationPacket::handle);
        networkHandler.register(LeaveCivilizationPacket.class, LeaveCivilizationPacket::encode,
                LeaveCivilizationPacket::decode, LeaveCivilizationPacket::handle);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ICivilizationData.class, NBTStorage.ofCompound(), CivilizationData::new);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(CivContainers.CREATE_CIVILIZATION.get(), CreateCivilizationScreen::new);
        ScreenManager.registerFactory(CivContainers.MANAGE_CIVILIZATION.get(), ManageCivilizationScreen::new);

    }
}