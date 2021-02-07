package com.hyf.juc.cl;

/**
 * @author baB_hyf
 * @date 2020/12/20
 */
public class Test_Change {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());

        new Thread(() -> {
            System.out.println(Thread.currentThread().getContextClassLoader());
        }).start();
    }
}
