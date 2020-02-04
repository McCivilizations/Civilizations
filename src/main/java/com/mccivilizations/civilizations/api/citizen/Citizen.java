package com.mccivilizations.civilizations.api.citizen;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class Citizen implements ICitizen, INBTSerializable<CompoundNBT> {
    private Civilization civilization;

    @Override
    public Civilization getCivilization() {
        return civilization;
    }

    @Override
    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        if (civilization != null) {
            nbt.putLong("id", civilization.getId());
            nbt.putString("name", civilization.getName());
            nbt.putString("isoCode", civilization.getIsoCode());
            nbt.putString("teamName", civilization.getTeamName());
            nbt.put("flag", civilization.getFlagPattern());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("id")) {
            this.civilization = new Civilization(
                    nbt.getLong("id"),
                    nbt.getString("name"),
                    nbt.getString("isoCode"),
                    nbt.getString("teamName"),
                    nbt.getCompound("flag")
            );
            CivilizationsAPI.getInstance()
                    .getCivilizationRepository()
                    .getById(nbt.getLong("id"))
                    .thenAcceptAsync(value -> civilization = value.orElse(null));
        }
    }
}
