package com.hyf.juc.test;

/**
 * join的神奇用法
 *
 * @author baB_hyf
 * @date 2020/12/17
 */
public class Test_Join {

    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            Thread thread = new Thread(() -> System.out.println(Thread.currentThread().getName()));
            thread.start();
            threads[i] = thread;
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("结束"); // main线程会等待所有的线程执行完后结束，其他的多个线程会并行执行
    }
}
