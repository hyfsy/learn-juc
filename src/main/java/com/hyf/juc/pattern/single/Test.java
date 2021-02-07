package com.hyf.juc.pattern.single;

/**
 * 单线程执行
 *
 * Single Threaded Execution
 *
 * 一些行人要通过一个门（共享资源，临界区）
 *
 * @author baB_hyf
 * @date 2020/12/19
 */
public class Test {

    public static void main(String[] args) {
        Gate gate = new Gate();
        User a = new User("A-name", "A-address", gate);
        User b = new User("B-name", "B-address", gate);
        User c = new User("C-name", "C-address", gate);

        a.start();
        b.start();
        c.start();
    }
}
