package com.hyf.juc.test;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池状态分割
 * <p>
 * 高3位为线程池状态，剩下的为线程池工作线程数量
 *
 * @author baB_hyf
 * @date 2020/12/15
 */
public class Test_StateSplit_ThreadPoolExecutor {

    private static final int COUNT_BITS = Integer.SIZE - 3; // 29
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1; // 00011111111111111111111111111111

    // 负数的原码 = 正数的反码 = 1xxx 取反 + 1
    private static final int RUNNING    = -1 << COUNT_BITS; // 11100000000000000000000000000000
    private static final int SHUTDOWN   = 0 << COUNT_BITS;  // 00000000000000000000000000000000
    private static final int STOP       = 1 << COUNT_BITS;  // 00100000000000000000000000000000
    private static final int TIDYING    = 2 << COUNT_BITS;  // 01000000000000000000000000000000
    private static final int TERMINATED = 3 << COUNT_BITS;  // 01100000000000000000000000000000

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    @Test
    public void testOne() {
        System.out.println(-2 << COUNT_BITS);
        System.out.println(-3 << COUNT_BITS);
        System.out.println(-4 << COUNT_BITS);
        System.out.println(4 << COUNT_BITS);
        System.out.println(0b11000000000000000000000000000000);
        System.out.println(0b10100000000000000000000000000000);
        System.out.println(0b10000000000000000000000000000000);
        System.out.println((1 << 31) - 1);
    }

    @Test
    public void testTwo() {
        System.out.println(RUNNING);
        System.out.println(SHUTDOWN);
        System.out.println(STOP);
        System.out.println(TIDYING);
        System.out.println(TERMINATED);
    }

    @Test
    public void testThree() {
        int state = 0B00100000000000000000000000000001;

        System.out.println(runStateOf(state));
        System.out.println(workerCountOf(state));
    }


}
