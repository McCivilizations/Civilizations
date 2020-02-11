package com.mccivilizations.civilizations.network;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.civilization.data.ICivilizationData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class LeaveCivilizationPacket extends CivilizationPacket {
    public LeaveCivilizationPacket(Civilization civilization) {
        super(civilization);
    }

    @Override
    public void alterCivilizationData(NetworkEvent.Context context, ICivilizationData civilizationData) {
        PlayerEntity playerEntity = context.getSender();
        civilizationData.removePlayerFrom(playerEntity, civilizationData.get(playerEntity));
    }

    public static LeaveCivilizationPacket decode(PacketBuffer packetBuffer) {
        Civilization civilization = new Civilization();
        civilization.decode(packetBuffer);
        return new LeaveCivilizationPacket(civilization);
    }


}
