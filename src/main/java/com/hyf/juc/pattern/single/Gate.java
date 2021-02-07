package com.hyf.juc.pattern.single;

/**
 * 共享资源
 *
 * @author baB_hyf
 * @date 2020/12/19
 */
public class Gate {

    private int    counter = 0;
    private String name    = "NoName";
    private String address = "NoAddress";

    /**
     * 临界值
     *
     * @param name
     * @param address
     */
    public synchronized void pass(String name, String address) { // 对 门口 加锁控制
        this.counter++;
        this.name = name;
        this.address = address;
        verify();
    }

    private void verify() {
        if (this.name.charAt(0) != this.address.charAt(0)) {
            System.out.println("===Broken===: " + toString());
        }
    }

    @Override
    public synchronized String toString() {
        return "Gate:" +
                " counter[" + counter + "]," +
                " name[" + name + "]," +
                " address[" + address + "],";
    }
}
