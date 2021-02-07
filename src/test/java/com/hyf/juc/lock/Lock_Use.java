package com.hyf.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * void lock();
 * void unlock();
 * <p>
 * 可重入锁，加锁几次就必须要释放几次
 *
 * @author baB_hyf
 * @date 2020/12/12
 */
public class Lock_Use {

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Lock_Use lockTest = new Lock_Use();


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                lockTest.lock1();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 测试释放锁
            // for (int i = 0; i < 10; i++) {
            //     lockTest.lock.unlock();
            // }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                lockTest.lock2();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void lock1() {
        lock.lock();
        try {
            System.out.println(1);
        } finally {
            // lock.unlock();
        }
    }

    public void lock2() {
        lock.lock();
        try {
            System.out.println(2);
        } finally {
            lock.unlock();
        }
    }


}
