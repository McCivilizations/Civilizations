package com.mccivilizations.civilizations.api.civilization.capability;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICivilizationHolder extends INBTSerializable<NBTTagString> {
    Civilization getCivilization();

    void setCivilization(Civilization civilization);
}
