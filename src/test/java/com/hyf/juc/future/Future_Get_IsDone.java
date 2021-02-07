package com.hyf.juc.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * boolean isDone(); // 判断当前任务是否执行完毕
 * <p>
 * V get() throws InterruptedException, ExecutionException; // 获取任务执行结果，如果任务没有执行完毕，会阻塞等待
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class Future_Get_IsDone {

    public static void main(String[] args) {

        Callable<Integer> callable = () -> {
            Thread.sleep(1000);
            return 888;
        };

        FutureTask<Integer> futureTask = new FutureTask<>(callable);

        System.out.println(futureTask.isDone());
        System.out.println(futureTask.isCancelled());

        new Thread(futureTask).start();
        // futureTask.run(); // run实际上还是调用了call方法

        try {
            futureTask.get(); // 会阻塞
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(futureTask.isDone());
        System.out.println(futureTask.isCancelled());

    }
}
