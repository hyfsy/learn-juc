package com.hyf.juc.sample.socket.nio;

import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * @author baB_hyf
 * @date 2021/03/15
 */
public class EchoClient {

    private LinkedList<ByteBuffer> queue;

    EchoClient() {
        queue = new LinkedList<>();
    }

    public LinkedList<ByteBuffer> getOutputQueue() {
        return queue;
    }

    public void enqueue(ByteBuffer bb) {
        queue.addFirst(bb);
    }
}
