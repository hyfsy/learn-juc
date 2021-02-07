package com.hyf.juc.cl;

import org.junit.Test;

/**
 * final关键字的作用
 * <p>
 * final修饰的常量在编译期间会被放到常量池，不会初始化类
 *
 * @author baB_hyf
 * @date 2020/12/16
 */
public class Test_Final {

    /**
     * 如果在编译期间能知道它的确切值，则编译器会把它当做编译期常量使用。
     * 也就是说在用到该final变量的地方，相当于直接访问的这个常量，不需要在运行时确定。
     * 不过要注意，只有在编译期间能确切知道final变量值的情况下，编译器才会进行这样的优化。
     */
    @Test
    public void testOne() {
        final

        String a = "hello";
        String b = "hello";
        String c = "hello2";
        String d = a + 2;
        String e = b + 2;
        System.out.println((c == d));
        System.out.println((c == e));
    }


    static class FinalFieldExample {
        static FinalFieldExample f;
        final  int               x; // 语义上解决问题
        int y;

        // 对象已在堆上开辟位置，不为null了，但构造函数还没执行完毕，导致其他线程使用对象内属性报错
        public FinalFieldExample() {
            x = 3;
            y = 4;
        }

        static void writer() {
            f = new FinalFieldExample();
        }

        static void reader() {
            if (f != null) {
                int i = f.x; // guaranteed to see 3
                int j = f.y; // could see 0
            }
        }
    }
}
