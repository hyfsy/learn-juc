package com.hyf.juc.lock;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 *
 * @author baB_hyf
 * @date 2020/12/06
 */
public class Lock_ReadWrite_Notice {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Test
    public void notBlock() {
        readWriteLock.writeLock().lock();
        readWriteLock.readLock().lock();
        System.out.println(1);
    }

    @Test
    public void block() {
        // readWriteLock.writeLock().lock(); // must get write lock first ???
        readWriteLock.readLock().lock();
        readWriteLock.writeLock().lock(); // block
        System.out.println(1);
    }
}
