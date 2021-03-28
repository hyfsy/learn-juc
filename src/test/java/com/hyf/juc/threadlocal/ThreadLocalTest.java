package com.hyf.juc.threadlocal;

import org.junit.Test;

/**
 * ThreadLocal包含一个 ThreadLocalMap内部类，ThreadLocalMap类的实例会被绑定到当前线程的 threadLocals变量中，
 * ThreadLocal调用get/set/remove方法实际上就是在操作当前线程中的这个ThreadLocalMap（没有就初始化一个）
 * <p>
 * ThreadLocalMap类中有一个 Entry内部类，key为ThreadLocal对象，value为指定的值，设置值时会将ThreadLocal实例和
 * 指定值进行绑定，放入到Entry中，Entry又继承了 WeakReference类，弱引用，gc回收时，如果ThreadLocal实例没有强引用指向，
 * 则该ThreadLocal实例会被gc回收掉，ThreadLocalMap中也会自动回收key为null的Entry，导致Entry会被回收，
 * 这个机制是为了当局部变量的ThreadLocal使用完毕，会自动回收掉对应线程中的ThreadLocalMap中的Entry，
 * 防止 ThreadLocalMap的内存泄漏。
 * <p>
 * 1、但是如果ThreadLocal为全局变量时，如 public static ThreadLocal<Integer> xxx ,对应线程的Entry就不会被释放，
 * 从而导致内存泄漏，正确的做法是用完以后调用ThreadLocal实例的remove()方法，手动移除当前线程的 ThreadLocalMap中的 Entry
 * <p>
 * 2、如果ThreadLocal为null，由于Thread->ThreadLocalMap->Entry->value
 * 因此value与Thread的生命周期是一样长的， 如果没有手动删除对应的value，就也有可能会导致内存泄漏。可能的原因是：
 * 在下一次ThreadLocalMap调用set(),get(),remove()的时候该value可能被清除，也可能不被清除（hash）
 * 此时内存泄漏的根源是：ThreadLocalMap的生命周期跟Thread一样长， 如果没有手动删除对应key就会导致内存泄漏。
 * <p>
 * ThreadLocal构造时不做任何操作，set操作会将value放入到当前线程中
 *
 * @author baB_hyf
 * @date 2020/12/13
 */
public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<Integer> t1 = new ThreadLocal<>();
        t1.set(1);
        System.out.println(t1.get());
        t1.remove();

        ThreadLocal<String> t2 = ThreadLocal.withInitial(() -> "1");
        System.out.println(t2.get());

        Thread.sleep(10000);

        for (int i = 0; ; i++) {
            Thread.sleep(2000);
            testInvoke(i);
        }

    }

    public static int testInvoke(int i) {
        System.out.println(i);
        return i;
    }

    @Test
    public void testLeak() throws InterruptedException {
        ThreadLocal<byte[]> t = new ThreadLocal<>();
        System.out.println("start loading");
        Thread.sleep(5000);
        t.set(new byte[100 << 20]);
        t.set(new byte[100 << 20]);
        t.set(new byte[100 << 20]);
        System.out.println("loading end");
        Thread.sleep(3000);

        t = null;
        System.gc();
        System.out.println("gc end");

        Thread.sleep(3000);

        new ThreadLocal<>().get();
        new ThreadLocal<>().get(); // expungeStaleEntry
        System.gc();

        Thread thread = Thread.currentThread();
        System.out.println();
        thread.join();
    }
}
