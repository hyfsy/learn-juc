package com.hyf.juc.hello;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Callable测试
 *
 * @author baB_hyf
 * @date 2020/12/06
 */
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> callable = () -> {
            System.out.println("1");
            return 1024;
        };


        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();// 多次调用，任务只会执行一次
        new Thread(futureTask).start();

        // System.out.println(futureTask.get()); // 获取任务执行结果，会阻塞
    }
}
