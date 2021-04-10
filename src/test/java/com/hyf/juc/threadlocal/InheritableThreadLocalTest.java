package com.hyf.juc.threadlocal;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可让子线程继承父线程数据的ThreadLocal
 * <p>
 * 不推荐在线程池中使用，线程创建后不会再同步 InheritableThreadLocal的修改
 *
 * @author baB_hyf
 * @date 2021/04/10
 */
public class InheritableThreadLocalTest {

    @Test
    public void test1() {
        InheritableThreadLocal<Integer> inheritableThreadLocal = new InheritableThreadLocal<>();
        inheritableThreadLocal.set(1);

        new Thread(() -> System.out.println(inheritableThreadLocal.get())).start();
    }

    @Test
    public void test2() {
        InheritableThreadLocal<Integer> inheritableThreadLocal = new InheritableThreadLocal<>();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Runnable runnable = () -> System.out.println(inheritableThreadLocal.get());

        inheritableThreadLocal.set(1);
        executorService.submit(runnable);
        executorService.submit(runnable);

        inheritableThreadLocal.set(2);
        executorService.submit(runnable);
        executorService.submit(runnable);

        executorService.shutdown();
    }

    @Test
    public void test3() {
        InheritableThreadLocal<Integer> inheritableThreadLocal = new InheritableThreadLocal<Integer>() {
            @Override
            protected Integer childValue(Integer parentValue) {
                return parentValue + 1;
            }
        };

        inheritableThreadLocal.set(1);
        new Thread(() -> System.out.println(inheritableThreadLocal.get())).start();
    }
}
