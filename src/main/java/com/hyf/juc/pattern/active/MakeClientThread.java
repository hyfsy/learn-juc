package com.hyf.juc.pattern.active;

/**
 * @author baB_hyf
 * @date 2020/12/24
 */
public class MakeClientThread extends Thread {

    private final ActiveObject activeObject;
    private final char         fillChar;

    public MakeClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        this.fillChar = name.charAt(0);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Result result = activeObject.makeString(i++, fillChar);
                Thread.sleep(20);
                Object value = result.getResultValue();
                System.out.println(currentThread().getName() + ": value=" + value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
