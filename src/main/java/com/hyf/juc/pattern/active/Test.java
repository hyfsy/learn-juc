package com.hyf.juc.pattern.active;

/**
 * Active Objects
 *
 * @author baB_hyf
 * @date 2020/12/19
 */
public class Test {

    public static void main(String[] args) {
        // System.gc();

        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakeClientThread("Tom", activeObject).start();
        new MakeClientThread("Atom", activeObject).start();
        new DisplayClientThread("Jerry", activeObject).start();


    }
}
