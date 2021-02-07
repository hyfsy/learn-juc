package com.hyf.juc.test.two;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class Reader implements Runnable {

    private final Atmospheric atmospheric;
    private final int count;

    public Reader(Atmospheric atmospheric, int count) {
        this.atmospheric = atmospheric;
        this.count = count;
    }

    @Override
    public void run() {
        int i = 1;
        while (i <= count) {
            atmospheric.read(i++);
        }
    }
}
