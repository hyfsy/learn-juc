package com.hyf.juc.test.one;

import java.util.stream.IntStream;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class One {

    public static void main(String[] args) {
        Cave cave = new Cave();
        IntStream.range(0, 9).boxed().map(String::valueOf).forEach(i-> {
            new Thread(new Person(cave, i)).start();
        });
    }
}
