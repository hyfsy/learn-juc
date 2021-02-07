package com.hyf.juc.mix;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class PoolTest {

    @Test
    public void testTimeout() throws InterruptedException {
        ThreadPoolExecutor executor = getPool();
        // executor.execute(() -> {
        //     try {
        //         Thread.sleep(5000);
        //     } catch (InterruptedException e) {
        //     }
        // });
        IntStream.range(0, 4).forEach(i -> {
            Runnable runnable = () -> {
                try {
                    Thread.sleep(3000);
                    System.out.println(1);
                } catch (InterruptedException e) {
                }
            };
            System.out.println(runnable);
            executor.execute(runnable);
        });

        BlockingQueue<Runnable> queue = executor.getQueue();
        Runnable run = queue.take();
        System.out.println(run);

        Thread.currentThread().join();
    }


    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor executor = getPool();
        executor.setCorePoolSize(4);
        executor.allowCoreThreadTimeOut(true);

        IntStream.range(0, 6).boxed().forEach(i -> {
            executor.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1");
            });
        });


        // List<Callable<Integer>> list = IntStream.range(0, 9).boxed().map(i ->
        //         (Callable<Integer>)() -> {
        //         try {
        //             Thread.sleep(1000L * i);
        //             if (i >= 6) {
        //                 throw new RuntimeException("i -> " + i);
        //             }
        //             return i;
        //         } catch (Exception e) {
        //             e.printStackTrace();
        //             return null;
        //         }
        // }).collect(Collectors.toList());

        // List<Future<Integer>> futures = executor.invokeAll(list, 1, TimeUnit.SECONDS);
        // for (Future<Integer> future : futures) {
        //     try {
        //         System.out.println(future.get());
        //     } catch (ExecutionException e) {
        //         e.printStackTrace();
        //     }
        // }

        // try {
        //     System.out.println(executor.invokeAny(list));
        // } catch (ExecutionException e) {
        //     e.printStackTrace();
        // }

        //
        // 测试getAll 等所有结果都返回才返回
        // List<Future<Integer>> futures = executor.invokeAll(list);
        // System.out.println("has finished");
        // for (Future<Integer> future : futures) {
        //     try {
        //         System.out.println(future.get());
        //     } catch (ExecutionException e) {
        //         e.printStackTrace();
        //     }
        // }

        // executor.shutdown();



        // executor.shutdownNow();
        // System.out.println("shutdown");
        // System.out.println(executor.isShutdown());
        // System.out.println(executor.isTerminating());
        // // TimeUnit.SECONDS.sleep(1);
        // System.out.println(executor.isTerminated());
        // // executor.awaitTermination(10, TimeUnit.SECONDS);
        // System.out.println("termination");
    }

    public static ThreadPoolExecutor getPool() {
        return new ThreadPoolExecutor(
                1,
                3,
                100,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                new CustomThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    static class CustomThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }
}
