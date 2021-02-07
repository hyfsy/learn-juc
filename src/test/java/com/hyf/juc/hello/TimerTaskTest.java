package com.hyf.juc.hello;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author baB_hyf
 * @date 2020/12/10
 */
public class TimerTaskTest {

    public static void main(String[] args) {
        Timer timer = new Timer();
        for (int i = 0; i < 1000; i++) {
            TimerTask timerTask = new TimerTask() {

                @Override
                public void run() {
                    System.out.println("timer tasker schedule...");
                }
            };

            timer.schedule(timerTask, 2000, 3000);
        }

        // timer.cancel();
    }
}
