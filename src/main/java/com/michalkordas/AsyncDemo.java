package com.michalkordas;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Test;

public class AsyncDemo {
    private final StackOverflow stackOverflow = new StackOverflow();
    private final Google google = new Google();
    private volatile String question;

    @Test
    public void synchronous() {
        System.out.println(stackOverflow.newestQuestion());
    }

    @Test
    public void manually() throws InterruptedException {
        final Thread thread = new Thread(() -> question = stackOverflow.newestQuestion());
        thread.start();
        System.out.println("Some useful work");
        System.out.println(question);
    }

    @Test
    public void executorService() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(stackOverflow::newestQuestion);
        System.out.println(future.get());
    }

    @Test
    public void waitForFirst() {
        //??
    }

    @Test
    public void completableFuture() throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<String> future
            = CompletableFuture.supplyAsync(stackOverflow::newestQuestion);
    }

    @Test
    public void whoIsFaster() throws ExecutionException, InterruptedException {
        //??
    }

    private CompletableFuture<String> googleHits(String q) {
        return CompletableFuture.supplyAsync(() -> google.hits(q));
    }

    private CompletableFuture<String> newestQuestion() {
        return CompletableFuture.supplyAsync(stackOverflow::newestQuestion);
    }

}
