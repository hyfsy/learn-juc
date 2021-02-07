package com.hyf.juc.pattern.observer;

import java.util.Arrays;

/**
 * 通过观察者模式被动的监控线程中的信息
 *
 * @author baB_hyf
 * @date 2020/12/19
 */
public class Test {

    public static void main(String[] args) {

        new ThreadLifecycleObserver().concurrentQuery(Arrays.asList("1", "2"));
    }
}
