package com.mccivilizations.civilizations.functional.attempt;

public class Failure<T> extends Attempt<T> {
    private final RuntimeException exception;

    Failure(RuntimeException exception) {
        this.exception = exception;
    }

    @Override
    public T getValue() {
        throw exception;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}
