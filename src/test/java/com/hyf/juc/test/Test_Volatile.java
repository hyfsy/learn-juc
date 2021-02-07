package com.hyf.juc.test;

/**
 * volatile的功能展示
 *
 * @author baB_hyf
 * @date 2020/12/15
 */
public class Test_Volatile {

    public static boolean flag = false;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("开始");
            while (!flag) {
            }
            System.out.println("停止");
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("开始修改flag");
            flag = true;
            System.out.println("修改flag结束");
        }).start();
    }
}
