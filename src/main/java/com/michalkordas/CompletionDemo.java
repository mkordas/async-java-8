package com.michalkordas;

import com.diffplug.common.base.Errors;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CompletionDemo {

    private static final int NO_TASKS = 10;
    private static final ExecutorService SERVICE = Executors.newFixedThreadPool(NO_TASKS);

    public static void main(String[] args) {
        CompletableFuture<String> initialFuture = new CompletableFuture<>();

        CompletableFuture<Void> timedFuture = CompletableFuture
            .runAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        CompletableFuture<String> chainedFuture = initialFuture
            .thenApply(s -> s + " World")
            .thenCombine(timedFuture, (s, aVoid) -> s + "!");

        final List<Runnable> objects = Collections.nCopies(NO_TASKS, () -> {
            try {
                System.out.println(chainedFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException();
            }
        });
        objects.forEach(SERVICE::execute);
        SERVICE.shutdown();
        initialFuture.complete("Hello");
    }
}