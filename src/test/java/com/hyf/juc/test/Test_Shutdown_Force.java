package com.hyf.juc.test;

/**
 * 指定时间强制结束线程
 *
 * @author baB_hyf
 * @date 2020/12/17
 */
public class Test_Shutdown_Force {

    public static void main(String[] args) {
        ThreadService threadService = new ThreadService();

        long start = System.currentTimeMillis();
        threadService.execute(() -> {
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务执行完毕");
        });

        threadService.shutdown(4000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        // 有问题
        // new Thread(() -> {
        //     try {
        //         Thread.sleep(10000);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }).start();
    }

    private static class ThreadService {

        private Thread executeThread;

        private boolean finished;

        public void execute(Runnable target) {
            executeThread = new Thread(() -> {
                Thread worker = new Thread(target);
                worker.setDaemon(true); // 主线程被中断后，没有其他非守护线程，会直接退出
                worker.start();
                try {
                    worker.join();
                } catch (InterruptedException e) {
                    // ignore worker interrupt
                }
                finished = true;
            });
            executeThread.start();
        }

        public void shutdown(long millis) {
            long start = System.currentTimeMillis();
            while (!finished) {
                if (System.currentTimeMillis() - start > millis) {
                    System.out.println("线程超时被中断！");
                    executeThread.interrupt();
                    break;
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("执行线程被打断");
                    break;
                }
            }
            finished = false;
        }
    }
}
