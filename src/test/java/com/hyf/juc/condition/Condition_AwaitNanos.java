package com.hyf.juc.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * long awaitNanos(long nanosTimeout) throws InterruptedException;
 * <p>
 * 等待指定的纳秒时间，为纳秒的原因是减少返回时间的不准确度
 * <p>
 * 传入的nanosTimeout值的估计值减去等待该方法返回所花费的时间。
 * 可以使用一个正值作为该方法的后续调用的参数，以完成所需的等待时间。
 * 小于或等于0的值表示没有剩余时间
 *
 * @author baB_hyf
 * @date 2020/12/12
 */
public class Condition_AwaitNanos {

    private Lock      lock = new ReentrantLock();
    private Condition c1   = lock.newCondition();

    public static void main(String[] args) {
        Condition_AwaitNanos condition_awaitNanos = new Condition_AwaitNanos();

        Thread t1 = new Thread(() -> condition_awaitNanos.lock(() -> {
            try {
                System.out.println("执行await()方法");
                long timeout = 2;
                TimeUnit unit = TimeUnit.SECONDS;
                long nanos = unit.toNanos(timeout);

                long l = condition_awaitNanos.c1.awaitNanos(nanos);
                System.out.println(TimeUnit.NANOSECONDS.toSeconds(l));
                System.out.println(TimeUnit.NANOSECONDS.toMicros(l));
                System.out.println(l);

                System.out.println("--------------");

                long l2 = condition_awaitNanos.c1.awaitNanos(nanos);
                System.out.println(TimeUnit.NANOSECONDS.toSeconds(l2));
                System.out.println(TimeUnit.NANOSECONDS.toMicros(l2));
                System.out.println(l2);

                System.out.println("await()方法等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        t1.start();

        Thread t2 = new Thread(() -> condition_awaitNanos.lock(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition_awaitNanos.c1.signal();
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
