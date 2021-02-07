package com.hyf.juc.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * boolean await(long time, TimeUnit unit) throws InterruptedException;
 * <p>
 * 指定时间内被唤醒则返回true,否则返回false
 *
 * @author baB_hyf
 * @date 2020/12/12
 */
public class Condition_AwaitWithTimeout {

    private final Lock      lock = new ReentrantLock();
    private final Condition c1   = lock.newCondition();

    public static void main(String[] args) {
        Condition_AwaitWithTimeout condition_awaitWithTimeout = new Condition_AwaitWithTimeout();

        Thread t1 = new Thread(() -> condition_awaitWithTimeout.lock(() -> {
            try {
                System.out.println("执行await()方法");

                boolean l = condition_awaitWithTimeout.c1.await(2, TimeUnit.SECONDS);
                System.out.println(l);

                System.out.println("--------------");

                boolean l2 = condition_awaitWithTimeout.c1.await(2, TimeUnit.SECONDS);
                System.out.println(l2);

                System.out.println("await()方法等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        t1.start();

        Thread t2 = new Thread(() -> condition_awaitWithTimeout.lock(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition_awaitWithTimeout.c1.signal();
        }));
        t2.start();
    }


    public void lock(Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }
}
