package com.hyf.juc.cl;

/**
 * 静态语句块只能访问定义在静态语句块之前的变量，定义在之后的变量，只能赋值，不能访问
 *
 * @author baB_hyf
 * @date 2020/12/20
 */
public class Test_Static {

    private static int x = 0;

    static {
        x = 100;
        y = 100;
        System.out.println(x);
        // System.out.println(y);
    }

    private static int y = 0;
}
