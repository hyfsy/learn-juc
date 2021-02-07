package com.hyf.juc.pattern.observer;

/**
 * @author baB_hyf
 * @date 2020/12/19
 */
public class RunnableEvent {

    final ObserverRunnable.RunnableState state;
    final Thread thread;
    final Throwable cause;

    public RunnableEvent(ObserverRunnable.RunnableState state, Thread thread, Throwable cause) {
        this.state = state;
        this.thread = thread;
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "RunnableEvent is publish," +
                " state:[" + state + "]," +
                " thread:[" + thread + "]," +
                " cause:[" + cause + "]"
                ;
    }
}
