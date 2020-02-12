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
    private final Map<UUID, Civilization> entityLookUp;
    private final Supplier<Scoreboard> scoreboardSupplier;

    public CivilizationData() {
        this(() -> null);
    }

    public CivilizationData(Supplier<Scoreboard> scoreboardSupplier) {
        super(NAME);
        this.civilizationLookUp = Maps.newHashMap();
        this.entityLookUp = Maps.newHashMap();
        this.scoreboardSupplier = scoreboardSupplier;
    }

    public Civilization getCivilization(UUID civilizationUniqueId) {
        return civilizationLookUp.get(civilizationUniqueId);
    }

    public Civilization getCivilizationForEntity(Entity entity) {
        return this.getCivilizationForEntity(entity.getUniqueID());
    }

    public Civilization getCivilizationForEntity(UUID uniqueId) {
        return entityLookUp.get(uniqueId);
    }

    public void createCivilization(Civilization civilization) {
        civilizationLookUp.put(civilization.getUniqueId(), civilization);
        Optional.ofNullable(this.scoreboardSupplier.get())
                .ifPresent(scoreboard -> scoreboard.createTeam(civilization.getTeam()));
    }

    public void addEntityToCivilization(Entity entity, Civilization civilization) {
        if (!civilizationLookUp.containsKey(civilization.getUniqueId())) {
            this.createCivilization(civilization);
        }
        entityLookUp.put(entity.getUniqueID(), civilization);
        Optional.ofNullable(this.scoreboardSupplier.get())
                .ifPresent(scoreboard -> scoreboard.addPlayerToTeam(entity.getScoreboardName(),
                        scoreboard.getTeam(civilization.getTeam())));
    }

    public void removeEntityFromCivilization(Entity entity) {
        Civilization civilization = entityLookUp.remove(entity.getUniqueID());
        if (civilization != null) {
            Optional.ofNullable(this.scoreboardSupplier.get())
                    .ifPresent(scoreboard -> scoreboard.addPlayerToTeam(entity.getScoreboardName(),
                            scoreboard.getTeam(civilization.getTeam())));
        }
    }

    @Override
    public void read(@Nonnull CompoundNBT nbt) {
        ListNBT civilizationData = nbt.getList("civilizations", 10);
        IntStream.range(0, civilizationData.size())
                .mapToObj(civilizationData::getCompound)
                .map(Civilization::load)
                .forEach(civilization -> civilizationLookUp.put(civilization.getUniqueId(), civilization));
        ListNBT entityData = nbt.getList("entities", 10);
        IntStream.range(0, entityData.size())
                .mapToObj(entityData::getCompound)
                .forEach(compoundNBT ->
                        entityLookUp.put(
                                compoundNBT.getUniqueId("entity"),
                                this.getCivilization(compoundNBT.getUniqueId("civilization"))
                        )
                );
    }

    @Override
    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        ListNBT civilizationData = new ListNBT();
        for (Civilization civilization : civilizationLookUp.values()) {
            civilizationData.add(civilization.serializeNBT());
        }
        compound.put("civilizations", civilizationData);
        ListNBT entityData = new ListNBT();
        for (Map.Entry<UUID, Civilization> entry : entityLookUp.entrySet()) {
            CompoundNBT playerCivilizationInfo = new CompoundNBT();
            playerCivilizationInfo.putUniqueId("entity", entry.getKey());
            playerCivilizationInfo.putUniqueId("civilization", entry.getValue().getUniqueId());
            entityData.add(playerCivilizationInfo);
        }
        compound.put("entities", entityData);
        return compound;
    }
}
