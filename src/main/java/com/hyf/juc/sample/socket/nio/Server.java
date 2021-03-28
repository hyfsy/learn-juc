package com.hyf.juc.sample.socket.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 要了解NIO,我们首先需要知道在NIO中的一个关键组件Channel(通道)。
 * Channel有点类似于流,一个Channel可以和文件或者网络Socket对应。
 * 如果Channel对应着一个Socket,那么往这个Channel中写数据,就等同于向Socket中写入数据。
 * <p>
 * 和Channel一起使用的另外一个重要组件就是Buffer。大家可以简单地把Buffer理解成一个内存区域或者byte数组。
 * 数据需要包装成Buffer的形式才能和Channel交互(写入或者读取）。
 * <p>
 * 另外一个与Channel密切相关的是Selector (选择器)。在Channel的众多实现中,有一个SelectableChannel实现,
 * 表示可被选择的通道。任何一个SelectableChannel都可以将自己注册到一个Selector中。
 * 这样,这个Channel就能被Selector所管理。而一个Selector可以管理多个SelectableChannel。
 * 当SelectableChannel的数据准备好时, Selector就会接到通知,得到那些已经准备好的数据。
 * 而SocketChannel就是SelectableChannel的一种。
 *
 * @author baB_hyf
 * @date 2021/03/15
 */
public class Server {

    public static Map<Socket, Long> time_stat = new HashMap<Socket, Long>(10240);
    private       Selector          selector;
    private       ExecutorService   tp        = Executors.newCachedThreadPool();

    private void startServer() throws Exception {
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        // InetSocketAddress isa = new InetSocketAddress (InetAddress.getLocalHost (), 8000);
        InetSocketAddress isa = new InetSocketAddress(8000);
        ssc.socket().bind(isa);
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        for (; ; ) {
            selector.select();
            Set readyKeys = selector.selectedKeys();
            Iterator i = readyKeys.iterator();
            long e = 0;
            while (i.hasNext()) {
                SelectionKey sk = (SelectionKey) i.next();
                i.remove();
                if (sk.isAcceptable()) {
                    doAccept(sk);
                }
                else if (sk.isValid() & sk.isReadable()) {
                    if (!time_stat.containsKey(((SocketChannel) sk.channel()).socket())) {
                        time_stat.put(((SocketChannel) sk.channel()).socket(), System.currentTimeMillis());
                    }
                    doRead(sk);
                }
                else if (sk.isValid() && sk.isWritable()) {
                    doWrite(sk);
                    e = System.currentTimeMillis();
                    long b = time_stat.remove(((SocketChannel) sk.channel()).socket());
                    System.out.println("spend:" + (e - b) + "ms");
                }
            }
        }
    }

    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = server.accept();
            clientChannel.configureBlocking(false);
            // Register this channel for reading.
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            // Allocate an EchoClient instance and attach it to this selection key.
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);
            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress() + ".");
        } catch (Exception e) {
            System.out.println("Failed to accept new client.");
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;
        try {
            len = channel.read(bb);
            if (len < 0) {
                disConnect(sk);
                return;
            }
        } catch (Exception e) {
            System.out.println("Failed to read from client.");
            e.printStackTrace();
            disConnect(sk);
            return;
        }

        bb.flip();
        tp.execute(new HandleMsg(sk, bb));
    }

    private void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoclient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq = echoclient.getOutputQueue();
        ByteBuffer bb = outq.getLast();
        try {
            int len = 0;
            try {
                len = channel.write(bb);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (len == -1) {
                disConnect(sk);
                return;
            }

            if (bb.remaining() == 0) {
                // The buffer was completely written, remove it.
                outq.removeLast();
            }
        } catch (Exception e) {
            System.out.println("Failed to write to client.");
            e.printStackTrace();
            disConnect(sk);
        }

        if (outq.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    public void disConnect(SelectionKey sk) {
        try {
            sk.channel().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sk.selector().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}