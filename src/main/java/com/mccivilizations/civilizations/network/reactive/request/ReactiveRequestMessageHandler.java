package com.mccivilizations.civilizations.network.reactive.request;

import com.mccivilizations.civilizations.network.reactive.ReactiveNetwork;
import com.mccivilizations.civilizations.network.reactive.response.ReactiveResponseMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ReactiveRequestMessageHandler<ParamT, ObjectT, RequestT extends ReactiveRequestMessage<ParamT>,
        ResponseT extends ReactiveResponseMessage<ObjectT>> implements IMessageHandler<RequestT, IMessage> {

    private final ReactiveNetwork<ParamT, ObjectT, RequestT,ResponseT> reactiveNetwork;

    public ReactiveRequestMessageHandler(ReactiveNetwork<ParamT, ObjectT, RequestT, ResponseT> reactiveNetwork) {
        this.reactiveNetwork = reactiveNetwork;
    }

    @Override
    public IMessage onMessage(RequestT message, MessageContext ctx) {
        this.reactiveNetwork.handleRequest(message.getRequestId(), message.getMethod(), message.getParameters());
        return null;
    }
}
