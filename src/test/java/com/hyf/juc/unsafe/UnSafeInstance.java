package com.hyf.juc.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author baB_hyf
 * @date 2020/12/16
 */
public class UnSafeInstance {

    private static final Unsafe INSTANCE;

    static {
        try {
            Constructor<Unsafe> declaredConstructor = Unsafe.class.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            INSTANCE = declaredConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new IllegalStateException("获取Unsafe失败！");
        }
    }

    public static Unsafe getInstance() {
        return INSTANCE;
    }
}
