package com.hyf.juc.mix.forkjoin;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author baB_hyf
 * @date 2021/01/14
 */
public class E {

    private static final Integer expect = 2;

    public static void main(String[] args) {
        // Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer[] ints = {1, 2};
        System.out.println(Searcher.search(ints));
    }

    static class Searcher<E> extends CountedCompleter<E> {
        final E[]                array;
        final AtomicReference<E> result;
        final int                lo, hi;

        Searcher(CountedCompleter<?> p, E[] array, AtomicReference<E> result, int lo, int hi) {
            super(p);
            this.array = array;
            this.result = result;
            this.lo = lo;
            this.hi = hi;
        }

        public static <E> E search(E[] array) {
            return new Searcher<E>(null, array, new AtomicReference<E>(), 0, array.length).invoke();
        }

        public E getRawResult() {
            return result.get();
        }

        public void compute() { // similar to ForEach version 3
            int l = lo, h = hi;
            while (result.get() == null && h >= l) {
                if (h - l >= 2) {
                    int mid = (l + h) >>> 1;
                    addToPendingCount(1);
                    new Searcher(this, array, result, mid, h).fork();
                    h = mid;
                }
                else {
                    E x = array[l];
                    if (matches(x) && result.compareAndSet(null, x)) {
                        quietlyCompleteRoot(); // root task is now joinable
                    }
                    break;
                }
            }
            tryComplete(); // normally complete whether or not found
        }

        boolean matches(E e) { // return true if found
            return e == expect;
        }
    }
}
