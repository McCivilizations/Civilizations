package com.mccivilizations.civilizations.functional.attempt;

public class Success<T> extends Attempt<T> {
    private final T value;

    Success(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}
