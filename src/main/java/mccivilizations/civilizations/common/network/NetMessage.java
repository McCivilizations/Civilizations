package mccivilizations.civilizations.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public abstract class NetMessage<REQ extends NetMessage> implements Serializable, IMessage, IMessageHandler<REQ, IMessage> {

    private static final HashMap<Class, Pair<Reader, Writer>> handlers = new HashMap<>();

    static {
        map(byte.class, PacketBuffer::readByte, (buf, data) -> buf.writeByte((int) data));
        map(short.class, PacketBuffer::readShort, (buf, data) -> buf.writeShort((int) data));
        map(int.class, PacketBuffer::readVarInt, PacketBuffer::writeVarInt);
        map(long.class, PacketBuffer::readVarLong, PacketBuffer::writeVarLong);
        map(float.class, PacketBuffer::readFloat, PacketBuffer::writeFloat);
        map(double.class, PacketBuffer::readDouble, PacketBuffer::writeDouble);
        map(boolean.class, PacketBuffer::readBoolean, PacketBuffer::writeBoolean);
        map(char.class, PacketBuffer::readChar, (buf, data) -> buf.writeChar((int) data));
        map(String.class, (buf) -> buf.readString(32767), PacketBuffer::writeString);
        map(NBTTagCompound.class, PacketBuffer::readCompoundTag, PacketBuffer::writeCompoundTag);
        map(ItemStack.class, PacketBuffer::readItemStack, PacketBuffer::writeItemStack);
        map(BlockPos.class, PacketBuffer::readBlockPos, PacketBuffer::writeBlockPos);
    }

    public abstract IMessage handleMessage(MessageContext context);

    @Override
    public final IMessage onMessage(REQ message, MessageContext context) {
        return message.handleMessage(context);
    }

    @Override
    public final void fromBytes(ByteBuf buf) {
        try {
            PacketBuffer packetBuffer = new PacketBuffer(buf);
            Class<?> clazz = getClass();
            for(Field f : clazz.getDeclaredFields()) {
                Class<?> type = f.getType();
                if(acceptField(f, type))
                    readField(f, type, packetBuffer);
            }
        } catch(Exception e) {
            throw new RuntimeException("Error at reading packet " + this, e);
        }
    }

    @Override
    public final void toBytes(ByteBuf buf) {
        try {
            PacketBuffer packetBuffer = new PacketBuffer(buf);
            Class<?> clazz = getClass();
            for(Field f : clazz.getDeclaredFields()) {
                Class<?> type = f.getType();
                if(acceptField(f, type))
                    writeField(f, type, packetBuffer);
            }
        } catch(Exception e) {
            throw new RuntimeException("Error at writing packet " + this, e);
        }
    }

    private void writeField(Field f, Class clazz, PacketBuffer buf) throws IllegalArgumentException, IllegalAccessException {
        Pair<Reader, Writer> handler = getHandler(clazz);
        handler.getRight().write(buf, f.get(this));
    }

    private void readField(Field f, Class clazz, PacketBuffer buf) throws IllegalArgumentException, IllegalAccessException, IOException {
        Pair<Reader, Writer> handler = getHandler(clazz);
        f.set(this, handler.getLeft().read(buf));
    }

    private static Pair<Reader, Writer> getHandler(Class<?> clazz) {
        Pair<Reader, Writer> pair = handlers.get(clazz);
        if(pair == null)
            throw new RuntimeException("No R/W handler for  " + clazz);
        return pair;
    }

    private static boolean acceptField(Field f, Class<?> type) {
        int mods = f.getModifiers();
        return !Modifier.isFinal(mods) && !Modifier.isStatic(mods) && !Modifier.isTransient(mods) && handlers.containsKey(type);

    }

    private static <T> void map(Class<T> type, Reader<T> reader, Writer<T> writer) {
        handlers.put(type, Pair.of(reader, writer));
    }

    public interface Writer<T> {
        void write(PacketBuffer buf, T t);
    }

    public interface Reader<T> {
        T read(PacketBuffer buf) throws IOException;
    }

}