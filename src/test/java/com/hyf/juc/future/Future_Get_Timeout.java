package com.hyf.juc.future;

import java.util.concurrent.*;

/**
 * V get(long timeout, TimeUnit unit) // 有等待超时的获取
 * throws InterruptedException, ExecutionException, TimeoutException;
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class Future_Get_Timeout {

    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            Thread.sleep(3000);
            return 888;
        });

        new Thread(futureTask).start();

        try {
            System.out.println(futureTask.get(1, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(futureTask.get(1, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(futureTask.get(1, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
