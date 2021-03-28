package com.hyf.disruptor.singleproducer;

import com.hyf.disruptor.hello.LongEvent;
import com.hyf.disruptor.hello.LongEventFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * @author baB_hyf
 * @date 2021/03/11
 */
public class LongEventMain
{
    public static void main(String[] args) throws Exception
    {
        //.....
        // Construct the Disruptor with a SingleProducerSequencer
        Disruptor<LongEvent> disruptor = new Disruptor(
                LongEventFactory::new, 2, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        //.....
    }
}
