package com.mccivilizations.civilizations.network;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.civilization.data.ICivilizationData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class CivilizationPacket {
    private final Civilization civilization;

    public CivilizationPacket(Civilization civilization) {
        this.civilization = civilization;
    }

    public void encode(PacketBuffer packetBuffer) {
        this.getCivilization().encode(packetBuffer);
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        ServerPlayerEntity playerEntity = Objects.requireNonNull(contextSupplier.get().getSender());
        contextSupplier.get().enqueueWork(() -> Objects.requireNonNull(playerEntity.getServer())
                .getWorld(DimensionType.OVERWORLD)
                .getCapability(CivilizationsAPI.CIV_DATA)
                .ifPresent(civilizationData -> this.alterCivilizationData(contextSupplier.get(), civilizationData)));
        contextSupplier.get().setPacketHandled(true);
    }

    public abstract void alterCivilizationData(NetworkEvent.Context context, ICivilizationData civilizationData);
}
