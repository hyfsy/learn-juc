package com.hyf.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * boolean tryLock();
 * <p>
 * 尝试一次获取锁，成功返回true,否则返回false
 *
 * @author baB_hyf
 * @date 2020/12/12
 */
public class Lock_TryLockNoWait {

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Lock_TryLockNoWait lockTest2 = new Lock_TryLockNoWait();

        new Thread(lockTest2::tryLockNoWait).start();
        new Thread(lockTest2::tryLockNoWait).start();
    }

    public void tryLockNoWait() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " 开始获取锁：" + System.currentTimeMillis());
        if (lock.tryLock()) {
            try {
                System.out.println(name + " 获取锁成功，开始执行！");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }
        else {
            System.out.println(name + " 未获取到锁");
        }
        System.out.println(name + " 获取锁结束：" + System.currentTimeMillis());
    }

}
