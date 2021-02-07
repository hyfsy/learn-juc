package com.hyf.juc.test;

/**
 * @author baB_hyf
 * @date 2020/12/23
 */
public class Test_Join2 {

    public static void main(String[] args) {
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
