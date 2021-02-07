package com.hyf.juc.mix;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.DoubleAdder;

/**
 * @author baB_hyf
 * @date 2020/12/20
 */
public class AtomicTest {

    public static void main(String[] args) {

        AtomicIntegerArray arr = new AtomicIntegerArray(7);
        int i = arr.get(3);

        AtomicMarkableReference<Object> markableReference = new AtomicMarkableReference<>(1, true);

        DoubleAccumulator doubleAccumulator = new DoubleAccumulator((a, b) -> {
            return b;
        }, 222);
        System.out.println(doubleAccumulator.get());
        doubleAccumulator.accumulate(111);
        System.out.println(doubleAccumulator.getThenReset());
        System.out.println(doubleAccumulator.get());
        doubleAccumulator.accumulate(111);
        System.out.println(doubleAccumulator.get());
        doubleAccumulator.reset();
        System.out.println(doubleAccumulator.get());

        System.out.println("=====");

        DoubleAdder doubleAdder = new DoubleAdder();
        System.out.println(doubleAdder.sum());
        doubleAdder.add(111);
        System.out.println(doubleAdder.sum());
        System.out.println(doubleAdder.sumThenReset());
        System.out.println(doubleAdder.sum());
    }
}
