package com.hyf.juc.sample.future;

/**
 * Future模式
 *
 * @author baB_hyf
 * @date 2021/03/15
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        Data data = Client.request("hello");
        Thread.sleep(1000);
        System.out.println("main thread do other thing...");
        System.out.println("get data: " + data.getValue());
    }
}
