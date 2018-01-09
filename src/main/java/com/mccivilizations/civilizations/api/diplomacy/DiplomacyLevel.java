package com.mccivilizations.civilizations.api.diplomacy;

public enum DiplomacyLevel {
    ALLIES(3),
    FRIENDS(2),
    FRIENDLY(1),
    NEUTRAL(0),
    UNKNOWN(0),
    UNFRIENDLY(-1),
    DENOUNCED(-2),
    AT_WAR(-3);

    private final int level;

    DiplomacyLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}