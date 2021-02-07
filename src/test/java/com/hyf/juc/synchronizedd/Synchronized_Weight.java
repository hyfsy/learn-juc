package com.hyf.juc.synchronizedd;

import org.openjdk.jol.info.ClassLayout;

/**
 * 重量级锁
 *
 * @author baB_hyf
 * @date 2020/12/18
 */
public class Synchronized_Weight {

    public static void main(String[] args) {
        Object o = new Object();
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(() -> {
                print(o);
            });
            t.start();
        }


        /*

            java.lang.Object object internals:
             OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
                  0     4        (object header)                           5a 44 b7 1c (01011010 01000100 10110111 00011100) (481772634)
                  4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
                  8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
                 12     4        (loss due to the next object alignment)
            Instance size: 16 bytes
            Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

         */
    }

    public static void print(Object o) {
        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }
}
