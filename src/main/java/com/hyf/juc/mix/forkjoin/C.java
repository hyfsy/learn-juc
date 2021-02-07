package com.hyf.juc.mix.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author baB_hyf
 * @date 2021/01/11
 */
public class C {

    public static void main(String[] args) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        double[] doubles = new double[]{1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8, 9.9, 10};
        double v = new C().sumOfSquares(pool, doubles);
        System.out.println(v);
    }

    double sumOfSquares(ForkJoinPool pool, double[] array) {
        int n = array.length;
        Applyer a = new Applyer(array, 0, n, null);
        pool.invoke(a);
        return a.result;
    }

    class Applyer extends RecursiveAction {
        final double[] array;
        final int      lo, hi;
        double  result;
        Applyer next; // keeps track of right-hand-side tasks

        Applyer(double[] array, int lo, int hi, Applyer next) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
            this.next = next;
        }

        double atLeaf(int l, int h) {
            double sum = 0;
            for (int i = l; i < h; ++i) // perform leftmost base step
            {
                sum += array[i] * array[i];
            }
            return sum;
        }

        protected void compute() {
            int l = lo;
            int h = hi;
            Applyer right = null;
            while (h - l > 1 && getSurplusQueuedTaskCount() <= 3) {
                int mid = (l + h) >>> 1;
                right = new Applyer(array, mid, h, right);
                right.fork();
                h = mid;
            }
            double sum = atLeaf(l, h);
            while (right != null) {
                if (right.tryUnfork()) // directly calculate if not stolen
                {
                    sum += right.atLeaf(right.lo, right.hi);
                }
                else {
                    right.join();
                    sum += right.result;
                }
                right = right.next;
            }
            result = sum;
        }
    }
}
