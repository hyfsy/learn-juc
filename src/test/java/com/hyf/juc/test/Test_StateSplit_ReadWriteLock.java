package com.hyf.juc.test;

/**
 * 读写锁状态分割
 * <p>
 * 高16位为读锁，低16位为写锁
 *
 * @author baB_hyf
 * @date 2020/12/15
 */
public class Test_StateSplit_ReadWriteLock {

    static final int SHARED_SHIFT   = 16;
    static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
    static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1; // 65535
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1; // 00000000000000001111111111111111

    static int sharedCount(int c) {
        return c >>> SHARED_SHIFT;
    }

    static int exclusiveCount(int c) {
        return c & EXCLUSIVE_MASK;
    }

    public static void main(String[] args) {
        int state = 0B00000000000000010000000000000001;

        System.out.println(sharedCount(state));
        System.out.println(exclusiveCount(state));


    }
}
