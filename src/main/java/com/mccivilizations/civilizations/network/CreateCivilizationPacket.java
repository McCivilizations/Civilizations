package com.mccivilizations.civilizations.network;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.civilization.data.ICivilizationData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.management.OpList;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;

public class CreateCivilizationPacket extends CivilizationPacket {
    public CreateCivilizationPacket(Civilization civilization) {
        super(civilization);
    }

    @Override
    public void alterCivilizationData(NetworkEvent.Context context, ICivilizationData civilizationData) {
        PlayerEntity playerEntity = context.getSender();
        civilizationData.addPlayerTo(playerEntity, this.getCivilization());
    }

    public static CreateCivilizationPacket decode(PacketBuffer packetBuffer) {
        Civilization civilization = new Civilization();
        civilization.decode(packetBuffer);
        return new CreateCivilizationPacket(civilization);
    }
}
