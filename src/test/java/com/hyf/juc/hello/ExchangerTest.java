package com.hyf.juc.hello;

import java.util.concurrent.Exchanger;

/**
 * 交换器测试
 * <p>
 * 多个线程也可以交换
 *
 * @author baB_hyf
 * @date 2020/12/21
 */
public class ExchangerTest {

    public static void main(String[] args) {
        Exchanger<Object> exchanger = new Exchanger<>();

        new Thread(() -> {
            String name = Thread.currentThread().getName();
            try {
                Object a = new Object();
                System.out.println(name + " : " + a);
                Object exchange = exchanger.exchange(a);
                System.out.println(name + " : " + exchange);
                // exchanger.exchange("aaa", 1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            String name = Thread.currentThread().getName();
            try {
                Object b = new Object();
                ;
                System.out.println(name + " : " + b);
                Object exchange = exchanger.exchange(b);
                System.out.println(name + " : " + exchange);
                // exchanger.exchange("aaa", 1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
