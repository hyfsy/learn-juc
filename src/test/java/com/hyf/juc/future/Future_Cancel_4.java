package com.hyf.juc.future;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 中断或不中断执行任务的线程
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class Future_Cancel_4 {

    /**
     * 任务执行完成，调用cancel返回false
     */
    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println("开始执行");
            // try {
            Thread.sleep(3000);
            // } catch (InterruptedException e) {
            // }
            System.out.println("执行结束");
            return 888;
        });


        new Thread(futureTask).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(() -> {
            try {
                System.out.println(futureTask.get()); // CancellationException
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("获取结果异常");
                e.printStackTrace();
            } catch (CancellationException e) {
                System.out.println("获取结果线程被中断");
            }
        }).start();


        System.out.println(futureTask.isDone());
        System.out.println(futureTask.isCancelled());

        System.out.println(futureTask.cancel(true)); // true表示中断正在执行任务的线程
        // System.out.println(futureTask.cancel(false)); // false表示不中断正在执行任务的线程

        System.out.println(futureTask.isDone());
        System.out.println(futureTask.isCancelled());
    }
}
