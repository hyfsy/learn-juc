package com.hyf.juc.hello;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 相位器
 * <p>
 * 可以替代 CountDownLatch 和 CyclicBarrier
 *
 * @author baB_hyf
 * @date 2020/12/22
 */
public class PhaserTest {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(3); // 初始化三个竞争者

        for (int i = 0; i < 4; i++) {
            new Thread(new PhaserTask(phaser)).start();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                phaser.arriveAndAwaitAdvance();
            }).start();
        }

        // phaser.awaitAdvance(phaser.getPhase()); // phase相同时，并且都到达，则会停止等待，phase不相同会立即返回

        System.out.println("main " + phaser.isTerminated()); // phaser归0会自动终止

        phaser.forceTermination(); // 强制终止
    }

    static class PhaserTask implements Runnable {
        private final Phaser phaser;

        public PhaserTask(Phaser phaser) {
            this.phaser = phaser;
            int register = phaser.register(); // 将自己注册进竞争者
            // int i = phaser.bulkRegister(2);// 一次注册多个
            System.out.println("Phase Number: " + register);
            System.out.println("Register Number: " + phaser.getRegisteredParties());
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();

            System.out.println(name + " 开始等待-1");
            System.out.println(name + " -Arrived- " + phaser.getArrivedParties()); // 到达的线程数量
            System.out.println(name + " -UnArrived- " + phaser.getUnarrivedParties()); // 未到达的数量
            phaser.arriveAndAwaitAdvance();
            System.out.println(name + " 等待结束-1");

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(phaser.isTerminated());

            System.out.println("===");

            System.out.println(name + " 开始等待-2");
            phaser.arriveAndAwaitAdvance();
            System.out.println(name + " 等待结束-2");

            // phaser.arriveAndDeregister(); // 取消注册
            // phaser.arrive(); // 只是到达，不会阻塞
        }
    }
}
