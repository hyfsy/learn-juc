package com.hyf.juc.hello;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 *
 * @author baB_hyf
 * @date 2020/12/06
 */
public class ReadWriteLockTest {

    private Map<String, String> cache         = new HashMap<>();
    private ReadWriteLock       readWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {

        ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                String name = Thread.currentThread().getName();
                System.out.println(name + " 开始设置数据：" + finalI);
                readWriteLockTest.set(String.valueOf(finalI), name);
                System.out.println(name + " 设置数据完毕");
            }).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                String name = Thread.currentThread().getName();
                System.out.println(name + " 开始读取数据");
                String res = readWriteLockTest.get(String.valueOf(finalI));
                System.out.println(name + " 读取数据完毕：" + res);
            }).start();
        }
    }

    public String get(String key) {
        readWriteLock.readLock().lock();
        try {
            return cache.get(key);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void set(String key, String value) {
        readWriteLock.writeLock().lock();
        try {
            cache.put(key, value);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
