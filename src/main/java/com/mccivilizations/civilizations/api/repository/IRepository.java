package com.mccivilizations.civilizations.api.repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface IRepository<T> {
    CompletableFuture<T> create(T value);

    CompletableFuture<Integer> update(T value);

    CompletableFuture<Optional<T>> getById(long id);

    CompletableFuture<Integer> delete(T value);

    IResultSetHandler<T> getResultSetHandler();
}