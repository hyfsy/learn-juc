package com.hyf.juc.pattern.active;

/**
 * @author baB_hyf
 * @date 2020/12/24
 */
public final class ActiveObjectFactory {

    private ActiveObjectFactory() {
    }

    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActivationQueue queue = new ActivationQueue();
        ScheduledThread scheduledThread = new ScheduledThread(queue);
        ActiveObjectProxy proxy = new ActiveObjectProxy(scheduledThread, servant);
        scheduledThread.start();
        return proxy;
    }

}
