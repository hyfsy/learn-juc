package com.hyf.juc.sample.pipeline;

/**
 * 并行流水线模式
 * <p>
 * ((i + j) * i) / 2
 *
 * @author baB_hyf
 * @date 2021/03/15
 */
public class Test {

    public static void main(String[] args) {

        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();
        new Thread(new Div()).start();

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                String str = "((" + i + " + " + j + ") * " + i + ") / 2";
                Msg msg = new Msg(i, j, str);
                Plus.queue.add(msg);
            }
        }
    }
}
