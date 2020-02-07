package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.citizen.Citizen;
import com.mccivilizations.civilizations.api.citizen.ICitizen;
import com.mccivilizations.civilizations.content.CivContainers;
import com.mccivilizations.civilizations.content.CivEnchants;
import com.mccivilizations.civilizations.network.CreateCivilizationPacket;
import com.mccivilizations.civilizations.network.LeaveCivilizationPacket;
import com.mccivilizations.civilizations.network.NetworkHandler;
import com.mccivilizations.civilizations.repository.civilization.CivilizationRepository;
import com.mccivilizations.civilizations.screen.ManageCivilizationScreen;
import com.mccivilizations.civilizations.screen.CreateCivilizationScreen;
import com.mccivilizations.database.Database;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

@Mod(Civilizations.MODID)
public class Civilizations {
    public static final String MODID = "civilizations";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static Civilizations instance;

    public NetworkHandler networkHandler;

    public Civilizations() {
        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CivEnchants.register(modEventBus);
        CivContainers.register(modEventBus);

        Database.setup();

        CivilizationsAPI.getInstance().setCivilizationRepository(new CivilizationRepository());

        networkHandler = new NetworkHandler();
        networkHandler.register(CreateCivilizationPacket.class, CreateCivilizationPacket::encode,
                CreateCivilizationPacket::decode, CreateCivilizationPacket::handle);
        networkHandler.register(LeaveCivilizationPacket.class, LeaveCivilizationPacket::encode,
                LeaveCivilizationPacket::decode, LeaveCivilizationPacket::handle);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ICitizen.class, new Capability.IStorage<ICitizen>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<ICitizen> capability, ICitizen instance, Direction side) {
                if (instance.getCivilization() != null) {
                    return LongNBT.valueOf(instance.getCivilization().getId());
                }
                return null;
            }

            @Override
            public void readNBT(Capability<ICitizen> capability, ICitizen instance, Direction side, INBT nbt) {
                if (nbt instanceof LongNBT && ((LongNBT) nbt).getLong() > 0) {
                    CivilizationsAPI.getInstance().getCivilizationRepository()
                            .getById(((LongNBT) nbt).getLong())
                            .thenAcceptAsync(civilization -> civilization.ifPresent(instance::setCivilization));
                }
            }
        }, Citizen::new);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(CivContainers.CREATE_CIVILIZATION.get(), CreateCivilizationScreen::new);
        ScreenManager.registerFactory(CivContainers.MANAGE_CIVILIZATION.get(), ManageCivilizationScreen::new);

    }
}