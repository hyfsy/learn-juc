package com.hyf.juc.test;

/**
 * @author baB_hyf
 * @date 2022/07/18
 */
public class Test_Join3 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (t1) {
                t1.notify(); // lead to t1.join() stopped and isAlive() still false, so still blocked
            }
        });
        t2.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("stop");
    }
}
