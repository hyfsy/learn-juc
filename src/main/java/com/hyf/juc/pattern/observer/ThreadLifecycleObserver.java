package com.hyf.juc.pattern.observer;

import java.util.List;

/**
 * @author baB_hyf
 * @date 2020/12/19
 */
public class ThreadLifecycleObserver implements LifecycleListener{

    private final Object LOCK = new Object();

    public void concurrentQuery(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        ids.forEach(id -> new Thread(new ObserverRunnable(this) {
            @Override
            public void run() {
                Thread t = Thread.currentThread();
                try {
                    notifyChange(new RunnableEvent(RunnableState.RUNNING, t, null));

                    System.out.println("query for id: " + id);

                    Thread.sleep(1000);

                    int i = 1 / 0;

                    notifyChange(new RunnableEvent(RunnableState.DONE, t, null));
                } catch (Exception e) {
                    notifyChange(new RunnableEvent(RunnableState.ERROR, t, null));
                    e.printStackTrace();
                }
            }
        }, id).start());
    }

    @Override
    public void onEvent(RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println(event);
        }
    }
}
