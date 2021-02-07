package com.hyf.juc.test;

import com.hyf.juc.unsafe.UnSafeInstance;
import sun.misc.Unsafe;

/**
 * 使用Unsafe绕过构造函数，实例化对象
 *
 * @author baB_hyf
 * @date 2020/12/20
 */
public class Test_SkipConstructor {

    public static void main(String[] args) {
        Stupid stupid = createInstance(Stupid.class);
        System.out.println(stupid.s);
        stupid.speak();
    }

    static class Stupid {
        private final String s;
        { System.out.println("进入代码块"); }
        public Stupid() {
            s = "1";
            System.out.println("进入构造");
        }
        public void speak() { System.out.println("我从石头里蹦出来了"); }
    }

    public static <T> T createInstance(Class<T> clazz) {
        Unsafe unsafe = UnSafeInstance.getInstance();
        try {
            return (T) unsafe.allocateInstance(clazz);
        } catch (InstantiationException e) {
            throw new RuntimeException("实例化失败！");
        }
    }

}
