package com.hyf.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
 * <p>
 * 指定时间内自旋等待，获取到锁则返回true,否则返回false
 *
 * @author baB_hyf
 * @date 2020/12/12
 */
public class Lock_TryLockWait {

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Lock_TryLockWait lockTest2 = new Lock_TryLockWait();

        new Thread(() -> {
            try {
                lockTest2.tryLockWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                lockTest2.tryLockWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void tryLockWait() throws InterruptedException {
        String name = Thread.currentThread().getName();
        System.out.println(name + " 开始获取锁：" + System.currentTimeMillis());
        if (lock.tryLock(3, TimeUnit.SECONDS)) { // 尝试更改锁等待时间
            try {
                System.out.println(name + " 获取锁成功，开始执行！");
                Thread.sleep(2000);
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
