package com.hyf.juc.cacheline;

/**
 * @author baB_hyf
 * @date 2021/01/28
 */
public class Test_Disorder_2 {

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
