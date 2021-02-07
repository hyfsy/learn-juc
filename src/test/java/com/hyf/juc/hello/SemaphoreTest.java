package com.hyf.juc.hello;

import java.util.concurrent.Semaphore;

/**
 * 信号量测试
 * <p>
 * 用于多个共享资源的互斥使用和并发线程的数量控制
 *
 * @author baB_hyf
 * @date 2020/12/06
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3); // 资源数量

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 抢占到资源");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
