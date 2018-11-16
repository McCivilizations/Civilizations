package com.mccivilizations.civilizations.network.reactive.response;

import com.mccivilizations.civilizations.network.reactive.ReactiveNetwork;
import com.mccivilizations.civilizations.network.reactive.request.ReactiveRequestMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ReactiveResponseHandler<ParamT, ObjectT, RequestT extends ReactiveRequestMessage<ParamT>,
        ResponseT extends ReactiveResponseMessage<ObjectT>> implements IMessageHandler<ResponseT, IMessage> {
    private final ReactiveNetwork<ParamT, ObjectT, RequestT, ResponseT> reactiveNetwork;

    public ReactiveResponseHandler(ReactiveNetwork<ParamT, ObjectT, RequestT, ResponseT> reactiveNetwork) {
        this.reactiveNetwork = reactiveNetwork;
    }

    @Override
    public IMessage onMessage(ResponseT message, MessageContext ctx) {
        reactiveNetwork.handleResponse(message.getRequestId(), message.getObject());
        return null;
    }
}
