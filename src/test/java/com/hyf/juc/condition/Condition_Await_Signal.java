package com.hyf.juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * void await() throws InterruptedException; // 使当前线程进入阻塞状态，放弃持有的锁
 * <p>
 * void signal(); // 唤醒某一个调用await进入等待的线程
 * void signalAll(); // 唤醒所有调用await进入等待的线程
 *
 * @author baB_hyf
 * @date 2020/12/12
 */
public class Condition_Await_Signal {

    private final Lock      lock = new ReentrantLock();
    private final Condition c1   = lock.newCondition();
    private final Condition c2   = lock.newCondition();

    public static void main(String[] args) {
        Condition_Await_Signal condition_await_signal = new Condition_Await_Signal();

        new Thread(() -> condition_await_signal.lock(() -> {
            try {
                System.out.println("执行await()方法");
                condition_await_signal.c1.await();
                System.out.println("await()方法等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).start();
        new Thread(() -> condition_await_signal.lock(() -> {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("开始发送信号");
            condition_await_signal.c1.signal();
            System.out.println("信号发送完毕");

        })).start();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(() -> condition_await_signal.lock(() -> {
            String name = Thread.currentThread().getName();
            try {
                System.out.println(name + " 执行await()方法");
                condition_await_signal.c2.await();
                System.out.println(name + " await()方法等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).start();
        new Thread(() -> condition_await_signal.lock(() -> {
            String name = Thread.currentThread().getName();
            try {
                System.out.println(name + " 执行await()方法");
                condition_await_signal.c2.await();
                System.out.println(name + " await()方法等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).start();
        new Thread(() -> condition_await_signal.lock(() -> {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("开始发送信号");
            condition_await_signal.c2.signalAll();
            System.out.println("信号发送完毕");

        })).start();
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
