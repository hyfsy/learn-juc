package com.hyf.juc.mix;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author baB_hyf
 * @date 2021/01/05
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinPool pool = new ForkJoinPool(15); // 0x7fff
        ForkJoinTask<?> runnable = ForkJoinTask.adapt(() -> System.out.println(1));
        ForkJoinTask<?> runnable2 = ForkJoinTask.adapt(() -> System.out.println(1));
        // pool.invoke(runnable);
        pool.execute(runnable);
        pool.execute(runnable2);
        // pool.submit(runnable);
        // pool.shutdown();
        // System.out.println(runnable.get());
        pool.awaitTermination(1, TimeUnit.HOURS);
    }

    @Test
    public void testTwenty_Eight() {
        ExecutorService executorService = Executors.newWorkStealingPool(15); // 默认异步
    }

    @Test
    public void testTwenty_Seven() throws InterruptedException {
        System.out.println(Integer.toBinaryString(0xf0010000 >>> 16));
        System.out.println((0xf0010000 >>> 16) != 0);

        System.out.println((100000 & 2) <= 2);
    }

    @Test
    public void testTwenty_Six() throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(15);
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                System.out.println(1);
            });
        }

        Thread.currentThread().join();
    }

    @Test
    public void testTwenty_Five() throws InterruptedException {

        // test SS_SEQ
        // SS_SEQ在释放阻塞线程的时候才设置，并且立即置32为0，所以持续增加即使溢出（所有1都变为0）也没事

        // for (int i = 0; i < (1 << 15); i++) {
        //     System.out.println(Integer.toBinaryString((1<< 31) + ((1<<16) * i)));
        // }

        // System.out.println(0b11111111111111110000000000000001);
        // System.out.println(0b11111111111111110000000000000001 + (1<<16));
        // System.out.println(Integer.toBinaryString(0b11111111111111110000000000000001 + (1<<16)));

        class FT extends ForkJoinWorkerThread {
            protected FT(ForkJoinPool pool) {
                super(pool);
            }

            @Override
            protected void onTermination(Throwable exception) {
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        }
        class F implements ForkJoinWorkerThreadFactory {
            @Override
            public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
                return new FT(pool);
            }
        }

        ForkJoinPool pool = new ForkJoinPool(1, new F(), null, false);
        ForkJoinTask<Integer> task = new ForkJoinTask<Integer>() {

            @Override
            public Integer getRawResult() {
                return null;
            }

            @Override
            protected void setRawResult(Integer value) {

            }

            @Override
            protected boolean exec() {
                return true;
            }
        };
        for (int i = 0; i < 100000; i++) { // 65535
            pool.invoke(task);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Thread.currentThread().join();
    }

    @Test
    public void testTwenty_Four() throws InterruptedException, ExecutionException {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        // pool.execute(() -> System.out.println(1));
        ForkJoinTask<Object> task = pool.submit(new ForkJoinTask<Object>() {

            @Override
            public Object getRawResult() {
                return null;
            }

            @Override
            protected void setRawResult(Object value) {

            }

            @Override
            protected boolean exec() {
                throw new IllegalArgumentException();
            }
        });

        Thread.sleep(1000);

        task.get();

    }

    @Test
    public void testTwenty_Three() {
        int n = 3;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 1;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 2;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 4;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 8;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 16;
        System.out.println(Integer.toBinaryString(n));
        n = (n + 1) << 1;
        System.out.println(Integer.toBinaryString(n));
        System.out.println(n);
    }

    @Test
    public void testTwenty_Two() {
        ForkJoinPool pool = new ForkJoinPool(5);
        pool.execute(() -> System.out.println(1));
    }

    @Test
    public void testTwenty_One() {
        for (int i = 0; i < 100; i++) {
            System.out.println("8191 & " + (i + 8190) + " = " + (8191 & (i + 8190)));
        }
    }

    @Test
    public void testTwenty() throws InterruptedException {
        ForkJoinTask<Integer> task = new ForkJoinTask<Integer>() {

            @Override
            public Integer getRawResult() {
                return null;
            }

            @Override
            protected boolean exec() {
                return true;
            }

            @Override
            protected void setRawResult(Integer value) {

            }


        };


        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 100000; i++) {
            pool.submit(task);
            System.out.println(pool.getStealCount());
        }
    }

    @Test
    public void testNineteen() throws InterruptedException {
        AtomicInteger integer = new AtomicInteger(0);
        ForkJoinTask t = new ForkJoinTask<Object>() {

            @Override
            public Object getRawResult() {
                return null;
            }

            @Override
            protected boolean exec() {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    System.out.println("interrupt");
                }
                integer.incrementAndGet();
                return true;
            }

            @Override
            protected void setRawResult(Object value) {

            }


        };
        ForkJoinPool pool = new ForkJoinPool(15);
        // ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < (1 << 27); i++) {
            pool.submit(t);
        }
        // pool.awaitTermination(180, TimeUnit.SECONDS);
        // pool.awaitQuiescence(200, TimeUnit.SECONDS);
        System.out.println(integer.get());
    }

    @Test
    public void testEighteen() {
        Integer[] old = new Integer[]{0, 0, 0, 4, 5, 6, 0, 0};
        int oldLen = old.length;
        int size = old.length << 1;
        int b = 3; // 4
        int t = 5; // 6

        int oldMask = oldLen - 1;
        int newMask = size - 1;


        Integer[] newArr = new Integer[size];

        do {
            Integer v = old[b];
            newArr[b] = v;
        } while (++b != t);

        System.out.println(Arrays.toString(old));
        System.out.println(Arrays.toString(newArr));
    }

    @Test
    public void testSeventeen() {
        ForkJoinPool pool = new ForkJoinPool(15);
        pool.submit(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.shutdown();
    }

    @Test
    public void testSixteen() {
        ForkJoinTask<Integer> task = new ForkJoinTask<Integer>() {

            @Override
            public Integer getRawResult() {
                return null;
            }

            @Override
            protected boolean exec() {
                int surplusQueuedTaskCount = getSurplusQueuedTaskCount();
                return false;
            }

            @Override
            protected void setRawResult(Integer value) {

            }


        };

        // task.invoke();
        ForkJoinPool.commonPool().submit(task);
    }

    @Test
    public void testFifteen() throws InterruptedException {
        ForkJoinTask o = new ForkJoinTask<Object>() {

            @Override
            public Object getRawResult() {
                return null;
            }

            @Override
            protected boolean exec() {
                throw new RuntimeException();
            }

            @Override
            protected void setRawResult(Object value) {

            }


        };
        try {
            ForkJoinPool.commonPool().invoke(o);
        } catch (Exception e) {
        }
        System.out.println(o.getException());
        o.reinitialize();
    }

    @Test
    public void testFourteen() {
        int i = 4096;
        System.out.println(Integer.toBinaryString(i | Integer.MIN_VALUE));

        System.out.println(1 >>> 1);
    }

    @Test
    public void testThirteen() {
        List<Integer> integers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        Integer[] ws = integers.toArray(new Integer[0]);
        int m = ws.length - 1;

        for (int i = 0; i <= m; ++i) {
            Integer v;
            if ((v = ws[((i << 1) | 1) & m]) != null) {
                System.out.println(v);
            }
        }
    }

    @Test
    public void testTwelve() {
        boolean b = true;
        // if (b) throw new Throwable();
        // if (b) throw new Exception();
        if (b) {
            throw new Error();
        }
        if (b) {
            throw new RuntimeException();
        }
    }

    @Test
    public void testEleven() {
        ForkJoinTask<?> runnable = ForkJoinTask.adapt(() -> System.out.println(1));
        short s = -1;
        runnable.setForkJoinTaskTag(s);
        System.out.println(runnable.isCompletedAbnormally());
    }

    @Test
    public void testTen() {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(0x80000000);

        int s = 0x80000000;
        long s2 = (long) (s < 0 ? Integer.MAX_VALUE : s);
        System.out.println(s2);

        System.out.println(Integer.toBinaryString(0xf0000000 >>> 16));
        System.out.println(Integer.toBinaryString(0xf0010000 >>> 16));
    }

    @Test
    public void testNight() {
        System.out.println(Long.toBinaryString(-3940707656007661L));
        System.out.println(Long.toBinaryString(-3940714098458624L));
        System.out.println(Integer.toBinaryString((int) (-3940714098458624L >>> 32)));
        System.out.println(Integer.toBinaryString((short) (-3940714098458624L >>> 32)));
        System.out.println((short) (-3940714098458624L >>> 32));

        System.out.println("=====");

        System.out.println(Integer.toBinaryString(2));
        System.out.println(Integer.toBinaryString(-2));
        System.out.println(Integer.toBinaryString(-2 >> 1));
        System.out.println(Integer.toBinaryString(-2 >>> 1));
        System.out.println(Integer.toBinaryString(-2 << 1));
    }

    @Test
    public void testEight() {
        System.out.println(Long.toBinaryString(0xffffffffL));
        System.out.println(0xffff);
        System.out.println(0xffff >>> 1);

        System.out.println(2 & 31); // [].length - 1
        System.out.println(30 & 31);
        System.out.println(31 & 31);
        System.out.println(32 & 31);
        System.out.println(33 & 31);
    }

    @Test
    public void testSeven() {
        System.out.println(Integer.toBinaryString(~1));

        long ctl = 0;
        ctl = ((-15L << 48) & (0xffffL << 48)) |
                ((-15L << 32) & (0xffffL << 32));
        System.out.println(ctl);
        System.out.println(Long.toBinaryString(ctl));
        long add = 0x0001L << (32 + 15);
        System.out.println(Long.toBinaryString(add));
        System.out.println(Long.toBinaryString(ctl + add));
        System.out.println(Long.toBinaryString(ctl & add));

    }

    @Test
    public void testSix() {
        System.out.println(0xfffe); // even
        System.out.println(0x007e); // even

        System.out.println("=====");

        int n = 16 - 1 - 1;

        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 1;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 2;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 4;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 8;
        System.out.println(Integer.toBinaryString(n));
        n |= n >>> 16;
        System.out.println(Integer.toBinaryString(n));
        n = (n + 1) << 1;
        System.out.println(Integer.toBinaryString(n));
        System.out.println(n);
    }

    @Test
    public void testFive() {
        System.out.println(1 << 13);

        int i = 1 << 26;
        System.out.println(i >> 10 >> 10);

        System.out.println(Integer.toBinaryString(1 << 13 >>> 1));

        System.out.println(Integer.toBinaryString(0xffffffff & 0xffff >>> 1));

        System.out.println(Integer.toBinaryString(0x9e3779b9));

        System.out.println(1);
        System.out.println(Integer.toBinaryString((1 << 30) - 1));
        System.out.println(1 << 31);
        System.out.println(Integer.toBinaryString(1 << 31));

    }

    @Test
    public void testFour() {
        int SMASK = 0xffff;
        int MAX_CAP = 0x7fff;
        int EVENMASK = 0xfffe;
        int SQMASK = 0x007e;
        System.out.println(Integer.toBinaryString(SMASK));
        System.out.println(Integer.toBinaryString(MAX_CAP));
        System.out.println(Integer.toBinaryString(EVENMASK));
        System.out.println(Integer.toBinaryString(SQMASK));

        int SCANNING = 1;
        int INACTIVE = 1 << 31;
        int SS_SEQ = 1 << 16;
        System.out.println(Integer.toBinaryString(SCANNING));
        System.out.println(Integer.toBinaryString(INACTIVE));
        System.out.println(Integer.toBinaryString(SS_SEQ));

        int MODE_MASK = 0xffff << 16;
        int LIFO_QUEUE = 0;
        int FIFO_QUEUE = 1 << 16;
        int SHARED_QUEUE = 1 << 31;
        System.out.println(Integer.toBinaryString(MODE_MASK));
        System.out.println(Integer.toBinaryString(LIFO_QUEUE));
        System.out.println(Integer.toBinaryString(FIFO_QUEUE));
        System.out.println(Integer.toBinaryString(SHARED_QUEUE));
    }

    @Test
    public void testThree() throws InterruptedException {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinPool.getCommonPoolParallelism();
        ForkJoinWorkerThreadFactory factory = ForkJoinPool.defaultForkJoinWorkerThreadFactory;
        ForkJoinPool.managedBlock(null);
        // forkJoinPool
    }

    @Test
    public void testTwo() {
        int[] a = new int[-1];
    }

    @Test
    public void testOne() {
        System.out.println(1 << 15 - 1);
        System.out.println(1 << 1);
        System.out.println(1 << 2);
    }

}
