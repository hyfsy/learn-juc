package com.hyf.juc.future.completable;

import java.util.concurrent.CompletableFuture;

/**
 * Future的增强版本，不需要在get()调用时使当前线程进入等待状态
 * <p>
 * 1.8新增 orz
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class T {

    public static void main(String[] args) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        completableFuture.thenRunAsync(() -> {
            System.out.println(1);
        });

        // ...

    }
}
