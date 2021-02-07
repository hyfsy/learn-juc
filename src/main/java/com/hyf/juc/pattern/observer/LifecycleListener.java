package com.hyf.juc.pattern.observer;

/**
 * @author baB_hyf
 * @date 2020/12/19
 */
public interface LifecycleListener {

    void onEvent(RunnableEvent event);
}
