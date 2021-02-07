package com.hyf.juc.aqs;

import java.util.concurrent.locks.LockSupport;

/**
 * @author baB_hyf
 * @date 2020/12/27
 */
public class A {

    public static void main(String[] args) throws InterruptedException {
        String s = "asdf";
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("3" + Thread.interrupted());
                LockSupport.park(s);
                System.out.println("1" + Thread.interrupted());
            }
        });
        t1.start();

        Thread.sleep(1000);

        t1.interrupt();
        System.out.println("2" + t1.isInterrupted());
        LockSupport.unpark(t1);
    }
}
