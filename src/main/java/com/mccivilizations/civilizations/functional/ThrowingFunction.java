package com.mccivilizations.civilizations.functional;

public interface ThrowingFunction<T, R, E extends Exception> {
    R apply(T value) throws E;
}
