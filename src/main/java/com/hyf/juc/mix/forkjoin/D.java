package com.hyf.juc.mix.forkjoin;

import java.util.concurrent.CountedCompleter;

/**
 * @author baB_hyf
 * @date 2021/01/13
 */
public class D {

    public static void main(String[] args) {
        Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // Integer[] ints = {1, 2};
        ForEach.forEach(ints, new MyOperation<>());
    }

    static class MyOperation<E> {
        void apply(E e) {
            System.out.println("My Operation: " + e);
        }
    }

    static class ForEach<E> extends CountedCompleter<Void> {

        final E[]            array;
        final MyOperation<E> op;
        final int            lo, hi;

        ForEach(CountedCompleter<?> p, E[] array, MyOperation<E> op, int lo, int hi) {
            super(p);
            this.array = array;
            this.op = op;
            this.lo = lo;
            this.hi = hi;
        }

        public static <E> void forEach(E[] array, MyOperation<E> op) {
            new ForEach<E>(null, array, op, 0, array.length).invoke();
        }

        @Override
        public void onCompletion(CountedCompleter<?> caller) {
            System.out.println(Thread.currentThread().getName() + " " + this + " -> " + ((ForEach) this).lo + " - " + ((ForEach) this).hi + "   " + caller + " -> " + ((ForEach) caller).lo + " - " + ((ForEach) caller).hi);
        }

        // public void compute() { // version 1
        //     if (hi - lo >= 2) {
        //         int mid = (lo + hi) >>> 1;
        //         setPendingCount(2); // must set pending count before fork
        //         new ForEach(this, array, op, mid, hi).fork(); // right child
        //         new ForEach(this, array, op, lo, mid).fork(); // left child
        //     }
        //     else if (hi > lo) {
        //         op.apply(array[lo]);
        //     }
        //
        //     // 将计数器减一，到0时，将当前任务指向上层completer任务，继续尝试完成上层任务
        //
        //     // 根节点任务会默认减一，子节点无pending，执行完毕就代表任务结束，
        //     // 但是子节点的tryComplete会循环获取存在的上层任务，并尝试将上层任务的pending减少，递归的通知任务的完成，
        //     // 当最上层无completer时，表示任务彻底结束
        //     tryComplete();
        // }

        // public void compute() { // version 2
        //     if (hi - lo >= 2) {
        //         int mid = (lo + hi) >>> 1;
        //         setPendingCount(1); // only one pending
        //         new ForEach(this, array, op, mid, hi).fork(); // right child
        //         new ForEach(this, array, op, lo, mid).compute(); // direct invoke, a improve
        //     }
        //     else {
        //         if (hi > lo)
        //             op.apply(array[lo]);
        //         tryComplete();
        //     }
        // }

        public void compute() { // version 3
            int l = lo,  h = hi;
            while (h - l >= 2) {
                int mid = (l + h) >>> 1;
                addToPendingCount(1); // 表示与自己关联的子任务的数量
                new ForEach(this, array, op, mid, h).fork(); // right child
                h = mid;
            }
            if (h > l)
                op.apply(array[l]);
            propagateCompletion(); // 仅仅是不调用onCompletion了
        }
    }
}
