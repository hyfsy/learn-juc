package com.hyf.juc.mix.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author baB_hyf
 * @date 2021/01/11
 */
public class B {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        SortTask task = (SortTask)pool.submit(new SortTask(new long[]{2, 4, 6, 8, 0, 1, 3, 5, 7, 9}));
        task.get();
        System.out.println(Arrays.toString(task.getArray()));
    }

    static class SortTask extends RecursiveAction {
        // implementation details follow:
        static final int    THRESHOLD = 5;
        final        long[] array;
        final        int    lo, hi;

        SortTask(long[] array, int lo, int hi) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
        }

        SortTask(long[] array) {
            this(array, 0, array.length);
        }

        protected void compute() {
            if (hi - lo < THRESHOLD) {
                sortSequentially(lo, hi);
            }
            else {
                int mid = (lo + hi) >>> 1;
                invokeAll(new SortTask(array, lo, mid),
                        new SortTask(array, mid, hi));
                merge(lo, mid, hi);
            }
        }

        void sortSequentially(int lo, int hi) {
            Arrays.sort(array, lo, hi);
        }

        void merge(int lo, int mid, int hi) {
            long[] buf = Arrays.copyOfRange(array, lo, mid);
            for (int i = 0, j = lo, k = mid; i < buf.length; j++) {
                array[j] = (k == hi || buf[i] < array[k]) ?
                        buf[i++] : array[k++];
            }
            System.out.println(Arrays.toString(array));
        }

        public long[] getArray () {
            return array;
        }
    }
}
