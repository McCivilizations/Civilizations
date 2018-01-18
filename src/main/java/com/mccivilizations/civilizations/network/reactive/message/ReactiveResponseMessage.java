package com.mccivilizations.civilizations.network.reactive.message;

import com.mccivilizations.civilizations.network.reactive.ReactiveNetwork;
import com.mccivilizations.civilizations.network.reactive.mapper.IReactiveRequestMapper;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ReactiveResponseMessage<T> implements IMessage {
    private Long id;
    private T response;
    private String type;

    public ReactiveResponseMessage(Long id, T response, String type) {
        this.id = id;
        this.response = response;
        this.type = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readLong();
        this.type = ByteBufUtils.readUTF8String(buf);
        this.response = this.getMapper().fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(this.id);
        ByteBufUtils.writeUTF8String(buf, this.type);
        this.getMapper().toBytes(buf, this.response);
    }

    @SuppressWarnings("unchecked")
    public IReactiveRequestMapper<T> getMapper() {
        return ReactiveNetwork.getInstance().getRequestStore().getMapper(this.type);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
