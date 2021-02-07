package com.hyf.juc.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的最大加锁数
 *
 * @author baB_hyf
 * @date 2020/12/15
 */
public class Test_LockMaximum {

    public static void main(String[] args) {
        Lock lock = new ReentrantReadWriteLock().readLock();
        int count = 1 << 16; // 65536 读写锁有限制，因为锁被分割了
        for (int i = 0; i < count; i++) {
            lock.lock();
        }
        for (int i = 0; i < count; i++) {
            lock.unlock();
        }
    }
}
