package com.mccivilizations.civilizations.content;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.container.ManageCivilizationContainer;
import com.mccivilizations.civilizations.container.CreateCivilizationContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CivContainers {
    private static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(
            ForgeRegistries.CONTAINERS, Civilizations.ID);

    public static final RegistryObject<ContainerType<CreateCivilizationContainer>> CREATE_CIVILIZATION = CONTAINER_TYPES.register(
            "create_civilization", () -> IForgeContainerType.create((windowId, inv, data) ->
                    new CreateCivilizationContainer(windowId, data.readCompoundTag())));
    public static final RegistryObject<ContainerType<ManageCivilizationContainer>> MANAGE_CIVILIZATION = CONTAINER_TYPES.register(
            "manage_civilization", () -> IForgeContainerType.create((windowId, inv, data) ->
                    new ManageCivilizationContainer(windowId)));

    public static void register(IEventBus eventBus) {
        CONTAINER_TYPES.register(eventBus);
    }
}
