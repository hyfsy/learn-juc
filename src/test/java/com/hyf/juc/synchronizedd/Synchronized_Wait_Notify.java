package com.hyf.juc.synchronizedd;

/**
 * wait/notify/notifyAll 方法的调用前提 必须获取到该对象的monitor
 *
 * @author baB_hyf
 * @date 2020/12/12
 */
public class Synchronized_Wait_Notify {

    public static void main(String[] args) {
        Object obj = new Object();

        try {
            obj.wait();
        } catch (InterruptedException e) {
        } catch (IllegalMonitorStateException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj) {
                obj.notify();
            }
        }).start();

        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException e) {
            } catch (IllegalMonitorStateException e) {
                e.printStackTrace();
            }
        }

    }
}
