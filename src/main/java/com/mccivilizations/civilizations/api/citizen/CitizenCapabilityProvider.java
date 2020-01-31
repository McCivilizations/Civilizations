package com.mccivilizations.civilizations.api.citizen;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import net.minecraft.nbt.LongNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class CitizenCapabilityProvider implements ICapabilitySerializable<LongNBT> {
    private final ICitizen citizen;
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
    public LongNBT serializeNBT() {
        return Optional.ofNullable(citizen.getCivilization())
                .map(civilization -> LongNBT.valueOf(civilization.getId()))
                .orElseGet(() -> LongNBT.valueOf(-1));
    }

    @Override
    public void deserializeNBT(LongNBT nbt) {
        if (nbt.getLong() > 0) {
            CivilizationsAPI.getInstance().getCivilizationRepository()
                    .getCivilizationById(nbt.getLong())
                    .thenAcceptAsync(civilization -> civilization.ifPresent(citizen::setCivilization));
        }
    }
}
