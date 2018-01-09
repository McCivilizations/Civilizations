package com.mccivilizations.civilizations.civilization;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.civilization.ICivilizationHandler;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;
import java.util.function.Consumer;

public class CivilizationServerHandler implements ICivilizationHandler {
    @Override
    public void getCivilizationByName(String name, Consumer<Civilization> onReceived) {

    }

    @Override
    public void getCivilizationByPlayer(EntityPlayer entityPlayer, Consumer<Civilization> onReceived) {
        getCivilizationByPlayerUUID(entityPlayer.getUniqueID(), onReceived);
    }

    @Override
    public void getCivilizationByPlayerUUID(UUID uuid, Consumer<Civilization> onReceived) {

    }
}
