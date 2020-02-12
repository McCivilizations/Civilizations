package com.mccivilizations.civilizations.api.data;

import com.google.common.collect.Maps;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class CivilizationData extends WorldSavedData {
    public static final String NAME = "civ_data";

    private final Map<UUID, Civilization> civilizationLookUp;
    private final Map<UUID, Civilization> playerLookUp;
    private final Supplier<Scoreboard> scoreboardSupplier;

    public CivilizationData() {
        this(() -> null);
    }

    public CivilizationData(Supplier<Scoreboard> scoreboardSupplier) {
        super(NAME);
        this.civilizationLookUp = Maps.newHashMap();
        this.playerLookUp = Maps.newHashMap();
        this.scoreboardSupplier = scoreboardSupplier;
    }

    public Civilization getCivilizationForEntity(Entity entity) {
        return this.getCivilizationForEntity(entity.getUniqueID());
    }

    public Civilization getCivilizationForEntity(UUID uniqueId) {
        return playerLookUp.get(uniqueId);
    }

    public void createCivilization(Civilization civilization) {
        civilizationLookUp.put(civilization.getUniqueId(), civilization);
        Optional.ofNullable(this.scoreboardSupplier.get())
                .ifPresent(scoreboard -> scoreboard.createTeam(civilization.getTeam()));
    }

    public void addEntityToCivilization(Entity entity, Civilization civilization) {
        this.addEntityToCivilization(entity.getUniqueID(), civilization);
    }

    public void addEntityToCivilization(UUID entityUniqueId, Civilization civilization) {
        if (!civilizationLookUp.containsKey(civilization.getUniqueId())) {
            this.createCivilization(civilization);
        }
        playerLookUp.put(entityUniqueId, civilization);
    }

    public void removeEntityFromCivilization(Entity entity) {
        this.removeEntityFromCivilization(entity.getUniqueID());
    }

    public void removeEntityFromCivilization(UUID entityUniqueId) {
        playerLookUp.remove(entityUniqueId);
    }

    @Override
    public void read(@Nonnull CompoundNBT nbt) {
        ListNBT civilizationData = nbt.getList("civilizations", 10);
        IntStream.range(0, civilizationData.size())
                .mapToObj(civilizationData::getCompound)
                .map(Civilization::load)
                .forEach(civilization -> civilizationLookUp.put(civilization.getUniqueId(), civilization));
    }

    @Override
    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        ListNBT civilizationData = new ListNBT();
        for (Civilization civilization : civilizationLookUp.values()) {
            civilizationData.add(civilization.serializeNBT());
        }
        compound.put("civilizations", civilizationData);
        ListNBT playerData = new ListNBT();
        for (Map.Entry<UUID, Civilization> entry : playerLookUp.entrySet()) {

        }
        compound.put("players", playerData);
        return compound;
    }
}
