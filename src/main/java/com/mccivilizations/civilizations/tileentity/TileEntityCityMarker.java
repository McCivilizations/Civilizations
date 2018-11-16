package com.mccivilizations.civilizations.tileentity;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.teamacronymcoders.base.tileentities.TileEntityBase;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCityMarker extends TileEntityBase {

    @Override
    protected void readFromDisk(NBTTagCompound data) {

    }

    @Override
    protected NBTTagCompound writeToDisk(NBTTagCompound data) {
        return data;
    }
}
