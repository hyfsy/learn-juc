package com.hyf.juc.future;

import java.util.concurrent.FutureTask;

/**
 * 任务已经被cancel，再次调用，返回false
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class Future_Cancel_2 {

    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println("开始执行");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            System.out.println("执行完毕");
            return 888;
        });


        new Thread(futureTask).start();

        System.out.println(futureTask.isDone());
        System.out.println(futureTask.isCancelled());

        System.out.println(futureTask.cancel(true));
        System.out.println(futureTask.cancel(true)); // 不能取消，返回false

        System.out.println(futureTask.isDone());
        System.out.println(futureTask.isCancelled());


    }
}
