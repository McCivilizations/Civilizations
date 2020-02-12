package com.mccivilizations.civilizations.network;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.data.CivilizationData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class LeaveCivilizationPacket extends CivilizationPacket {
    public LeaveCivilizationPacket(Civilization civilization) {
        super(civilization);
    }

    @Override
    public void alterCivilizationData(PlayerEntity playerEntity, CivilizationData civilizationData) {
        civilizationData.removeEntityFromCivilization(playerEntity);
    }

    public static LeaveCivilizationPacket decode(PacketBuffer packetBuffer) {
        Civilization civilization = new Civilization();
        civilization.decode(packetBuffer);
        return new LeaveCivilizationPacket(civilization);
    }


}
