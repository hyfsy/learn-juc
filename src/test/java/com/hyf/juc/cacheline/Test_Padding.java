package com.hyf.juc.cacheline;

/**
 * 缓存行对齐
 *
 * @author baB_hyf
 * @date 2021/01/26
 */
public class Test_Padding {

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_000_000; i++) {
                arr[0].x = 1;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_000_000; i++) {
                arr[1].x = 1;
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - start) / 1_000_000);
    }

    static class T {
        public volatile long p1, p2, p3, p4, p5, p6, p7; // 64字节
        public volatile long x = 0L;
    }
}
