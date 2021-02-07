package com.hyf.juc.foursync;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 倒计时门闩
 * 设置一个计数器，调用countDown递减
 * 调用await时，当前线程会等待，直到指定数的countDown被调用
 * <p>
 * 一次性的，用于一个主线程与其他多个子线程之间的通行
 * <p>
 * 主要通过AQS实现
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class CountDownLatch_Use {

    public static void main(String[] args) {
        int count = 3;

        CountDownLatch countDownLatch = new CountDownLatch(count);

        IntStream.range(0, count).forEach(i -> new Thread(() -> {
            String name = Thread.currentThread().getName();
            try {
                long l = new Random().nextInt(5) * 1000L;
                System.out.println(name + " 任务执行时间：" + l);
                Thread.sleep(l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println(name + " " + countDownLatch.getCount()); // 变得不安全了
            System.out.println(name + " " + countDownLatch.toString()); // 变得不安全了
        }).start());

        try {
            countDownLatch.await();
            System.out.println("await返回");
            countDownLatch.await(10, TimeUnit.SECONDS); // 直接返回
            System.out.println("再次调用返回");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
