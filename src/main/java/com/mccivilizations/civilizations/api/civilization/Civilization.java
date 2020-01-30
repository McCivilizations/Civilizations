package com.mccivilizations.civilizations.api.civilization;

import net.minecraft.nbt.CompoundNBT;

public class Civilization {
    private final long id;
    private final String name;
    private final String isoCode;
    private final CompoundNBT flagPattern;

    public Civilization(long id, String name, String isoCode, CompoundNBT flagPattern) {
        this.id = id;
        this.name = name;
        this.isoCode = isoCode;
        this.flagPattern = flagPattern;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getIsoCode() {
        return this.isoCode;
    }

    public CompoundNBT getFlagPattern() {
        return this.flagPattern;
    }
}
