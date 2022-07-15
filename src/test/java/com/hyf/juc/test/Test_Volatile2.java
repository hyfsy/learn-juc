package com.hyf.juc.test;

/**
 * 在非DEBUG环境，由于IDE自动创建一条名为Monitor Ctrl-Break的线程（从名字看应该是监控Ctrl-Break中断信号的）
 * 而导致while循环无法结束，改为大于2或者用Thread::join()方法代替可以解决该问题。
 *
 * @author baB_hyf
 * @date 2021/04/19
 */
public class Test_Volatile2 {
    private static final   int THREADS_COUNT = 20;
    public static volatile int race          = 0;

    public static void increase() {
        race++;
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }
        // 等待所有累加线程都结束
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(race);
    }
}
