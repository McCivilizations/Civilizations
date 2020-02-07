package com.mccivilizations.civilizations.container;

import com.mccivilizations.civilizations.content.CivContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;

import javax.annotation.Nonnull;

public class ManageCivilizationContainer extends Container {

    public ManageCivilizationContainer(int id) {
        super(CivContainers.MANAGE_CIVILIZATION.get(), id);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity player) {
        return true;
    }
}
