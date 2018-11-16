package com.mccivilizations.civilizations.civilization.network;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.network.reactive.response.ReactiveResponseMessage;
import io.netty.buffer.ByteBuf;

public class CivilizationResponse extends ReactiveResponseMessage<Civilization> {
    @Override
    protected Civilization readObject(ByteBuf buf) {
        return new Civilization();
    }

    @Override
    protected void writeObject(ByteBuf buf, Civilization object) {

    }
}
