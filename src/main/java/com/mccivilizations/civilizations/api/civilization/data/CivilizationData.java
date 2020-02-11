package com.mccivilizations.civilizations.api.civilization.data;

import com.google.common.collect.Maps;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.Map;
import java.util.UUID;

public class CivilizationData implements ICivilizationData {
    private final Map<UUID, Civilization> civilizationLookup;
    private final Map<UUID, Civilization> playerCivilizationLookup;

    public CivilizationData() {
        this.civilizationLookup = Maps.newHashMap();
        this.playerCivilizationLookup = Maps.newHashMap();
    }

    @Override
    public Civilization get(UUID uuid) {
        return civilizationLookup.get(uuid);
    }

    @Override
    public Civilization get(PlayerEntity playerEntity) {
        return playerCivilizationLookup.get(playerEntity.getUniqueID());
    }

    @Override
    public void addPlayerTo(PlayerEntity playerEntity, Civilization civilization) {
        if (!civilizationLookup.containsKey(civilization.getUniqueId())) {
            civilizationLookup.put(civilization.getUniqueId(), civilization);
        }
        playerCivilizationLookup.put(playerEntity.getUniqueID(), civilization);
    }

    @Override
    public void removePlayerFrom(PlayerEntity playerEntity, Civilization civilization) {
        playerCivilizationLookup.remove(playerEntity.getUniqueID());
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        ListNBT civilizationData = new ListNBT();
        for (Civilization civilization: civilizationLookup.values()) {
            civilizationData.add(civilization.serializeNBT());
        }
        nbt.put("civilizations", civilizationData);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
