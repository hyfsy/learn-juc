package com.hyf.juc.aqs;

/**
 * @author baB_hyf
 * @date 2020/12/26
 */
public class AAA {

    public static void main(String[] args) {
        AAA aaa = new AAA();
        System.out.println(aaa);
        AAA bbb = new AAA();
        System.out.println(bbb);

        A a = aaa.createA();

        System.out.println(a.isOwn(aaa));
        System.out.println(a.isOwn(bbb));
    }

    class A {
        public boolean isOwn(AAA aaa) {
            System.out.println(AAA.this);
            return aaa == AAA.this;
        }
    }

    public A createA() {
        return new A();
    }
}
