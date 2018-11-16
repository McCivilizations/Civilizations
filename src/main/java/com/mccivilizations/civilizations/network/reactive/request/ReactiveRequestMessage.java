package com.mccivilizations.civilizations.network.reactive.request;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class ReactiveRequestMessage<T> implements IMessage {
    private int requestId;
    private String method;
    private T parameters;

    public ReactiveRequestMessage() {

    }

    public ReactiveRequestMessage(int requestId, String method, T parameters) {
        this.requestId = requestId;
        this.method = method;
        this.parameters = parameters;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.requestId = buf.readInt();
        this.method = ByteBufUtils.readUTF8String(buf);
        this.parameters = this.readParameters(buf);
    }

    protected abstract T readParameters(ByteBuf buf);

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(requestId);
        ByteBufUtils.writeUTF8String(buf, method);
        this.writeParameters(buf, parameters);
    }

    protected abstract void writeParameters(ByteBuf buf, T parameters);

    public int getRequestId() {
        return requestId;
    }

    public String getMethod() {
        return method;
    }

    public T getParameters() {
        return parameters;
    }
}
