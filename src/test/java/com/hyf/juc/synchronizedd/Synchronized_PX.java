package com.hyf.juc.synchronizedd;

import org.openjdk.jol.info.ClassLayout;

/**
 * 偏向锁
 *
 * @author baB_hyf
 * @date 2020/12/18
 */
public class Synchronized_PX {

    public static void main(String[] args) throws Exception {
        Thread.sleep(5000); //等待jvm开启偏向锁
        Object o = new Object();
        // o.hashCode(); // 影响锁状态
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        // o.hashCode(); // 影响锁状态

        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

        /*

            java.lang.Object object internals:
             OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
                  0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
                  4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
                  8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
                 12     4        (loss due to the next object alignment)
            Instance size: 16 bytes
            Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

            java.lang.Object object internals:
             OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
                  0     4        (object header)                           05 38 11 03 (00000101 00111000 00010001 00000011) (51460101)
                  4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
                  8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
                 12     4        (loss due to the next object alignment)
            Instance size: 16 bytes
            Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

         */
    }
}
