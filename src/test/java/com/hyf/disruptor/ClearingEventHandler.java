package com.hyf.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * @author baB_hyf
 * @date 2021/03/11
 */
public class ClearingEventHandler<T> implements EventHandler<ObjectEvent<T>> {

    private static int bufferSize = 2;

    public static void main(String[] args) {
        Disruptor<ObjectEvent<String>> disruptor = new Disruptor<>(
                ObjectEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // disruptor
        //         .handleEventsWith(new ProcessingEventHandler())
        //         .then(new ClearingObjectHandler());
    }

    public void onEvent(ObjectEvent<T> event, long sequence, boolean endOfBatch) {
        // Failing to call clear here will result in the
        // object associated with the event to live until
        // it is overwritten once the ring buffer has wrapped
        // around to the beginning.
        event.clear();
    }
}

class ObjectEvent<T> {
    T val;

    void clear() {
        val = null;
    }
}
