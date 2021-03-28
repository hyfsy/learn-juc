package com.hyf.disruptor.hello;

import com.lmax.disruptor.EventFactory;

/**
 * @author baB_hyf
 * @date 2021/03/11
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
