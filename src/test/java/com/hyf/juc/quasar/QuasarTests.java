package com.hyf.juc.quasar;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * http://docs.paralleluniverse.co/quasar
 *
 * -javaagent:E:\study\.m2\co\paralleluniverse\quasar-core\0.7.10\quasar-core-0.7.10.jar
 *
 * @author baB_hyf
 * @date 2022/03/02
 */
public class QuasarTests {

    @Test
    public void testA() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(10000);
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("Thread use:" + (end - start) + " ms");
    }

    @Test
    public void testB() throws InterruptedException {

        co.paralleluniverse.strands.concurrent.CountDownLatch countDownLatch = new co.paralleluniverse.strands.concurrent.CountDownLatch(10000);
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            new Fiber<>(new SuspendableRunnable() {
                @Override
                public void run() throws SuspendExecution, InterruptedException {
                    Fiber.sleep(1000);
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println("Fiber use:" + (end - start) + " ms");
    }
}
