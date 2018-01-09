package com.mccivilizations.civilizations.api.civilization;

import com.mccivilizations.civilizations.api.date.MinecraftDate;
import com.mccivilizations.civilizations.api.flag.Flag;
import com.mccivilizations.civilizations.api.leader.Leader;

import java.util.Optional;

public class Civilization {
    public static final String NAME = "civilization";

    private Leader leader;
    private Flag flag;
    private String name;
    private String motto;
    private MinecraftDate creationDate;
    private MinecraftDate destructionDate;

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public MinecraftDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(MinecraftDate creationDate) {
        this.creationDate = creationDate;
    }

    public Optional<MinecraftDate> getDestructionDate() {
        return Optional.ofNullable(destructionDate);
    }

    public void setDestructionDate(MinecraftDate destructionDate) {
        this.destructionDate = destructionDate;
    }
}
