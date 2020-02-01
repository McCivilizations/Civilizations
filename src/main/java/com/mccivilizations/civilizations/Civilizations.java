package com.mccivilizations.civilizations;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.citizen.Citizen;
import com.mccivilizations.civilizations.api.citizen.ICitizen;
import com.mccivilizations.civilizations.content.CivContainers;
import com.mccivilizations.civilizations.content.CivEnchants;
import com.mccivilizations.civilizations.network.NetworkHandler;
import com.mccivilizations.civilizations.network.NewCivilizationPacket;
import com.mccivilizations.civilizations.repository.civilization.CivilizationRepository;
import com.mccivilizations.civilizations.screen.NewCivilizationScreen;
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
        networkHandler.register(NewCivilizationPacket.class, NewCivilizationPacket::encode,
                NewCivilizationPacket::decode, NewCivilizationPacket::handle);

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
                            .getCivilizationById(((LongNBT) nbt).getLong())
                            .thenAcceptAsync(civilization -> civilization.ifPresent(instance::setCivilization));
                }
            }
        }, Citizen::new);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(CivContainers.NEW_CIVILIZATION.get(), NewCivilizationScreen::new);
    }
}