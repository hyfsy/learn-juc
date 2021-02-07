package com.hyf.juc.hello;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程通信-增强
 * <p>
 * 判断、干活、通知
 *
 * @author baB_hyf
 * @date 2020/12/06
 */
public class ConditionTest {

    private int       flag = 1;
    private Lock      lock = new ReentrantLock(); // 一把锁配多把钥匙
    private Condition c1   = lock.newCondition();
    private Condition c2   = lock.newCondition();
    private Condition c3   = lock.newCondition();

    public static void main(String[] args) {
        ConditionTest conditionTest = new ConditionTest();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                conditionTest.awork(2);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                conditionTest.bwork(2);
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                conditionTest.cwork(2);
            }
        }).start();
    }


    public void awork(int count) {
        lock.lock();
        try {
            while (flag != 1) {
                try {
                    c1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < count; i++) {
                System.out.println("AA");
            }
            flag = 2;
            c2.signal();
        } finally {
            lock.unlock();
        }
    }

    public void bwork(int count) {
        lock.lock();
        try {
            while (flag != 2) {
                try {
                    c2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < count; i++) {
                System.out.println("BB");
            }
            flag = 3;
            c3.signal();
        } finally {
            lock.unlock();
        }
    }

    public void cwork(int count) {
        lock.lock();
        try {
            while (flag != 3) {
                try {
                    c3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < count; i++) {
                System.out.println("CC");
            }
            flag = 1;
            c1.signal();
        } finally {
            lock.unlock();
        }
    }


}
