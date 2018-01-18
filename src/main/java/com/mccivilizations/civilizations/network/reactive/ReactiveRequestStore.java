package com.mccivilizations.civilizations.network.reactive;

import com.google.common.collect.Maps;
import com.mccivilizations.civilizations.network.NetworkWrapper;
import com.mccivilizations.civilizations.network.reactive.mapper.IReactiveRequestMapper;
import com.mccivilizations.civilizations.network.reactive.mapper.ReactiveRequestMapper;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveRequestMessage;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveResponseMessage;
import com.mccivilizations.civilizations.network.reactive.requesthandler.IReactiveRequestHandler;
import com.mccivilizations.civilizations.network.reactive.requesthandler.ReactiveRequestHandler;
import com.mccivilizations.civilizations.utils.ClassLoading;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ReactiveRequestStore {
    private Map<Long, Consumer> waitingRequests;
    private Map<String, IReactiveRequestMapper> mappers;
    private Map<String, IReactiveRequestHandler> handlers;

    ReactiveRequestStore(ASMDataTable asmDataTable) {
        waitingRequests = Maps.newHashMap();
        mappers = ClassLoading.getInstances(asmDataTable, ReactiveRequestMapper.class, IReactiveRequestMapper.class)
                .stream()
                .collect(Collectors.toMap(IReactiveRequestMapper::getName, requestMapper -> requestMapper));

        handlers = ClassLoading.getInstances(asmDataTable, ReactiveRequestHandler.class, IReactiveRequestHandler.class)
                .stream()
                .collect(Collectors.toMap(IReactiveRequestHandler::getName, requestMapper -> requestMapper));

    }

    <T> void addNewRequest(long id, Consumer<T> onReceive) {
        waitingRequests.put(id, onReceive);
    }

    @SuppressWarnings("unchecked")
    public void handleResponse(ReactiveResponseMessage reactiveResponseMessage) {
        if (waitingRequests.containsKey(reactiveResponseMessage.getId())) {
            waitingRequests.get(reactiveResponseMessage.getId()).accept(reactiveResponseMessage.getResponse());
        }
    }

    @SuppressWarnings("unchecked")
    public void handleRequest(EntityPlayer entityPlayer, ReactiveRequestMessage reactiveRequestMessage) {
        ReactiveRequest reactiveRequest = reactiveRequestMessage.getRequest();

        if (handlers.containsKey(reactiveRequest.getType())) {
            final String type = reactiveRequest.getType();
            final Long id = reactiveRequestMessage.getId();
            final WeakReference<EntityPlayer> playerWeakReference = new WeakReference<>(entityPlayer);
            handlers.get(type).handleRequest(reactiveRequest.getMethod(), reactiveRequest.getParameters(),
                   response -> ReactiveNetwork.getInstance().respondToClient(playerWeakReference, id, type, response));
        }
    }

    public IReactiveRequestMapper getMapper(String name) {
        return mappers.get(name);
    }
}
