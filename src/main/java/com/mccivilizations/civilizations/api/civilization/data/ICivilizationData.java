package com.mccivilizations.civilizations.api.civilization.data;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public interface ICivilizationData extends INBTSerializable<CompoundNBT> {
    Civilization get(UUID uuid);

    Civilization get(PlayerEntity playerEntity);

    void addPlayerTo(PlayerEntity playerEntity, Civilization civilization);

    void removePlayerFrom(PlayerEntity playerEntity, Civilization civilization);
}
