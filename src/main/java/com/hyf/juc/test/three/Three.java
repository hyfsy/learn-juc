package com.hyf.juc.test.three;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class Three {

    public static void main(String[] args) {
        Atmospheric atmospheric = new Atmospheric();
        Measure measure = new Measure(atmospheric, 100);
        Reader reader = new Reader(atmospheric, 100);

        new Thread(measure).start();
        new Thread(reader).start();
    }
}
