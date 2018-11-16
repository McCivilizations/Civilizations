package com.mccivilizations.civilizations.api.civilization.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapCivilization {
    @CapabilityInject(ICivilizationHolder.class)
    public static Capability<ICivilizationHolder> CIVILIZATION;

    public static void register() {
        CapabilityManager.INSTANCE.register(ICivilizationHolder.class, new Capability.IStorage<ICivilizationHolder>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<ICivilizationHolder> capability, ICivilizationHolder instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<ICivilizationHolder> capability, ICivilizationHolder instance, EnumFacing side, NBTBase nbt) {

            }
        }, CivilizationHolder::new);
    }
}
