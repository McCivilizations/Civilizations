package com.mccivilizations.civilizations.network.reactive.response;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class ReactiveResponseMessage<T> implements IMessage {
    private int requestId;
    private T object;

    public ReactiveResponseMessage() {

    }

    public ReactiveResponseMessage(int requestId, T object) {
        this.requestId = requestId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.requestId = buf.readInt();
        this.object = this.readObject(buf);
    }

    protected abstract T readObject(ByteBuf buf);

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(requestId);
        this.writeObject(buf, object);
    }

    protected abstract void writeObject(ByteBuf buf, T object);

    public int getRequestId() {
        return requestId;
    }

    public T getObject() {
        return object;
    }
}
