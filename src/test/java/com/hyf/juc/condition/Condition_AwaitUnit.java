package com.hyf.juc.condition;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * boolean awaitUntil(Date deadline) throws InterruptedException;
 * <p>
 * 指定时间之前被唤醒则返回true,否则返回false
 *
 * @author baB_hyf
 * @date 2020/12/12
 */
public class Condition_AwaitUnit {

    private final Lock      lock = new ReentrantLock();
    private final Condition c1   = lock.newCondition();

    public static void main(String[] args) {
        Condition_AwaitUnit condition_awaitUnit = new Condition_AwaitUnit();

        Thread t1 = new Thread(() -> condition_awaitUnit.lock(() -> {
            try {
                System.out.println("执行await()方法");

                Date date = new Date();
                long l1 = TimeUnit.SECONDS.toMillis(2);
                Date d1 = new Date(date.getTime() + l1);
                boolean r1 = condition_awaitUnit.c1.awaitUntil(d1);
                System.out.println(r1);

                System.out.println("--------------");

                long l2 = TimeUnit.SECONDS.toMillis(4);
                Date d2 = new Date(date.getTime() + l2);
                boolean r2 = condition_awaitUnit.c1.awaitUntil(d2);
                System.out.println(r2);

                System.out.println("await()方法等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        t1.start();

        Thread t2 = new Thread(() -> condition_awaitUnit.lock(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition_awaitUnit.c1.signal();
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
