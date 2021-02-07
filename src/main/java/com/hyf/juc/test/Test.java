package com.hyf.juc.test;

/**
 * @author baB_hyf
 * @date 2021/01/25
 */
public class Test {

    public final int a;

    public Test() {
        a = 1;
        // a = 2;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread();
        t.join();
        Thread.yield();
    }

}
