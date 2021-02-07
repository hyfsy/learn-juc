package com.hyf.juc.test;

/**
 * @author baB_hyf
 * @date 2021/02/06
 */
public class Test_Daemon {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Thread t2 = new Thread(() -> {
                System.out.println("内部线程的守护状态：" + Thread.currentThread().isDaemon());
            });
            t2.start();
        });
        t1.setDaemon(true);
        t1.start();

        t1.join();
    }
}
