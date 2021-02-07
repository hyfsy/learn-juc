package com.hyf.juc.mix;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author baB_hyf
 * @date 2020/12/28
 */
public class BlockingQueueTest {

    @Test
    public void testArray() throws Exception {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1, true);
        boolean remove = queue.remove("1");
        System.out.println(remove);

    }

    @Test
    public void testLinked() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    }

    @Test
    public void testTransfer() {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        System.out.println(queue.poll());
        queue.add("a");
        // try {
        //     queue.put("a");
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        System.out.println(queue.peek());
        // System.out.println(queue.element());
        try {
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLinkedTransfer() throws InterruptedException {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.offer("asdf");
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // try {
                // queue.transfer("b");
                // System.out.println(queue.take());
                // System.out.println(queue.poll());
                // System.out.println(queue.remove());
            System.out.println(queue.offer("b"));
            System.out.println(queue.poll());
            System.out.println(queue.poll());
            System.out.println(queue.poll());
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
        }).start();
        queue.transfer("a");
        // System.out.println(queue.tryTransfer("asdf"));
    }

}
