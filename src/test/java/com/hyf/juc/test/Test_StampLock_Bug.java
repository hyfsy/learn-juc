package com.hyf.juc.test;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * StampLock的小陷阱
 *
 * @author baB_hyf
 * @date 2021/03/16
 */
public class Test_StampLock_Bug {
    static final StampedLock lock           = new StampedLock();
    static       Thread[]    holdCpuThreads = new Thread[3];

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            long readLong = lock.writeLock();
            LockSupport.parkNanos(600000000000L);
            lock.unlockWrite(readLong);
        }).start();

        Thread.sleep(100);

        for (int i = 0; i < 3; ++i) {
            holdCpuThreads[i] = new Thread(new HoldCPUReadThread());
            holdCpuThreads[i].start();
        }

        Thread.sleep(5000);

        System.out.println("start interrupt");

        //线程中断后,会占用CPU
        for (int i = 0; i < 3; ++i) {
            holdCpuThreads[i].interrupt();
        }
    }

    private static class HoldCPUReadThread implements Runnable {
        @Override
        public void run() {
            long lockr = lock.readLock();
            System.out.println(Thread.currentThread().getName() + "获得读锁");
            lock.unlockRead(lockr);
        }
    }
}
