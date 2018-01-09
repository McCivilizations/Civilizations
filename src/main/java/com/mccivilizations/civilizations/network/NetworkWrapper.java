package com.mccivilizations.civilizations.network;

import com.mccivilizations.civilizations.Civilizations;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkWrapper {
    private static NetworkWrapper instance;
    private final SimpleNetworkWrapper simpleNetworkWrapper;
    private int id = 0;

    public static NetworkWrapper getInstance() {
        if (instance == null) {
            instance = new NetworkWrapper();
        }
        return instance;
    }

    private NetworkWrapper() {
        simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Civilizations.MODID);
    }

    public <I extends IMessage, O extends IMessage> void registerPacket(
            Class<? extends IMessageHandler<I, O>> messageHandler, Class<I> requestMessageType, Side side) {
        simpleNetworkWrapper.registerMessage(messageHandler, requestMessageType, ++id, side);
    }

    public void sendToServer(IMessage message) {
        simpleNetworkWrapper.sendToServer(message);
    }
}
