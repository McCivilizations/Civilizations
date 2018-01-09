package com.mccivilizations.civilizations.network.reactive.message;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ReactiveResponseMessageHandler implements IMessageHandler<ReactiveResponseMessage, IMessage> {
    @Override
    public IMessage onMessage(ReactiveResponseMessage message, MessageContext ctx) {

        return null;
    }
}
