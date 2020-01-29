package com.mccivilizations.civilizations.functional;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T value) throws E;
}
