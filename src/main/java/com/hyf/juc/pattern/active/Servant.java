package com.hyf.juc.pattern.active;

/**
 * @author baB_hyf
 * @date 2020/12/20
 */
class Servant implements ActiveObject {

    @Override
    public Result makeString(int count, char fillChar) {
        char[] buf = new char[count];

        for (int i = 0; i < count; i++) {
            buf[i] = fillChar;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new RealResult(String.valueOf(buf));
    }

    @Override
    public void displayString(String text) {
        System.out.println("Display String: " + text);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
