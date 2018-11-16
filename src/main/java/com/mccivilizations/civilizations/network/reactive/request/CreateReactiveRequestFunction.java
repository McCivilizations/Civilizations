package com.mccivilizations.civilizations.network.reactive.request;

@FunctionalInterface
public interface CreateReactiveRequestFunction<ParamT> {
    ReactiveRequestMessage<ParamT> apply(int requestId, String method, ParamT params);
}
