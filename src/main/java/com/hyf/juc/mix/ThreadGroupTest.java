package com.hyf.juc.mix;

/**
 * @author baB_hyf
 * @date 2020/12/18
 */
public class ThreadGroupTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup threadGroup = new ThreadGroup(rootGroup, "G1");

        Thread t1 = new Thread(threadGroup, () -> {
            ThreadGroup customGroup = Thread.currentThread().getThreadGroup();
            System.out.println(customGroup.getName());
            customGroup.activeCount(); // 当前组及子组的活动线程数量
            customGroup.activeGroupCount(); // 当前组及子组的活动的数量
            customGroup.list(); // 打印组内线程的所有信息
            System.out.println(customGroup.getMaxPriority()); // 优先级默认取父线程组的优先级
            Thread[] threads = new Thread[customGroup.activeCount()];
            System.out.println(customGroup.enumerate(threads)); // 将组内线程拷贝到数组中，并返回实际传入数组的线程数量
            System.out.println(customGroup.getParent()); // 获取父线程组
            System.out.println(customGroup.isDaemon()); // 守护线程组会在线程/子线程组都停止时自动销毁
            System.out.println(customGroup.isDestroyed());
            customGroup.interrupt(); // 中断组内的所有线程
            // 设置引发异常的线程和异常实例，可重写该方法逻辑
            // customGroup.uncaughtException(new Thread(() -> {
            //     System.out.println("中断");
            // }), new RuntimeException()); // new ThreadDeath
            throw new RuntimeException("asdfas");
        });

        t1.start();
        t1.join();

        threadGroup.destroy(); // 必须当组中没有活动线程才能销毁
    }
}
