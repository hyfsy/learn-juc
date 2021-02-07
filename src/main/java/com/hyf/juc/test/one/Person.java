package com.hyf.juc.test.one;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class Person implements Runnable {

    private final String name;
    private final Cave   cave;

    public Person(Cave cave, String name) {
        this.cave = cave;
        this.name = name;
    }

    @Override
    public void run() {
        cave.guoshandong(this);
    }

    public String getName() {
        return name;
    }

}
