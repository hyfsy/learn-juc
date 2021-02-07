package com.hyf.juc.pattern.single;

/**
 * @author baB_hyf
 * @date 2020/12/19
 */
public class User extends Thread {

    private final String name;
    private final String address;
    private final Gate gate;

    public User(String name, String address, Gate gate) {
        this.name = name;
        this.address = address;
        this.gate = gate;
    }

    @Override
    public void run() {
        System.out.println(name + " Begin");
        while (true) {
            this.gate.pass(name, address);
        }
    }
}
