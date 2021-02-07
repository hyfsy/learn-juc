package com.hyf.juc.hello;

/**
 * 通知唤醒机制
 * synchronized wait notify notifyAll
 * lock.lock() await signal signalAll
 *
 * @author baB_hyf
 * @date 2020/12/06
 */

public class ProducerTest {

    private int count = 0;

    public static void main(String[] args) {
        ProducerTest producer = new ProducerTest();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    producer.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    producer.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
    }

    public synchronized void increment() throws InterruptedException {
        while (count == 10) {
            this.wait();
        }

        count++;
        System.out.println(Thread.currentThread().getName() + " : " + count);
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        while (count == 0) {
            this.wait();
        }

        count--;
        System.out.println(Thread.currentThread().getName() + " : " + count);
        this.notifyAll();
    }
}
