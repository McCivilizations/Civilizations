package com.mccivilizations.civilizations.network;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class LeaveCivilizationPacket {
    public LeaveCivilizationPacket() {

    }

    public void encode(PacketBuffer packetBuffer) {

    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        ServerPlayerEntity playerEntity = Objects.requireNonNull(contextSupplier.get().getSender());
        Scoreboard scoreboard = Objects.requireNonNull(playerEntity.getServerWorld()).getScoreboard();
        contextSupplier.get().enqueueWork(() -> {
            scoreboard.removeEntity(playerEntity);
            playerEntity.getCapability(CivilizationsAPI.CITIZEN_CAP)
                    .ifPresent(citizen -> citizen.setCivilization(null));
        });
        contextSupplier.get().setPacketHandled(true);
    }

    public static LeaveCivilizationPacket decode(PacketBuffer packetBuffer) {
        return new LeaveCivilizationPacket();
    }


}
