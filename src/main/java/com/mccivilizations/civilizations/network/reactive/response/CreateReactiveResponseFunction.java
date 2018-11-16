package com.mccivilizations.civilizations.network.reactive.response;

@FunctionalInterface
public interface CreateReactiveResponseFunction<ObjectT> {
    ReactiveResponseMessage<ObjectT> apply(int requestId, ObjectT objectT);
}
