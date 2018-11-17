package com.mccivilizations.civilizations.functional.attempt;

public abstract class Attempt<T> {
    public abstract T getValue();

    public abstract boolean isSuccess();

    public static <U> Success<U> success(U value) {
        return new Success<>(value);
    }

    public static <U> Failure<U> failure(RuntimeException value) {
        return new Failure<>(value);
    }
}
