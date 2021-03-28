package com.hyf.juc.sample.socket.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 * @author baB_hyf
 * @date 2021/03/15
 */
public class HandleMsg implements Runnable {
    SelectionKey sk;
    ByteBuffer   bb;

    public HandleMsg(SelectionKey sk, ByteBuffer bb) {
        this.sk = sk;
        this.bb = bb;
    }

    @Override
    public void run() {
        EchoClient echoClient = (EchoClient) sk.attachment();
        echoClient.enqueue(bb);
        sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        // 强迫selector立即返回
        sk.selector().wakeup();
    }
}
