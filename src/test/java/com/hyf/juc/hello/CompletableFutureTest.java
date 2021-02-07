package com.hyf.juc.hello;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异步回调
 *
 * @author baB_hyf
 * @date 2020/12/07
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 无返回值
        CompletableFuture<Void> async = CompletableFuture.runAsync(() -> {
            System.out.println("异步调用 无返回值");
        });
        async.get();

        CompletableFuture<Integer> async2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步调用 有返回值");
            int i =  1/0;
            return 1;
        });

        // async2.get();

        async2.whenComplete((i, e) -> { // 不管有没有异常都会走
            System.out.println("Result: " + i);
            System.out.println("Exception: " + e);
        }).exceptionally((e) -> {
            System.out.println("Exception: " + e);
            return 111;
        });

        Thread.currentThread().join();
    }
}
