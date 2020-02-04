package com.mccivilizations.civilizations.api.citizen;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.LongNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class CitizenCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {
    private final Citizen citizen;
    private final LazyOptional<ICitizen> citizenLazyOptional;

    public CitizenCapabilityProvider() {
        citizen = new Citizen();
        citizenLazyOptional = LazyOptional.of(() -> citizen);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CivilizationsAPI.CITIZEN_CAP ? citizenLazyOptional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return citizen.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        citizen.deserializeNBT(nbt);
    }
}
