package com.hyf.juc.mix;

/**
 * @author baB_hyf
 * @date 2020/12/18
 */
public class RuntimeTest {

    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        // rt.exit(1); // 正常关闭，也包括Ctrl+C
        // rt.halt(1); // 强制关闭
        // Runtime.runFinalizersOnExit(true); // 运行终结器再停止

        System.out.println(rt.freeMemory() / 1024 / 1024); // jvm可用内存
        System.out.println(rt.totalMemory() / 1024 / 1024); // jvm最大内存
        System.out.println(rt.maxMemory() / 1024 / 1024); // jvm所在盘符的最大可用内存
        aaa();

        rt.addShutdownHook(new Thread(() -> {
            System.out.println("shutdown now...");
        }));
    }

    public static void aaa() {
        Runtime rt = Runtime.getRuntime(); // jvm不支持
        rt.traceMethodCalls(true);
        System.out.println("===");
        rt.traceInstructions(true);
    }
}
