package com.mccivilizations.civilizations.network.reactive.requesthandler;

import java.util.Map;
import java.util.function.Consumer;

public interface IReactiveRequestHandler<T> {
    String getName();

    void handleRequest(String method, Map<String, Object> parameters, Consumer<T> onHandled);
}
