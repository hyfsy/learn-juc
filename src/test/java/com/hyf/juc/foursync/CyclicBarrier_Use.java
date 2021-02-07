package com.hyf.juc.foursync;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * 循环屏障
 * <p>
 * 构造函数传入一个parties指定指定数个线程同时等待后，屏障才消失。还可传入一个Runnable,在屏障消失后执行
 * 调用await的线程会进入等待，并递减count数量，到达0时，所有线程继续执行，并重置count为parties
 * <p>
 * 所有的线程要么都成功，要么都失败（一个线程中断、错误、超时就会失败）
 * <p>
 * 循环屏障是可以不断使用的，并非一次性。主要应用于多个线程间的同步
 * <p>
 * 主要使用 ReentrantLock和 Condition实现
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class CyclicBarrier_Use {

    public static void main(String[] args) {
        int parties = 3;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties, () -> System.out.println("屏障消失"));

        System.out.println("parties：" + cyclicBarrier.getParties());

        IntStream.rangeClosed(0, parties - 1).forEach((i) -> new Thread(() -> {
            String name = Thread.currentThread().getName();
            long l = new Random().nextInt(5) * 1000L;
            System.out.println(name + " 任务等待时间：" + l);
            try {
                Thread.sleep(l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(name + " 等待线程数量：" + cyclicBarrier.getNumberWaiting());
                int state = cyclicBarrier.await();
                System.out.println(name + " state：" + state); // 缺少的等待数量
                System.out.println("开始执行任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
                System.out.println(name + " 屏障被破坏 ");
            }
        }).start());


        try {
            int state = cyclicBarrier.await(2, TimeUnit.SECONDS); // 超时等待后抛出超时异常并会破坏屏障，其他等待线程会报错
            System.out.println("main state：" + state); // 缺少的等待数量
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            System.out.println("main 等待超时，屏障被破坏：" + cyclicBarrier.isBroken());
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cyclicBarrier.reset(); // 破坏屏障并开启下一轮

    }
}
