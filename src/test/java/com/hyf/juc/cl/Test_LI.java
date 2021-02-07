package com.hyf.juc.cl;

/**
 * @author baB_hyf
 * @date 2020/12/20
 */
public class Test_LI {

    // private static Test_Final2 instance = new Test_Final2();

    public static int x = 0;
    public static int y;

    private static Test_LI instance = new Test_LI();

    // Linking -> Initialising

    /**
     * x = 0;
     * y = 0;
     * instance = null;
     *
     * x = 0;
     * instance = new Instance();
     *   x = 1;
     *   y = 1;
     */

    /**
     * instance = null;
     * x = 0;
     * y = 0;
     *
     * instance = new Instance();
     *   x = 1;
     *   y = 1;
     *
     * x = 0
     */
    private Test_LI() {
        x++;
        y++;
    }

    public static Test_LI getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        Test_LI final2 = Test_LI.getInstance();
        System.out.println(final2.x);
        System.out.println(final2.y);
    }
}
