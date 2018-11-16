package com.mccivilizations.civilizations.network.reactive;

import java.util.function.Consumer;

public interface IRequestHandler<ParamT, ObjectT> {
    void handleRequest(String method, ParamT params, Consumer<ObjectT> callBack);
}
