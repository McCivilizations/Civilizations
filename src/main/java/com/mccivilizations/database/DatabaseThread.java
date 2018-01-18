package com.mccivilizations.database;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public class DatabaseThread {
    private ListeningExecutorService executorService;

    public <T> void doDatabaseCall(Callable<T> task) {
        ListenableFuture<T> listenableFuture = executorService.submit(task);
        ;
    }
}
