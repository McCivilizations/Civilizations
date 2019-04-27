package com.mccivilizations.civilizations.block;

import com.mccivilizations.civilizations.tileentity.TileEntityCivilizationMarker;
import com.teamacronymcoders.base.blocks.BlockTEBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockCivilizationMarker extends BlockTEBase<TileEntityCivilizationMarker> {
    public BlockCivilizationMarker() {
        super(Material.IRON, "civilization_marker");
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState blockState) {
        return new TileEntityCivilizationMarker();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityCivilizationMarker.class;
    }
}
