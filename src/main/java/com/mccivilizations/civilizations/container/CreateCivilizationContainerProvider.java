package com.mccivilizations.civilizations.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class CreateCivilizationContainerProvider implements INamedContainerProvider {
    private final CompoundNBT flagPattern;

    public CreateCivilizationContainerProvider(CompoundNBT flagPattern) {
        this.flagPattern = flagPattern;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new CreateCivilizationContainer(windowId, flagPattern);
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container.civilizations.create_civilization")
                .applyTextStyle(TextFormatting.BLACK);
    }
}
