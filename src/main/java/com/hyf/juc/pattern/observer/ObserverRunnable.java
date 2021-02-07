package com.hyf.juc.pattern.observer;

/**
 * @author baB_hyf
 * @date 2020/12/19
 */
public abstract class ObserverRunnable implements Runnable {

    protected final LifecycleListener lifecycleListener;

    public ObserverRunnable(LifecycleListener lifecycleListener) {
        this.lifecycleListener = lifecycleListener;
    }

    protected void notifyChange(final RunnableEvent event) {
        lifecycleListener.onEvent(event);
    }

    public enum RunnableState {
        RUNNING, ERROR, DONE;
    }

}
