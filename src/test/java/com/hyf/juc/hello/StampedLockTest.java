package com.hyf.juc.hello;

import java.util.concurrent.locks.StampedLock;

/**
 * @author baB_hyf
 * @date 2020/12/21
 */
public class StampedLockTest {

    public static void main(String[] args) {
        StampedLock lock = new StampedLock();

        long l = lock.writeLock();

        new Thread(() -> {
            long l1 = lock.writeLock();
            System.out.println(111);
        }).start();
    }
}
