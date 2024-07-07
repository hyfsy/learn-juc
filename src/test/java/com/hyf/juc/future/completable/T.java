package com.hyf.juc.future.completable;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * Future的增强版本，不需要在get()调用时使当前线程进入等待状态
 * <p>
 * 1.8新增 orz
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class T {

    @Test
    public void testUse() throws Exception {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        completableFuture.thenAccept(val -> {
            System.out.println("thenAccept: " + val);
        })
                .thenApply(val -> {
                    System.out.println("thenApply: " + val);
                    return "xxx";
                })
                .thenAcceptAsync(val -> {
                    System.out.println("thenAcceptAsync: " + val);
                }, ForkJoinPool.commonPool())
                .thenRunAsync(() -> {
                    System.out.println("thenRunAsync");
                })
                .whenComplete((placeholder, exception) -> {
                    System.out.println("whenComplete");
                });
        completableFuture.complete(1);

        // ...

        System.out.println(completableFuture.get());


        Thread.currentThread().join();
    }

    @Test(expected = ExecutionException.class)
    public void testUseSync() throws Exception {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();

        completableFuture.completeExceptionally(new RuntimeException());

        System.out.println(completableFuture.get());
    }

    @Test
    public void testUseAsync() {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();

        completableFuture.whenComplete((val, ex) -> {
            System.out.println("whenComplete: " + val);
        });

        completableFuture.complete(1);
    }
}
