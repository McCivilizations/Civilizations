package com.mccivilizations.civilizations.api.civilization.data;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.leadership.Role;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.UUID;

public interface ICivilizationData extends INBTSerializable<CompoundNBT> {
    Civilization get(UUID uuid);

    List<Pair<Role, Civilization>> get(Entity entity);

    void add(Civilization civilization);

    void addTo(Entity entity, Civilization civilization);

    void removeFrom(Entity entity, Civilization civilization);

    default Role getRole(Entity entity, Civilization civilization) {
        return this.getRole(entity.getUniqueID(), civilization);
    }

    Role getRole(UUID uniqueId, Civilization civilization);

    default void setRole(Entity entity, Civilization civilization) {
        this.setRole(entity.getUniqueID(), civilization);
    }

    void setRole(UUID uniqueId, Civilization civilization);
}
