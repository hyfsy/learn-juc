package com.hyf.juc.unsafe;

import org.junit.Test;
import sun.misc.Unsafe;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author baB_hyf
 * @date 2021/01/09
 */
public class ThreadLocalRandomTest {

    @Test
    public void testUnsafe() throws NoSuchFieldException {
        Unsafe unsafe = UnSafeInstance.getInstance();
        int probe = unsafe.getInt(Thread.currentThread(), unsafe.objectFieldOffset
                (Thread.class.getDeclaredField("threadLocalRandomProbe")));
        System.out.println(probe);
        ThreadLocalRandom.current();

        int newProbe = unsafe.getInt(Thread.currentThread(), unsafe.objectFieldOffset
                (Thread.class.getDeclaredField("threadLocalRandomProbe")));
        System.out.println(newProbe);

    }
}
