package com.mccivilizations.civilizations.block;

import com.mccivilizations.civilizations.tileentity.TileEntityCityMarker;
import com.teamacronymcoders.base.blocks.BlockTEBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockCityMarker extends BlockTEBase<TileEntityCityMarker> {
    public BlockCityMarker() {
        super(Material.IRON, "city_marker");
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState blockState) {
        return new TileEntityCityMarker();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityCityMarker.class;
    }
}
