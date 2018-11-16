package com.mccivilizations.civilizations.network.reactive;

import com.mccivilizations.civilizations.network.reactive.request.CreateReactiveRequestFunction;
import com.mccivilizations.civilizations.network.reactive.request.ReactiveRequestMessage;
import com.mccivilizations.civilizations.network.reactive.request.ReactiveRequestMessageHandler;
import com.mccivilizations.civilizations.network.reactive.response.CreateReactiveResponseFunction;
import com.mccivilizations.civilizations.network.reactive.response.ReactiveResponseHandler;
import com.mccivilizations.civilizations.network.reactive.response.ReactiveResponseMessage;
import com.teamacronymcoders.base.IBaseMod;
import com.teamacronymcoders.base.network.PacketHandler;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public class ReactiveNetwork<ParamT, ObjectT, RequestT extends ReactiveRequestMessage<ParamT>,
        ResponseT extends ReactiveResponseMessage<ObjectT>> {

    private CreateReactiveRequestFunction<ParamT> createRequestPacket;
    private CreateReactiveResponseFunction<ObjectT> createResponsePacket;
    private final IRequestHandler<ParamT, ObjectT> requestHandler;
    private final PacketHandler packetHandler;

    private int lastRequestId;

    private final TIntObjectMap<WeakReference<EntityPlayerMP>> responsePlayers;
    private final TIntObjectMap<Consumer<ObjectT>> responseHandlers;

    public ReactiveNetwork(IBaseMod mod, Class<RequestT> requestTClass, CreateReactiveRequestFunction<ParamT> createRequestPacket,
                           Class<ResponseT> responseTClass, CreateReactiveResponseFunction<ObjectT> createResponsePacket,
                           IRequestHandler<ParamT, ObjectT> requestHandler) {
        this.createRequestPacket = createRequestPacket;
        this.createResponsePacket = createResponsePacket;
        this.requestHandler = requestHandler;
        this.packetHandler = mod.getPacketHandler();
        this.responseHandlers = new TIntObjectHashMap<>();
        this.responsePlayers = new TIntObjectHashMap<>();
        this.packetHandler.registerPacket(new ReactiveRequestMessageHandler<>(this), requestTClass, Side.SERVER);
        this.packetHandler.registerPacket(new ReactiveResponseHandler<>(this), responseTClass, Side.CLIENT);
    }

    public void sendRequest(String method, ParamT params, Consumer<ObjectT> objectTConsumer) {
        final int requestId = ++lastRequestId;
        this.packetHandler.sendToServer(createRequestPacket.apply(requestId, method, params));
        this.responseHandlers.put(requestId, objectTConsumer);
    }

    public void handleRequest(int requestId, String method, ParamT parameters) {
        requestHandler.handleRequest(method, parameters, objectT -> sendResponse(requestId, objectT));
    }

    private void sendResponse(int requestId, ObjectT object) {
        WeakReference<EntityPlayerMP> responsePlayerRef = responsePlayers.remove(requestId);
        if (responsePlayerRef != null) {
            responsePlayers.remove(requestId);
            EntityPlayerMP entityPlayerMP = responsePlayerRef.get();
            if (entityPlayerMP != null) {
                this.packetHandler.sendToPlayer(createResponsePacket.apply(requestId, object), entityPlayerMP);
            }
        }
    }

    public void handleResponse(int requestId, ObjectT object) {
        Consumer<ObjectT> responseHandler = responseHandlers.remove(requestId);
        if (responseHandler != null) {
            responseHandler.accept(object);
        }
    }
}
