package com.hyf.juc.hello;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 分支合并框架
 * <p>
 * ForkJoinPool
 * ForkJoinTask
 * RecursiveTask
 *
 * @author baB_hyf
 * @date 2020/12/07
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CountTask countTask = new CountTask(0, 100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.execute(countTask);
        // countTask.invoke();

        System.out.println(countTask.get());
        forkJoinPool.shutdown();


    }

    static class CountTask extends RecursiveTask<Integer> {

        private final int start;
        private final int end;
        private       int result;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int num = end - start;
            if (num <= 10) {
                for (int i = start; i <= end; i++) {
                    result += i;
                }
                return result;
            }

            int mid = (end + start) / 2;
            CountTask left = new CountTask(start, mid);
            CountTask right = new CountTask(mid + 1, end);

            left.fork();
            right.fork();

            // invokeAll(left, right);

            return left.join() + right.join();
        }
    }

}

