package com.hyf.juc.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author baB_hyf
 * @date 2020/12/26
 */
public class ConditionTest {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);
        Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                condition.await();
                System.out.println(1);
                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        Thread main = Thread.currentThread();

        // condition.signal();
        lock.lock();

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            main.interrupt();
            condition.signal();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signal();

            lock.unlock();
            System.out.println("unlock");
        });
        t1.start();

        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // IntStream.range(0, 10).forEach(i -> {
        //     new Thread(() -> {
        //         try {
        //             condition.await();
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }).start();
        // });
    }
}
