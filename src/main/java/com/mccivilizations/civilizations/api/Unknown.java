package com.mccivilizations.civilizations.api;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.date.MinecraftDate;
import com.mccivilizations.civilizations.api.flag.Flag;
import com.mccivilizations.civilizations.api.leader.Leader;
import net.minecraft.util.ResourceLocation;

public class Unknown {
    private final Civilization civilization;
    private final Leader leader;
    private final Flag flag;

    private static Unknown instance;
    public static final String UNKNOWN = "Unknown";

    private Unknown() {
        leader = new Leader();
        flag = new Flag();
        flag.setFlagLocation(new ResourceLocation("civilizations", "unknown"));
        civilization = createUnknownCivilization(leader, flag);
    }

    private Civilization createUnknownCivilization(Leader leader, Flag flag) {
        Civilization civilization = new Civilization();
        civilization.setFlag(flag);
        civilization.setLeader(leader);
        civilization.setName(UNKNOWN);
        civilization.setMotto(UNKNOWN);
        civilization.setCreationDate(new MinecraftDate(0));
        return civilization;
    }

    public static Unknown getInstance() {
        if (instance == null) {
            instance = new Unknown();
        }
        return instance;
    }

    public static Civilization getCivilization() {
        return getInstance().civilization;
    }

    public static Leader getLeader() {
        return getInstance().leader;
    }

    public static Flag getFlag() {
        return getInstance().flag;
    }
}
