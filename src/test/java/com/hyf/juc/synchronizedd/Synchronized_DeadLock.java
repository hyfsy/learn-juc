package com.hyf.juc.synchronizedd;

/**
 * @author baB_hyf
 * @date 2022/03/02
 */
public class Synchronized_DeadLock {

    public static void main(String[] args) {
        new Thread(A::invoke).start();
        new Thread(B::invoke).start();
    }

    static class A {
        public static synchronized void invoke() {
            System.out.println("A");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            B.invoke();
        }
    }

    static class B {
        public static synchronized void invoke() {
            System.out.println("B");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            A.invoke();
        }
    }
}
