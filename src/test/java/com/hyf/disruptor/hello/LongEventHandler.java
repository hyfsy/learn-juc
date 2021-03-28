package com.hyf.disruptor.hello;

import com.lmax.disruptor.EventHandler;

/**
 * @author baB_hyf
 * @date 2021/03/11
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Event: " + event);
    }
}
