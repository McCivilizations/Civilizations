package com.mccivilizations.civilizations.api.civilization.data;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CivilizationDataCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    private final ICivilizationData data;
    private final LazyOptional<ICivilizationData> lazyData;

    public CivilizationDataCapabilityProvider() {
        this.data = new CivilizationData();
        this.lazyData = LazyOptional.of(() -> data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CivilizationsAPI.CIV_DATA ? lazyData.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return data.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        data.deserializeNBT(nbt);
    }
}
