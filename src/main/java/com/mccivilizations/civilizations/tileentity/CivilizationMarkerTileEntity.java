package com.mccivilizations.civilizations.tileentity;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.content.CivBlocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

import java.util.Objects;
import java.util.concurrent.Future;

public class CivilizationMarkerTileEntity extends TileEntity {
    private Future<Civilization> civilization;
    private Long civilizationId;

    public CivilizationMarkerTileEntity() {
        super(Objects.requireNonNull(CivBlocks.MARKER_TYPE.get()));
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = super.serializeNBT();
        if (civilizationId != null) {
            nbt.putLong("civilizationId", civilizationId);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("civilizationId")) {
            civilizationId = nbt.getLong("civilizationId");
        }
    }
}
