package com.mccivilizations.civilizations.tileentity;

import com.mccivilizations.civilizations.content.CivBlocks;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.List;
import java.util.Objects;

public class CivilizationMarkerTileEntity extends TileEntity implements ITickableTileEntity {
    private Long civilizationId;
    private DyeColor baseColor = DyeColor.WHITE;
    private ListNBT patterns;
    private boolean patternDataSet;
    private List<BannerPattern> patternList;
    private List<DyeColor> colorList;
    private String patternResourceLocation;

    public CivilizationMarkerTileEntity() {
        super(Objects.requireNonNull(CivBlocks.MARKER_TYPE.get()));
    }

    @Override
    public void tick() {

    }

    public void setPatternFromBanner(AbstractBannerBlock block, CompoundNBT compoundNBT) {
        this.patterns = compoundNBT.getList("Patterns", 10);
        this.baseColor = block.getColor();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = super.serializeNBT();
        nbt.putLong("civilizationId", civilizationId);
        nbt.putInt("baseColor", baseColor.getId());
        nbt.put("patterns", patterns);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.civilizationId = nbt.getLong("civilizationId");
        this.baseColor = DyeColor.byId(nbt.getInt("baseColor"));
        this.patterns = nbt.getList("patterns", 10);
    }

    public String getPatternResourceLocation() {
        return patternResourceLocation;
    }

    public List<BannerPattern> getPatternList() {
        return this.patternList;
    }

    public List<DyeColor> getColorList() {
        return this.colorList;
    }
}
