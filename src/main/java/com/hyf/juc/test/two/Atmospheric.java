package com.hyf.juc.test.two;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class Atmospheric {

    private String oC           = "";
    private String humidity     = "";
    private String windVelocity = "";

    private Lock      lock      = new ReentrantLock();
    private Condition readC = lock.newCondition();
    private Condition writeC = lock.newCondition();

    private boolean isReady  = false;
    private boolean isReaded = true;

    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public void write(int i) {

        lock.lock();

        if (i > 100) {
            return;
        }

        try {
            while (isReady) {
                try {
                    writeC.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                // TimeUnit.MILLISECONDS.sleep(300);
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            oC = random.nextInt() + " oC";
            humidity = random.nextInt() + " sd";
            windVelocity = "东南风" + random.nextInt() + " 级";

            isReaded = false;
            isReady = true;
            readC.signal();
        } finally {
            lock.unlock();
        }
    }

    public void read(int i) {
        lock.lock();
        try {

            while (isReaded) {
                try {
                    readC.await();
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
            writeC.signal();
        } finally {
            lock.unlock();
        }
    }


}
