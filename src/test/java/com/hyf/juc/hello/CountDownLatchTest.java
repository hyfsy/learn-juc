package com.hyf.juc.hello;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁测试
 *
 * @author baB_hyf
 * @date 2020/12/06
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        int count = 5;

        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "：invoke");
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println("结束");
    }
}
