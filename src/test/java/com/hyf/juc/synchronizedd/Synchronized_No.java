package com.hyf.juc.synchronizedd;

import org.openjdk.jol.info.ClassLayout;

/**
 * 无锁
 *
 * @author baB_hyf
 * @date 2020/12/18
 */
public class Synchronized_No {

    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println("obj hash: " + obj.hashCode());
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        /*

            obj hash: 1836019240
            java.lang.Object object internals:
             OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
                  0     4        (object header)                           01 28 6e 6f (00000001 00101000 01101110 01101111) (1869490177)
                  4     4        (object header)                           6d 00 00 00 (01101101 00000000 00000000 00000000) (109)
                  8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
                 12     4        (loss due to the next object alignment)
            Instance size: 16 bytes
            Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

         */

    }
}
