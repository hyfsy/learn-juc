package com.hyf.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * void lockInterruptibly() throws InterruptedException;
 * <p>
 * 加锁时可被外部中断
 *
 * @author baB_hyf
 * @date 2020/12/12
 */
public class Lock_Lock_Interruptibility {

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Lock_Lock_Interruptibility lock_lock_interruptibility = new Lock_Lock_Interruptibility();

        Thread t1 = new Thread(lock_lock_interruptibility::lockCannotInterrupt);
        t1.start();
        t1.interrupt();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        Thread t2 = new Thread(lock_lock_interruptibility::lockCanInterrupt);
        t2.start();
        t2.interrupt();

    }

    public void lockCannotInterrupt() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " 执行不可中断上锁方法");
        lock.lock();
        try {
            System.out.println(name + " 获取锁执行");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        } finally {
            lock.unlock();
        }
        System.out.println(name + " 执行不可中断上锁方法结束");
        System.out.println("-------------");
    }

    public void lockCanInterrupt() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " 执行可中断上锁方法");
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(name + " 获取锁执行");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(name + " 获取锁时被中断");
        }
        System.out.println(name + " 执行可中断上锁方法结束");
        System.out.println("-------------");
    }


}
