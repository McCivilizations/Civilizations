package com.mccivilizations.civilizations.api.civilization.capability;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.Unknown;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import net.minecraft.nbt.NBTTagString;

public class CivilizationHolder implements ICivilizationHolder {
    private Civilization civilization = Unknown.getCivilization();

    @Override
    public Civilization getCivilization() {
        return civilization;
    }

    @Override
    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    @Override
    public NBTTagString serializeNBT() {
        return new NBTTagString(civilization.getName());
    }

    @Override
    public void deserializeNBT(NBTTagString nbt) {
        CivilizationsAPI.getInstance().getCivilizationHandler()
                .getCivilizationByName(nbt.getString(), this::setCivilization);
    }
}
