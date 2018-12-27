package com.mccivilizations.civilizations.databasehandling;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.database.operation.UpdateOperation;
import net.minecraft.entity.player.EntityPlayer;

public class DBPlayer {
    public static void create(final EntityPlayer entityPlayer) {
        CivilizationsAPI.getInstance().getDatabaseClient().submitOperation(new UpdateOperation(
                "INSERT INTO PLAYER(NAME, UUID) VALUES(?, ?)", preparedStatement -> {
            preparedStatement.setString(1, entityPlayer.getName());
            preparedStatement.setString(2, entityPlayer.getUniqueID().toString());
            preparedStatement.setString(3, entityPlayer.getName());
        }));
    }
}
