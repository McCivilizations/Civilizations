package com.mccivilizations.civilizations.api.civilization;

import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;
import java.util.function.Consumer;

public interface ICivilizationHandler {
    void getCivilizationByName(String name, Consumer<Civilization> onReceived);

    void getCivilizationByPlayer(EntityPlayer entityPlayer, Consumer<Civilization> onReceived);

    void getCivilizationByPlayerUUID(UUID uuid, Consumer<Civilization> onReceived);
}
