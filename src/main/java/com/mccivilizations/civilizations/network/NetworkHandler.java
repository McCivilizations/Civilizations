package com.mccivilizations.civilizations.network;

import com.mccivilizations.civilizations.Civilizations;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";

    private final SimpleChannel channel;
    private int id;

    public NetworkHandler() {
        channel = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Civilizations.ID, "network"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::matches,
                PROTOCOL_VERSION::matches
        );
    }

    public <T> void register(Class<T> packetClass, BiConsumer<T, PacketBuffer> encode,
                             Function<PacketBuffer, T> decode, BiConsumer<T, Supplier<NetworkEvent.Context>> handler) {
        channel.registerMessage(id++, packetClass, encode, decode, handler);
    }

    public <T> void sendPacket(T packet) {
        channel.sendToServer(packet);
    }
}
