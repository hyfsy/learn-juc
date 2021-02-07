package com.hyf.juc.mix.forkjoin;

import java.util.concurrent.CountedCompleter;

/**
 * @author baB_hyf
 * @date 2021/01/14
 */
public class F {

    public static void main(String[] args) {
        // Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer[] ints = {1, 2, 3, 4, 5};
        // Integer[] ints = {1, 2};
        Integer result = MapReducer.mapReduce(ints, new MyMapper<>(), new MyReducer<>());
        System.out.println("result: " + result);
    }

    static class MyMapper<E> {
        E apply(E v) {
            // System.out.println("MyMapper: " + v);
            return v;
        }
    }

    static class MyReducer<E> {
        E apply(E x, E y) {
            System.out.println("MyReducer: x=[" + x + "],y=[" + y + "]");
            return x;
        }
    }

    // static class MapReducer<E> extends CountedCompleter<E> {
    //     final E[] array;
    //     final MyMapper<E> mapper;
    //     final MyReducer<E> reducer;
    //     final int lo, hi;
    //     MapReducer<E> sibling;
    //     E             result;
    //
    //     MapReducer(CountedCompleter<?> p, E[] array, MyMapper<E> mapper,
    //                MyReducer<E> reducer, int lo, int hi) {
    //         super(p);
    //         this.array = array;
    //         this.mapper = mapper;
    //         this.reducer = reducer;
    //         this.lo = lo;
    //         this.hi = hi;
    //     }
    //
    //     public static <E> E mapReduce(E[] array, MyMapper<E> mapper, MyReducer<E> reducer) {
    //         return new MapReducer<E>(null, array, mapper, reducer, 0, array.length).invoke();
    //     }
    //
    //     public void compute() {
    //         if (hi - lo >= 2) {
    //             int mid = (lo + hi) >>> 1;
    //             MapReducer<E> left = new MapReducer(this, array, mapper, reducer, lo, mid);
    //             MapReducer<E> right = new MapReducer(this, array, mapper, reducer, mid, hi);
    //             left.sibling = right;
    //             right.sibling = left;
    //             setPendingCount(1); // only right is pending
    //             right.fork();
    //             left.compute();     // directly execute left
    //         }
    //         else {
    //             if (hi > lo) {
    //                 result = mapper.apply(array[lo]);
    //             }
    //             tryComplete();
    //         }
    //     }
    //
    //     public void onCompletion(CountedCompleter<?> caller) {
    //         if (caller != this) {
    //             MapReducer<E> child = (MapReducer<E>) caller;
    //             MapReducer<E> sib = child.sibling;
    //             if (sib == null || sib.result == null) {
    //                 result = child.result;
    //             }
    //             else {
    //                 result = reducer.apply(child.result, sib.result);
    //             }
    //         }
    //     }
    //
    //     public E getRawResult() {
    //         return result;
    //     }
    // }

    static class MapReducer<E> extends CountedCompleter<E> { // version 2
        final E[]          array;
        final MyMapper<E>  mapper;
        final MyReducer<E> reducer;
        final int          lo, hi;
        MapReducer<E> forks, next; // record subtask forks in list
        E result;

        MapReducer(CountedCompleter<?> p, E[] array, MyMapper<E> mapper,
                   MyReducer<E> reducer, int lo, int hi, MapReducer<E> next) {
            super(p);
            this.array = array;
            this.mapper = mapper;
            this.reducer = reducer;
            this.lo = lo;
            this.hi = hi;
            this.next = next;
        }

        public static <E> E mapReduce(E[] array, MyMapper<E> mapper, MyReducer<E> reducer) {
            return new MapReducer<E>(null, array, mapper, reducer, 0, array.length, null).invoke();
        }

        public void compute() {
            int l = lo, h = hi;
            while (h - l >= 2) {
                int mid = (l + h) >>> 1;
                addToPendingCount(1);
                (forks = new MapReducer<>(this, array, mapper, reducer, mid, h, forks)).fork();
                h = mid;
            }
            if (h > l) {
                result = mapper.apply(array[l]);
            }

            // System.out.println(Thread.currentThread().getName());

            // process completions by reducing along and advancing subtask links
            for (CountedCompleter<?> c = firstComplete(); c != null; c = c.nextComplete()) {
                System.out.println(c);
                for (MapReducer<E> t = (MapReducer<E>) c, s = t.forks; s != null; s = t.forks = s.next) {
                    t.result = reducer.apply(t.result, s.result);
                }
            }
        }

        public E getRawResult() {
            return result;
        }

        @Override
        public String toString() {
            return "low[" + lo +
                    "], high[" + hi +
                    "], forks[" + forks + "]";
        }
    }
}
