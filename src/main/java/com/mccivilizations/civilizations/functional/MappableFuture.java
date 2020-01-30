package com.mccivilizations.civilizations.functional;

import javax.annotation.Nonnull;
import java.util.concurrent.*;
import java.util.function.Function;

public class MappableFuture<T, U> implements Future<U> {
    private final Future<T> future;
    private final Function<T, U> handled;

    public MappableFuture(Future<T> future, Function<T, U> handled) {
        this.future = future;
        this.handled = handled;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }

    @Override
    public U get() throws InterruptedException, ExecutionException {
        T value = future.get();
        return handled.apply(value);
    }

    @Override
    public U get(long timeout, @Nonnull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        T value = future.get(timeout, unit);
        return handled.apply(value);
    }
}
