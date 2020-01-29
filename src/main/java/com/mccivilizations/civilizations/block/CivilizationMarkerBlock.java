package com.mccivilizations.civilizations.block;

import com.mccivilizations.civilizations.tileentity.CivilizationMarkerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

public class CivilizationMarkerBlock extends Block {
    public CivilizationMarkerBlock(Block.Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull BlockState blockState, @Nonnull IBlockReader reader) {
        return new CivilizationMarkerTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
