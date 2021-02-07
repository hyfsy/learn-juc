package com.hyf.juc.test.three;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class Atmospheric {

    private final Object readLock  = new Object();
    private final Object writeLock = new Object();

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private String oC           = "";
    private String humidity     = "";
    private String windVelocity = "";

    private boolean isReady  = false;
    private boolean isReaded = true;

    public void write(int i) {
        synchronized (writeLock) {

            if (i > 100) {
                return;
            }

            while (isReady) {
                try {
                    writeLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            oC = random.nextInt() + " oC";
            humidity = random.nextInt() + " sd";
            windVelocity = "东南风" + random.nextInt() + " 级";

            isReaded = false;
            isReady = true;

            synchronized (readLock) {
                readLock.notify();
            }
        }
    }

    public void read(int i) {

        synchronized (readLock) {

            while (isReaded) {
                try {
                    readLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(i + "- 温度: " + oC);
            System.out.println(i + "- 湿度: " + humidity);
            System.out.println(i + "- 风速: " + windVelocity);

            isReaded = true;
            isReady = false;

            synchronized (writeLock) {
                writeLock.notify();
            }
        }
    }
}
