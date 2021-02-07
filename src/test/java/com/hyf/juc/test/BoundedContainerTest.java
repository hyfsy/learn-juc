package com.hyf.juc.test;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author baB_hyf
 * @date 2020/12/24
 */
public class BoundedContainerTest {

    public static void main(String[] args) {

        // take数量不能比put大
        BoundedContainer container = new BoundedContainer();

        int count = 5;

        IntStream.range(0, count).forEach(i -> new Thread(() -> {
            try {
                container.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());

        IntStream.range(0, count).forEach(i -> new Thread(() -> {
            try {
                container.put("hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
    }

    static class BoundedContainer {

        private String[]  elements;
        private Lock      lock      = new ReentrantLock();
        private Condition NOT_EMPTY = lock.newCondition();
        private Condition NOT_FULL  = lock.newCondition();
        private int       elementCount; // elements中已有元素数量
        private int       takeIndex;
        private int       putIndex;

        public BoundedContainer() {
            this(10);
        }

        public BoundedContainer(int size) {
            elements = new String[size];
        }

        public String take() throws InterruptedException {
            this.lock.lock();
            try {
                while (elementCount == 0) {
                    NOT_EMPTY.await();
                }

                String element = elements[takeIndex];
                elements[takeIndex] = null;
                if (++takeIndex == elements.length) {
                    takeIndex = 0;
                }
                elementCount--;

                System.out.println("TAKE method: " + Arrays.toString(elements));
                NOT_FULL.signal();

                return element;
            } finally {
                this.lock.unlock();
            }
        }

        public void put(String element) throws InterruptedException {
            this.lock.lock();
            try {

                while (elementCount == elements.length) {
                    this.NOT_FULL.await();
                }

                elements[putIndex] = element;
                if (++putIndex == elements.length) {
                    putIndex = 0;
                }
                elementCount++;

                System.out.println("PUT method: " + Arrays.toString(elements));
                this.NOT_EMPTY.signal();
            } finally {
                this.lock.unlock();
            }
        }
    }
}
