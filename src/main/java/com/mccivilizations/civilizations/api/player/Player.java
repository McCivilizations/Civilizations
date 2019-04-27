package com.mccivilizations.civilizations.api.player;

import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

public class Player {
    public final long id;
    public final String name;
    public final UUID uuid;

    public Player(long id, String name, UUID uuid) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
    }

    public Player(EntityPlayer entityPlayer) {
        this(-1, entityPlayer.getName(), entityPlayer.getUniqueID());
    }
}
