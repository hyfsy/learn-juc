package com.hyf.juc.cacheline;

/**
 * CPU乱序执行
 *
 * @author baB_hyf
 * @date 2021/01/26
 */
public class Test_Disorder {

    public static int x, y, a, b;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;y = 0;
            a = 0;b = 0;
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });

            t1.start();t2.start();
            t1.join();t2.join();
            String result = "第" + i + "次（" + x + ", " + y + "）";
            if (x == 0 && y == 0) {
                System.err.println(result);
                break;
            }
        }
    }
}
