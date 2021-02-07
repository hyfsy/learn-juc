package com.hyf.juc.pattern.active;

/**
 * @author baB_hyf
 * @date 2020/12/24
 */
public class ScheduledThread extends Thread {

    private final ActivationQueue activationQueue;

    public ScheduledThread(ActivationQueue activationQueue) {
        this.activationQueue = activationQueue;
    }

    public void invoke(MethodRequest request) {
        activationQueue.put(request);
    }

    @Override
    public void run() {
        while (true) {
            activationQueue.take().execute();
        }
    }
}
