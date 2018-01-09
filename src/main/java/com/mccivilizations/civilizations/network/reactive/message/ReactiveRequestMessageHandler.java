package com.mccivilizations.civilizations.network.reactive.message;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ReactiveRequestMessageHandler implements IMessageHandler<ReactiveRequestMessage, IMessage> {
    @Override
    public IMessage onMessage(ReactiveRequestMessage message, MessageContext ctx) {

        return null;
    }
}
