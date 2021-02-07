package com.hyf.juc.hello;

import java.util.concurrent.*;

/**
 * CompletionService测试
 * <p>
 * 解决 Future::take 可能先获取到一个长时间的任务，导致其他操作等待
 *
 * @author baB_hyf
 * @date 2020/12/23
 */
public class CompletionServiceTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        BlockingQueue<Future<Integer>> queue = new LinkedBlockingQueue<>();

        CompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorService, queue);

        Future<Integer> future = executorCompletionService.submit(() -> 3);

        Future<Integer> poll = executorCompletionService.take(); // 阻塞等待
        // executorCompletionService.poll(); // 不阻塞，无就返回null
        // executorCompletionService.poll(1, TimeUnit.SECONDS); // 等待指定时间，无就返回null

        System.out.println(future);
        System.out.println(poll);

        executorService.shutdown();
    }
}
