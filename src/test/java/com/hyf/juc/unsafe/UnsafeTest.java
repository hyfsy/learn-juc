package com.hyf.juc.unsafe;

import org.junit.Test;
import sun.misc.Unsafe;

/**
 * @author baB_hyf
 * @date 2021/01/06
 */
public class UnsafeTest {

    public static int anInt;

    @Test
    public void testOne() throws NoSuchFieldException {

        Unsafe unsafe = UnSafeInstance.getInstance();
        long field = unsafe.staticFieldOffset(UnsafeTest.class.getDeclaredField("anInt"));
        unsafe.putInt(field, 1);

        System.out.println(anInt);
    }

    @Test
    public void testTwo() {
        Unsafe unsafe = UnSafeInstance.getInstance();

        String[] strs = new String[2];
        strs[0] = "str";

        // int[] strs = new int[2];
        // strs[0] = 1;

        int base = unsafe.arrayBaseOffset(strs.getClass()); // 内存地址基准偏移值
        int scale = unsafe.arrayIndexScale(strs.getClass()); // 数组元素每个位之间的比例值
        System.out.println("base: " + base);
        System.out.println("scale: " + scale);

        // Integer.numberOfLeadingZeros：获取int中从左边开始的为0的个数
        // 获取int（32位）中非0的位数
        int shift = 31 - Integer.numberOfLeadingZeros(scale); // 数组每个元素之间的偏移值
        System.out.println("shift: " + shift);

        int i = 0; // 基本类型的数组无越界检查
        long offset = ((long) i << shift) + base; // 指定下标个数的元素偏移值 + 基准值 = 当前下标的元素偏移值
        System.out.println("offset: " + offset);

        Object object = unsafe.getObject(strs, offset);
        System.out.println(object);

    }

    @Test
    public void testThree() {
        Unsafe unsafe = UnSafeInstance.getInstance();

        int[] ints = new int[2];
        ints[0] = 1;

        long[] longs = new long[2];
        longs[0] = 1;


        int intBase = unsafe.arrayBaseOffset(ints.getClass());
        int intScale = unsafe.arrayIndexScale(ints.getClass());
        int intShift = 31 - Integer.numberOfLeadingZeros(intScale);
        int i = 1; // 基本类型的数组无越界检查
        long intOffset = ((long) i << intShift) + intBase;
        System.out.println("intBase: " + intBase);
        System.out.println("intScale: " + intScale);
        System.out.println("intShift: " + intShift);
        System.out.println("intOffset: " + intOffset);

        System.out.println("==========");

        int longBase = unsafe.arrayBaseOffset(longs.getClass());
        int longScale = unsafe.arrayIndexScale(longs.getClass());
        int longShift = 31 - Integer.numberOfLeadingZeros(longScale);
        int l = 1; // 基本类型的数组无越界检查
        long longOffset = ((long) l << longShift) + longBase;
        System.out.println("longBase: " + longBase);
        System.out.println("longScale: " + longScale);
        System.out.println("longShift: " + longShift);
        System.out.println("longOffset: " + longOffset);
    }

    @Test
    public void testFour() {
        Unsafe unsafe = UnSafeInstance.getInstance();

        Object[] objs = new Object[1];
        objs[0] = 1;

        int base = unsafe.arrayBaseOffset(objs.getClass());
        int scale = unsafe.arrayIndexScale(objs.getClass());
        int shift = 31 - Integer.numberOfLeadingZeros(scale);
        int l = 1; // 基本类型的数组无越界检查
        long offset = ((long) l << shift) + base;
        System.out.println("base: " + base);
        System.out.println("scale: " + scale); // 对象所占字节数
        System.out.println("shift: " + shift);
        System.out.println("offset: " + offset);
    }

    @Test
    public void testFive() throws Exception {

        Unsafe unsafe = UnSafeInstance.getInstance();

        TestClass testClass = new TestClass();

        System.out.println(TestClass.s1);
        System.out.println(testClass.s2);

        // TestClass.s1 = "";
        // testClass.s2 = "";

        long s1Offset = unsafe.staticFieldOffset(TestClass.class.getDeclaredField("s1"));
        long s2Offset = unsafe.objectFieldOffset(TestClass.class.getDeclaredField("s2"));

        // o == TestClass.class
        Object o = unsafe.staticFieldBase(TestClass.class.getDeclaredField("s1"));
        unsafe.putObject(o, s1Offset, "new");
        unsafe.putObject(testClass, s2Offset, "new");

        System.out.println(TestClass.s1);
        System.out.println(testClass.s2);

    }

    public static class TestClass {
        public static final String s1 = "xxx";
        public final        String s2 = "xxx";
        // public static String s1 = "xxx";
        // public        String s2 = "xxx";
    }
}
