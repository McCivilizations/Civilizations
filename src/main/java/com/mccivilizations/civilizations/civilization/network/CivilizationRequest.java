package com.mccivilizations.civilizations.civilization.network;

import com.mccivilizations.civilizations.network.reactive.request.ReactiveRequestMessage;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class CivilizationRequest extends ReactiveRequestMessage<String> {
    public CivilizationRequest() {

    }

    public CivilizationRequest(int requestId, String method, String civilizationParameter) {
        super(requestId, method, civilizationParameter);
    }

    @Override
    protected String readParameters(ByteBuf buf) {
        return ByteBufUtils.readUTF8String(buf);
    }

    @Override
    protected void writeParameters(ByteBuf buf, String parameter) {
        ByteBufUtils.writeUTF8String(buf, parameter);
    }
}
