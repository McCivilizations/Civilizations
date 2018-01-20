package com.mccivilizations.civilizations.network.reactive;

import com.mccivilizations.civilizations.Civilizations;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveRequestMessage;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveRequestMessageHandler;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveResponseMessage;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveResponseMessageHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class ReactiveNetwork {
    private static ReactiveNetwork instance;

    private final ReactiveRequestStore requestStore;
    private long nextId = 1;

    public static ReactiveNetwork getInstance() {
        return Objects.requireNonNull(instance, "Called ReactiveNetwork before it was Setup");
    }

    public static void setup(ASMDataTable asmDataTable) {
        ReactiveNetwork reactiveNetwork = new ReactiveNetwork(asmDataTable);
        if (instance != null) {
            throw new IllegalStateException("Reactive Network was already setup!");
        } else {
            instance = reactiveNetwork;
        }
    }

    private ReactiveNetwork(ASMDataTable asmDataTable) {
        requestStore = new ReactiveRequestStore(asmDataTable);

        Civilizations.INSTANCE.getPacketHandler().registerPacket(ReactiveRequestMessageHandler.class, ReactiveRequestMessage.class,  Side.SERVER);
        Civilizations.INSTANCE.getPacketHandler().registerPacket(ReactiveResponseMessageHandler.class, ReactiveResponseMessage.class,  Side.CLIENT);

    }

    public <T> void requestFromServer(String type, String method, Map<String, String> parameters, Consumer<T> onReceive) {
        long requestId = nextId++;
        requestStore.addNewRequest(requestId, onReceive);

        Civilizations.INSTANCE.getPacketHandler().sendToServer(new ReactiveRequestMessage(requestId,
                new ReactiveRequest(type, method, parameters)));
    }

    public ReactiveRequestStore getRequestStore() {
        return requestStore;
    }

    @SuppressWarnings("unchecked")
    public void respondToClient(WeakReference<EntityPlayer> entityPlayerWeakReference, Long id, String type, Object object) {
        EntityPlayer entityPlayer = entityPlayerWeakReference.get();
        if (entityPlayer instanceof EntityPlayerMP) {
            Civilizations.INSTANCE.getPacketHandler().sendToPlayer(new ReactiveResponseMessage(id, object, type), (EntityPlayerMP) entityPlayer);
        }
    }
}
