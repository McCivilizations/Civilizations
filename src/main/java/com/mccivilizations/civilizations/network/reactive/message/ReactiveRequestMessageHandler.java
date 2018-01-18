package com.mccivilizations.civilizations.network.reactive.message;

import com.mccivilizations.civilizations.network.reactive.ReactiveNetwork;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ReactiveRequestMessageHandler implements IMessageHandler<ReactiveRequestMessage, IMessage> {
    @Override
    public IMessage onMessage(ReactiveRequestMessage message, MessageContext ctx) {
        ReactiveNetwork.getInstance().getRequestStore().handleRequest(ctx.getServerHandler().player, message);
        return null;
    }
}
