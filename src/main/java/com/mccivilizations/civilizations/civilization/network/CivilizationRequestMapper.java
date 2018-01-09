package com.mccivilizations.civilizations.civilization.network;

import com.mccivilizations.civilizations.api.civilization.Civilization;
import com.mccivilizations.civilizations.api.date.MinecraftDate;
import com.mccivilizations.civilizations.network.reactive.mapper.IReactiveRequestMapper;
import com.mccivilizations.civilizations.network.reactive.mapper.ReactiveRequestMapper;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

@ReactiveRequestMapper
public class CivilizationRequestMapper implements IReactiveRequestMapper<Civilization> {
    @Override
    public String getName() {
        return Civilization.NAME;
    }

    @Override
    public Civilization fromBytes(ByteBuf buf) {
        Civilization civilization = new Civilization();
        civilization.setName(ByteBufUtils.readUTF8String(buf));
        civilization.setMotto(ByteBufUtils.readUTF8String(buf));
        civilization.setCreationDate(new MinecraftDate(buf.readLong()));
        long destructionTicks = buf.readLong();
        if (destructionTicks > 0) {
            civilization.setDestructionDate(new MinecraftDate(destructionTicks));
        }
        return civilization;
    }

    @Override
    public void toBytes(ByteBuf buf, Civilization civilization) {
        ByteBufUtils.writeUTF8String(buf, civilization.getName());
        ByteBufUtils.writeUTF8String(buf, civilization.getMotto());
        buf.writeLong(civilization.getCreationDate().getTicks());
        buf.writeLong(civilization.getDestructionDate().orElseGet(() -> new MinecraftDate(0)).getTicks());
    }
}
