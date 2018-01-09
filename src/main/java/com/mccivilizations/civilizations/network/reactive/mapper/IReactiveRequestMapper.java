package com.mccivilizations.civilizations.network.reactive.mapper;

import io.netty.buffer.ByteBuf;

public interface IReactiveRequestMapper<T> {
    String getName();

    T fromBytes(ByteBuf buf);

    void toBytes(ByteBuf buf, T object);
}
