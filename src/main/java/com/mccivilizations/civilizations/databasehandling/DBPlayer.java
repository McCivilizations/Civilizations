package com.mccivilizations.civilizations.databasehandling;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.database.operation.UpdateOperation;
import net.minecraft.entity.player.EntityPlayer;

public class DBPlayer {
    public static void create(final EntityPlayer entityPlayer) {
        Civilizations.proxy.getDatabase().submitOperation(new UpdateOperation(
                "INSERT INTO PERSON(NAME, UUID) VALUES(?, ?) " +
                        "ON CONFLICT UPDATE NAME = ?", preparedStatement -> {
            preparedStatement.setString(1, entityPlayer.getName());
            preparedStatement.setString(2, entityPlayer.getUniqueID().toString());
            preparedStatement.setString(3, entityPlayer.getName());
        }));
    }
}
