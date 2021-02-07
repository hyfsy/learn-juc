package com.hyf.juc.hello;

/**
 * @author baB_hyf
 * @date 2021/01/02
 */
public class SynchronizedTest {

    private static final Object lock = new Object();

    public static void main(String[] args) {

        synchronized (lock) {
            System.out.println(1);
        }
    }
}
