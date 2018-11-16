package com.mccivilizations.civilizations.civilization;

import com.google.common.collect.Maps;
import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.civilization.ICivilizationHandler;
import com.mccivilizations.civilizations.network.reactive.ReactiveNetwork;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class CivilizationClientHandler implements ICivilizationHandler {
    @Override
    public void getCivilizationByName(String name, Consumer<Civilization> onReceived) {
        Map<String, String> parameters = Maps.newHashMap();
        parameters.put("CIV_NAME", name);
        ReactiveNetwork.getInstance().requestFromServer(Civilization.NAME, "BY_CIV_NAME", parameters, onReceived);
    }

    @Override
    public void getCivilizationByPlayer(EntityPlayer entityPlayer, Consumer<Civilization> onReceived) {
        getCivilizationByPlayerUUID(entityPlayer.getUniqueID(), onReceived);
    }

    @Override
    public void getCivilizationByPlayerUUID(UUID uuid, Consumer<Civilization> onReceived) {
        Map<String, String> parameters = Maps.newHashMap();
        parameters.put("LEADER_UUID", uuid.toString());
        Civilizations.INSTANCE.getCivilizationNetwork()
                .sendRequest(Civilization.NAME, parameters, onReceived);
    }
}
