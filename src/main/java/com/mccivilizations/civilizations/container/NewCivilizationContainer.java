package com.mccivilizations.civilizations.container;

import com.mccivilizations.civilizations.content.CivContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class NewCivilizationContainer extends Container {
    private final BlockPos bannerPos;

    public NewCivilizationContainer(int id, BlockPos bannerPos) {
        super(CivContainers.NEW_CIVILIZATION.get(), id);
        this.bannerPos = bannerPos;
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity player) {
        return true;
    }

    public BlockPos getBannerPos() {
        return bannerPos;
    }
}
