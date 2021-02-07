package com.hyf.juc.future;

import java.util.concurrent.FutureTask;

/**
 * boolean cancel(boolean mayInterruptIfRunning); // 取消执行任务
 * <p>
 * 返回false表示任务已经执行完毕，或已经被取消，或其他不能被取消的原因
 * 返回true表示方法执行成功
 * 如果任务并未启动，则该任务永远不会启动
 * 如果任务已经启动，则根据传入的参数决定 是否应该中断执行的任务线程并且停止它
 * <p>
 * 返回true后，isDone方法则永远返回true，isCancelled方法也永远返回true
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class Future_Cancel_1 {

    /**
     * 任务执行完成，调用cancel返回false
     */
    public static void main(String[] args) {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            System.out.println("任务执行");
            return 888;
        });


        new Thread(futureTask).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(futureTask.isDone());
        System.out.println(futureTask.isCancelled());

        System.out.println(futureTask.cancel(true)); // 执行完毕，无法取消，返回false

        System.out.println(futureTask.isCancelled());
    }
}
