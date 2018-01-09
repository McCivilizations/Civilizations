package com.mccivilizations.civilizations.network.reactive.message;

import com.google.common.collect.Maps;
import com.mccivilizations.civilizations.network.reactive.ReactiveRequest;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import scala.util.control.Exception;

import java.util.Map;

public class ReactiveRequestMessage implements IMessage {
    private long id;
    private ReactiveRequest request;

    public ReactiveRequestMessage(long id, ReactiveRequest request) {
        this.id = id;
        this.request = request;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readLong();
        String type = ByteBufUtils.readUTF8String(buf);
        String method = ByteBufUtils.readUTF8String(buf);
        int parameterLength = buf.readInt();
        Map<String, String> parameters = Maps.newHashMap();
        for (int i = 0; i < parameterLength; i++) {
            parameters.put(ByteBufUtils.readUTF8String(buf), ByteBufUtils.readUTF8String(buf));
        }
        this.request = new ReactiveRequest(type, method, parameters);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(id);
        ByteBufUtils.writeUTF8String(buf, request.getType());
        ByteBufUtils.writeUTF8String(buf, request.getMethod());
        int parameterLength = request.getParameters().size();
        buf.writeInt(parameterLength);
        request.getParameters().forEach((key, value) -> {
            ByteBufUtils.writeUTF8String(buf, key);
            ByteBufUtils.writeUTF8String(buf, value);
        });
    }

    public long getId() {
        return id;
    }

    public ReactiveRequest getRequest() {
        return request;
    }
}
