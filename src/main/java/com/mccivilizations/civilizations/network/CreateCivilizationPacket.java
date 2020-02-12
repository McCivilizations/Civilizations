package com.mccivilizations.civilizations.network;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.data.CivilizationData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CreateCivilizationPacket extends CivilizationPacket {
    public CreateCivilizationPacket(Civilization civilization) {
        super(civilization);
    }

    @Override
    public void alterCivilizationData(PlayerEntity playerEntity, CivilizationData civilizationData) {
        civilizationData.addEntityToCivilization(playerEntity, this.getCivilization());
    }

    public static CreateCivilizationPacket decode(PacketBuffer packetBuffer) {
        Civilization civilization = new Civilization();
        if (packetBuffer.readBoolean()) {
            civilization.decode(packetBuffer);
        } else {
            Civilizations.LOGGER.error("Failed to Find Civilization");
        }
        return new CreateCivilizationPacket(civilization);
    }
}
