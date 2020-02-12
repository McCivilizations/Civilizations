package com.mccivilizations.civilizations.network;

import com.mccivilizations.civilizations.api.CivilizationsAPI;
import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.data.CivilizationData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class CivilizationPacket {
    private final Civilization civilization;

    public CivilizationPacket(Civilization civilization) {
        this.civilization = civilization;
    }

    public void encode(PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(civilization != null);
        this.getCivilization().encode(packetBuffer);
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        ServerPlayerEntity playerEntity = Objects.requireNonNull(contextSupplier.get().getSender());
        contextSupplier.get().enqueueWork(() -> this.alterCivilizationData(playerEntity,
                CivilizationsAPI.getCivilizationData(playerEntity.getEntityWorld())));
        contextSupplier.get().setPacketHandled(true);
    }

    public abstract void alterCivilizationData(PlayerEntity playerEntity, CivilizationData civilizationData);
}
