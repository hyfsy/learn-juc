package com.hyf.juc.mix.forkjoin;

import java.util.Arrays;

/**
 * @author baB_hyf
 * @date 2021/01/17
 */
public class MyQueueTest {

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        push(queue, 7);
        poll(queue, 4);
        push(queue, 3);
        poll(queue, 4);
        poll(queue, 2);
        push(queue, 8);
        push(queue, 8);
        queue.print();
    }

    public static void push(MyQueue queue, int count) {
        for (int i = 1; i <= count; i++) {
            queue.push(i);
        }
    }

    public static void poll(MyQueue queue, int count) {
        for (int i = 1; i <= count; i++) {
            queue.poll();
        }
    }

    static class MyQueue {

        private int       base;
        private int       top;
        private Integer[] arr;

        public MyQueue() {
            arr = new Integer[1 << 3];
            this.base = this.top = arr.length >> 1;
            print();
        }

        public void push(Integer v) {
            int m = arr.length - 1;
            int t = top;
            int b = base;
            arr[t & m] = v;
            top++;
            int n = t - b;
            if (n >= m) {
                growArray();
            }
        }

        public Integer poll() {
            int b = base;
            Integer[] a = arr;
            while (b - top < 0 && a != null) {
                int j = (a.length - 1) & b;
                Integer t = a[j];
                if (t != null) {
                    a[j] = null;
                    base = b + 1;
                    return t;
                }
                else if (b + 1 == top) {
                    break;
                }
            }
            return null;
        }

        public Integer[] growArray() {
            Integer[] oldA = arr;
            int size = oldA.length << 1;
            int oldMask = oldA.length - 1, t = top, b = base;
            Integer[] a = arr = new Integer[size];
            if (oldMask >= 0 && t - b > 0) {
                int mask = size - 1;
                do {
                    Integer x;
                    int oldJ = b & oldMask;
                    int j = b & mask;
                    x = oldA[oldJ];
                    if (x != null) {
                        oldA[oldJ] = null;
                        a[j] = x;
                    }
                } while (++b != t);
            }
            return a;
        }

        public void print() {
            System.out.println("base: " + base + "\ntop: " + top);
            System.out.println(Arrays.toString(arr));
            System.out.println("==============================");
        }
    }
}

