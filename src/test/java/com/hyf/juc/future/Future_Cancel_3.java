package com.hyf.juc.future;

import java.util.concurrent.FutureTask;

/**
 * 未启动的任务调用cancel则永远不会被执行
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class Future_Cancel_3 {

    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println("任务执行");
            return 888;
        });


        System.out.println(futureTask.isDone());
        System.out.println(futureTask.isCancelled());

        System.out.println(futureTask.cancel(true)); // 任务永远不会执行

        System.out.println(futureTask.isDone());
        System.out.println(futureTask.isCancelled());

        new Thread(futureTask).start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
