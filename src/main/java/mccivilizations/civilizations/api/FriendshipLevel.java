package mccivilizations.civilizations.api;

import net.minecraft.util.IStringSerializable;

public enum FriendshipLevel implements IStringSerializable {
    ALLY,
    FRIEND,
    NEUTRAL,
    DISLIKE,
    ENEMY;


    @Override
    public String getName() {
        return name().toLowerCase();
    }
}