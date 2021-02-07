package com.hyf.juc.hello;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 周期栅栏测试
 * <p>
 * CyclicBarrier是可以循环多次的
 *
 * @author baB_hyf
 * @date 2020/12/06
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int count = 5;

        // 指定数量的await次数后，执行传入的Runnable
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count, () -> System.out.println("await集齐"));

        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "：invoke");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " after await");
            }).start();
        }

        System.out.println(1);
    }
}
