package com.mccivilizations.civilizations.network.reactive;

import com.mccivilizations.civilizations.network.NetworkWrapper;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveRequestMessage;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveRequestMessageHandler;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveResponseMessage;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveResponseMessageHandler;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.relauncher.Side;

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

        NetworkWrapper.getInstance().registerPacket(ReactiveRequestMessageHandler.class, ReactiveRequestMessage.class,  Side.SERVER);
        NetworkWrapper.getInstance().registerPacket(ReactiveResponseMessageHandler.class, ReactiveResponseMessage.class,  Side.CLIENT);

    }

    public <T> void requestFromServer(String type, String method, Map<String, String> parameters, Consumer<T> onReceive) {
        long requestId = nextId++;
        requestStore.addNewRequest(requestId, onReceive);

        NetworkWrapper.getInstance().sendToServer(new ReactiveRequestMessage(requestId,
                new ReactiveRequest(type, method, parameters)));
    }

    public ReactiveRequestStore getRequestStore() {
        return requestStore;
    }
}
