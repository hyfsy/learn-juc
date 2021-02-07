package com.hyf.juc.test.three;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class Measure implements Runnable {

    private final Atmospheric atmospheric;
    private final int         count;

    public Measure(Atmospheric atmospheric, int count) {
        this.atmospheric = atmospheric;
        this.count = count;
    }

    @Override
    public void run() {
        int i = 1;
        while (i <= count) {
            atmospheric.write(i++);
        }
    }
}
