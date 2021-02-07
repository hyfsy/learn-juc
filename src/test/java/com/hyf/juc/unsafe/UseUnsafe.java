package com.hyf.juc.unsafe;

import sun.misc.Unsafe;

/**
 * @author baB_hyf
 * @date 2020/12/26
 */
public class UseUnsafe {

    // volatile通知其他线程该变量已经无效
    volatile int state = 0;

    public boolean casState(int expect, int update) {
        return compareAndSetState(this, expect, update);
    }

    public static void main(String[] args) {
        UseUnsafe useUnsafe = new UseUnsafe();
        boolean succeed = useUnsafe.casState(0, 1);
        System.out.println(succeed);
        System.out.println(useUnsafe.state);
    }

    private static final Unsafe U;
    private static final long   stateOffset;

    static {
        U = UnSafeInstance.getInstance();
        try {
            stateOffset = U.objectFieldOffset(UseUnsafe.class.getDeclaredField("state"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    private static final boolean compareAndSetState(UseUnsafe testInstance, int expect, int update) {
        return U.compareAndSwapInt(testInstance, stateOffset, expect, update);
    }
}
