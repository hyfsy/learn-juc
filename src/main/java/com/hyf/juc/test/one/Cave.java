package com.hyf.juc.test.one;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class Cave {

    private final Object lock = new Object();

    public void guoshandong(Person person) {
        synchronized (lock) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(person.getName() + "通过山洞");
        }
    }
}
