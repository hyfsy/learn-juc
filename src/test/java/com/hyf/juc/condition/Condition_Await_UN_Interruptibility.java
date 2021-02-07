package com.hyf.juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * void await() throws InterruptedException; // 等待时会被中断
 * <p>
 * void awaitUninterruptibly(); // 等待时不会被中断
 *
 * @author baB_hyf
 * @date 2020/12/12
 */
public class Condition_Await_UN_Interruptibility {

    private final Lock      lock = new ReentrantLock();
    private final Condition c1   = lock.newCondition();

    public static void main(String[] args) {
        Condition_Await_UN_Interruptibility condition_await_un_interruptibility = new Condition_Await_UN_Interruptibility();

        Thread t1 = new Thread(() -> condition_await_un_interruptibility.lock(() -> {
            try {
                System.out.println("执行await()方法");
                condition_await_un_interruptibility.c1.await();
                System.out.println("await()方法等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("await()方法被中断");
            }
        }));
        t1.start();
        t1.interrupt();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Thread t2 = new Thread(() -> condition_await_un_interruptibility.lock(() -> {
            System.out.println("执行await()方法");
            condition_await_un_interruptibility.c1.awaitUninterruptibly();
            System.out.println("await()方法等待结束");
        }));
        t2.start();
        t2.interrupt();


        try {
            Thread.sleep(2000);
            condition_await_un_interruptibility.lock(condition_await_un_interruptibility.c1::signalAll);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
