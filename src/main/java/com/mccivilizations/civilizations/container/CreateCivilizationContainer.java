package com.mccivilizations.civilizations.container;

import com.mccivilizations.civilizations.content.CivContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class CreateCivilizationContainer extends Container {
    private final CompoundNBT flagPattern;

    public CreateCivilizationContainer(int id, CompoundNBT flagPattern) {
        super(CivContainers.CREATE_CIVILIZATION.get(), id);
        this.flagPattern = flagPattern;
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity player) {
        return true;
    }

    public CompoundNBT getFlagPattern() {
        return flagPattern;
    }
}
