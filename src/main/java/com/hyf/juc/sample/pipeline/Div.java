package com.hyf.juc.sample.pipeline;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author baB_hyf
 * @date 2021/03/15
 */
public class Div implements Runnable {

    public static BlockingQueue<Msg> queue = new LinkedBlockingQueue<>();

    @Override
    public void run() {
        while (true) {
            try {
                Msg msg = queue.take();
                msg.i = msg.i / 2;
                System.out.println("result: " + msg.str + " = " + msg.i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
