package com.hyf.juc.mix;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author baB_hyf
 * @date 2020/12/17
 */
public class MixTest {


    public static void main(String[] args) {


        AtomicInteger integer = new AtomicInteger();
        integer.lazySet(1);

        Thread.setDefaultUncaughtExceptionHandler((t,  e) -> {
            System.out.println("exception: " + e.getMessage());
        });

        StackTraceElement[] stackTrace = new Thread(() -> {
            System.out.println("start print stack trace");
        }).getStackTrace();
        Arrays.asList(stackTrace).forEach(System.out::println);

        // StackTraceElement element = new StackTraceElement();
        // System.out.println(element.isNativeMethod());

        System.out.println("main contextClassLoader: " + Thread.currentThread().getContextClassLoader());


        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

        // if (threadGroup != null) {
        //     throw new IllegalArgumentException();
        // }

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        System.out.println("defaultUncaughtExceptionHandler: " + defaultUncaughtExceptionHandler);
        t1.start();
        t1.isInterrupted();
        Thread.interrupted();
        new Thread(() -> {
            try {
                Thread.sleep(10_000);
                // t1.stop();
                threadGroup.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread[] threads = new Thread[threadGroup.activeCount()];
        int enumerate = threadGroup.enumerate(threads);
        System.out.println("thread count: " + enumerate);
        Arrays.asList(threads).forEach(System.out::println);

        threadGroup.list();
        // System.out.println();


        // new Thread(() -> {
        //     try {
        //         Thread.sleep(Integer.MAX_VALUE);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }).start();
        //
        // try {
        //     Thread.sleep(Integer.MAX_VALUE);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
    }
}
