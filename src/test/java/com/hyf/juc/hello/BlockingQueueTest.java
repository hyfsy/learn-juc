package com.hyf.juc.hello;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列测试
 * <p>
 * 当队列为空时，从队列中获取元素会被阻塞
 * 当队列满了时，向队列中添加元素会被阻塞
 *
 * @author baB_hyf
 * @date 2020/12/06
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        // 七种实现类
        // ArrayBlockingQueue:      由数组结构组成的有界阻塞队列。
        // LinkedBlockingQueue:     由链表结构组成的有界(但大小默认值为integer.MAX-VALUE)阻塞队列。
        // PriorityBlockingQueue:   支持优先级排序的无界阻塞队列。
        // DelayQueue:              使用优先级队列实现的延迟无界阻塞队列。
        // SynchronousQueue:        不存储元素的阻塞队列,也即单个元素的队列。
        // LinkedTransferQueue:     由链表组成的无界阻塞队列。
        // LinkedBlockingDeque:     由链表组成的双向阻塞队列。


        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);


        // 方法类型     抛出异常     特殊值       阻塞       超时
        // 插入        add(e)      offer(e)    put(e)    offer(e, time, unit)
        // 移除        remove()    poll()      take()    poll(time, unit)
        // 检查        element()   peek()      不可用     不可用

        // 抛出异常     当阻塞队列满时,再往队列里add插入元素会抛IllegalStateException:Queue full
        //             当阻塞队列空时,再往队列里remove移除元素会抛NoSuchElementException
        // 特殊值  插入方法,成功ture失败false
        //        移除方法,成功返回出队列的元素,队列里没有就返回null
        // 一直阻塞 当阻塞队列满时,生产者线程继续往队列里put元素,队列会一直阻塞生产者线程直到put数据or响应中断退出
        //         当阻塞队列空时,消费者线程试图从队列里take元素,队列会一直阻塞消费者线程直到队列可用
        // 超时退出 当阻塞队列满时,队列会阻塞生产者线程一定时间,超过限时后生产者线程会退出

    }
}
