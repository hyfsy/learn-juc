package com.hyf.juc.sample.future;

/**
 * @author baB_hyf
 * @date 2021/03/15
 */
public class FutureData implements Data {

    private final Object lock = new Object();

    private RealData realData = null;

    @Override
    public String getValue() {
        synchronized (lock) {
            while (this.realData == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }

        return this.realData.getValue();
    }

    public void setRealData(RealData realData) {
        if (this.realData != null) {
            return;
        }

        synchronized (lock) {
            this.realData = realData;
            lock.notifyAll();
        }
    }
}
