package com.mccivilizations.civilizations.network.reactive;

import com.google.common.collect.Maps;
import com.mccivilizations.civilizations.network.reactive.mapper.IReactiveRequestMapper;
import com.mccivilizations.civilizations.network.reactive.mapper.ReactiveRequestMapper;
import com.mccivilizations.civilizations.network.reactive.message.ReactiveResponseMessage;
import com.mccivilizations.civilizations.network.reactive.requesthandler.IReactiveRequestHandler;
import com.mccivilizations.civilizations.network.reactive.requesthandler.ReactiveRequestHandler;
import com.mccivilizations.civilizations.utils.ClassLoading;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import java.util.Map;
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
    void handleResponse(ReactiveResponseMessage reactiveResponseMessage) {
        if (waitingRequests.containsKey(reactiveResponseMessage.getId())) {
            waitingRequests.get(reactiveResponseMessage.getId()).accept(reactiveResponseMessage.getResponse());
        }
    }

    public IReactiveRequestMapper getMapper(String name) {
        return mappers.get(name);
    }

    public IReactiveRequestHandler getHandler(String name) {
        return handlers.get(name);
    }

}
