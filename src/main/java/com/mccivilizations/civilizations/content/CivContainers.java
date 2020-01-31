package com.mccivilizations.civilizations.content;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.container.NewCivilizationContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CivContainers {
    private static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(
            ForgeRegistries.CONTAINERS, Civilizations.MODID);

    public static final RegistryObject<ContainerType<NewCivilizationContainer>> NEW_CIVILIZATION = CONTAINER_TYPES.register(
            "new_civilization", () -> IForgeContainerType.create((windowId, inv, data) ->
                    new NewCivilizationContainer(windowId, data.readBlockPos())));

    public static void register(IEventBus eventBus) {
        CONTAINER_TYPES.register(eventBus);
    }
}
