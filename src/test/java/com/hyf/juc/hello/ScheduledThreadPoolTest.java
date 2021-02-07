package com.hyf.juc.hello;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledThreadPoolExecutor测试
 *
 * @author baB_hyf
 * @date 2020/12/23
 */
public class ScheduledThreadPoolTest {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(2);

        Runnable r = () -> System.out.println("a");

        // 接口方法
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(r, 2, TimeUnit.SECONDS);
        System.out.println(scheduledFuture.cancel(true));

        // 固定的速度，当前任务与下个任务之间的delay时间
        scheduledExecutorService.scheduleAtFixedRate(r, 1, 2, TimeUnit.SECONDS);

        // 固定的周期，任务执行完毕后，延迟delay秒再执行
        scheduledExecutorService.scheduleWithFixedDelay(r, 1, 2, TimeUnit.SECONDS);

        // 实现类方法


        // shutdownNow会强制关闭

        // 线程池shutdown后，是否仍然定时执行存在的任务
        System.out.println(scheduledExecutorService.getContinueExistingPeriodicTasksAfterShutdownPolicy());

        // 线程池shutdown后，是否仍然执行正在执行的任务
        System.out.println(scheduledExecutorService.getExecuteExistingDelayedTasksAfterShutdownPolicy());

        scheduledExecutorService.shutdown();

    }
}
