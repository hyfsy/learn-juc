package com.hyf.juc.aqs;

import java.util.Collection;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author baB_hyf
 * @date 2020/12/21
 */
public class AQSTest {

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock writeLock = readWriteLock.writeLock();
        Lock readLock = readWriteLock.readLock();
        writeLock.lock();

        // Thread.sleep(3000);

        Thread t1 = new Thread(() -> {
            try {
                readLock.lock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            try {
                readLock.lock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t2.start();
        Thread t3 = new Thread(() -> {
            try {
                readLock.lock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t3.start();

        Thread.sleep(2000);

        writeLock.unlock();


        /*Lock lock = new ReentrantLock();
        lock.lock();
        Thread t1 = new Thread(() -> {
            lock.lock();
            // try {
            //     Thread.sleep(5000);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            System.out.println("Thread interrupt: " + Thread.interrupted());
        });
        t1.start();

        Thread.sleep(1000);

        t1.interrupt();
        System.out.println(t1.isInterrupted());

        Thread.sleep(1000);
        lock.unlock();*/

    }

    static class Sync extends AbstractQueuedSynchronizer {

        private ConditionObject condition = new ConditionObject();

        @Override
        protected boolean tryAcquire(int arg) {
            // acquire(1);
            // acquireInterruptibly(1);
            // acquireShared(1);
            // acquireSharedInterruptibly(1);
            release(1);
            releaseShared(1);

            Collection<Thread> exclusiveQueuedThreads = getExclusiveQueuedThreads();
            Collection<Thread> sharedQueuedThreads = getSharedQueuedThreads();
            Collection<Thread> queuedThreads = getQueuedThreads();
            Thread firstQueuedThread = getFirstQueuedThread();
            int queueLength = getQueueLength();
            Collection<Thread> waitingThreads = getWaitingThreads(condition);
            int waitQueueLength = getWaitQueueLength(condition);

            boolean b = hasContended();
            boolean b1 = hasQueuedPredecessors();
            boolean b2 = hasQueuedThreads();
            boolean b3 = hasWaiters(condition);

            boolean queued = isQueued(Thread.currentThread());
            boolean owns = owns(condition);

            return super.tryAcquire(arg);
        }

        @Override
        protected boolean tryRelease(int arg) {
            return super.tryRelease(arg);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return super.tryAcquireShared(arg);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            return super.tryReleaseShared(arg);
        }

        @Override
        protected boolean isHeldExclusively() {
            return super.isHeldExclusively();
        }
    }
}
