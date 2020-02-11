package com.mccivilizations.civilizations.api.civilization.data;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.leadership.Role;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CivilizationData implements ICivilizationData {
    private final Map<UUID, Civilization> civilizationLookUp;
    private final Table<UUID, UUID, Role> roleLookUp;
    private final Supplier<Scoreboard> scoreboardSupplier;

    public CivilizationData() {
        this(() -> null);
    }
    public CivilizationData(Supplier<Scoreboard> scoreboardSupplier) {
        this.civilizationLookUp = Maps.newHashMap();
        this.roleLookUp = HashBasedTable.create();
        this.scoreboardSupplier = scoreboardSupplier;
    }

    @Override
    public Civilization get(UUID uuid) {
        return civilizationLookUp.get(uuid);
    }

    @Override
    public List<Pair<Role, Civilization>> get(Entity entity) {
        return roleLookUp.column(entity.getUniqueID())
                .entrySet()
                .stream()
                .map(entry -> Pair.of(entry.getValue(), this.get(entry.getKey())))
                .collect(Collectors.toList());
    }

    @Override
    public void add(Civilization civilization) {
        civilizationLookUp.put(civilization.getUniqueId(), civilization);
        Optional.ofNullable(this.scoreboardSupplier.get())
                .ifPresent(scoreboard -> scoreboard.createTeam(civilization.getTeam()));
    }

    @Override
    public void addTo(Entity entity, Civilization civilization) {
        if (!civilizationLookUp.containsKey(civilization.getUniqueId())) {
            this.add(civilization);
        }
        roleLookUp.put(entity.getUniqueID(), civilization.getUniqueId(), new Role("citizen", Collections.emptyList()));

    }

    @Override
    public void removeFrom(Entity playerEntity, Civilization civilization) {

    }

    @Override
    public Role getRole(UUID uniqueId, Civilization civilization) {
        return roleLookUp.get(uniqueId, civilization.getUniqueId());
    }

    @Override
    public void setRole(UUID uniqueId, Civilization civilization) {

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        ListNBT civilizationData = new ListNBT();
        for (Civilization civilization: civilizationLookUp.values()) {
            civilizationData.add(civilization.serializeNBT());
        }
        nbt.put("civilizations", civilizationData);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        ListNBT civilizationData = nbt.getList("civilizations", 10);
        IntStream.range(0, civilizationData.size())
                .mapToObj(civilizationData::getCompound)
                .map(Civilization::load)
                .forEach(civilization -> civilizationLookUp.put(civilization.getUniqueId(), civilization));
    }
}
