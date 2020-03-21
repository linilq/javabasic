package com.linilq.thread;

import com.linilq.thread.waiting.WaitingThread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/21
 */
public class WaitingState {

    public static void main(String[] args) throws InterruptedException {

//        testLockUnlock();
//        testLockSupport();
        testObjectWait();
//        testJoin();
    }

    private static void testJoin() throws InterruptedException {
        WaitingThread waitingThread1 = new WaitingThread("blockedThread-1", WaitingThread.TYPE_THREAD_JOIN);
        waitingThread1.start();
        Thread.sleep(1000);
        printState(waitingThread1);

    }

    private static void testObjectWait() throws InterruptedException {
        WaitingThread waitingThread1 = new WaitingThread("blockedThread-1", WaitingThread.TYPE_OBJECT_WAIT);
        WaitingThread waitingThread2 = new WaitingThread("blockedThread-2", WaitingThread.TYPE_OBJECT_WAIT);
        waitingThread1.start();
        Thread.sleep(1000);// 让waitingThread1跑起来先
        printState(waitingThread1);
        waitingThread2.start();
        Thread.sleep(1000);// 让waitingThread2跑起来先
        printState(waitingThread2);
        synchronized (WaitingThread.reentrantLock) {
            WaitingThread.reentrantLock.notifyAll();
        }
        Thread.sleep(1000);// 让waitingThread1 释放起作用跑起来先
        printState(waitingThread1,waitingThread2);
        waitingThread1.stopThread();
//        Thread.sleep(1000);// 让waitingThread1先停掉，不然一直
//        synchronized (WaitingThread.reentrantLock) {
//            WaitingThread.reentrantLock.notify();
//        }
//        Thread.sleep(1000);// waitingThread2 释放起作用跑起来先
        waitingThread2.stopThread();
    }

    private static void testLockSupport() throws InterruptedException {
        WaitingThread waitingThread1 = new WaitingThread("blockedThread-1", WaitingThread.TYPE_LOCKSUPPORT_PARK);
        WaitingThread waitingThread2 = new WaitingThread("blockedThread-2", WaitingThread.TYPE_LOCKSUPPORT_PARK);
        waitingThread1.start();
        Thread.sleep(1000);// 让waitingThread1跑起来先
//        waitingThread2.start();
        printState(waitingThread1);
        LockSupport.unpark(waitingThread1);
        Thread.sleep(1000);
//        printState(waitingThread2);
        printState(waitingThread1);
        waitingThread1.stopThread();
        Thread.sleep(1000);
        printState(waitingThread1);
    }

    private static void testLockUnlock() throws InterruptedException {
        WaitingThread waitingThread1 = new WaitingThread("blockedThread-1", WaitingThread.TYPE_LOCK_UNLOCK);
        WaitingThread waitingThread2 = new WaitingThread("blockedThread-2", WaitingThread.TYPE_LOCK_UNLOCK);
        waitingThread1.start();
        Thread.sleep(1000);// 让waitingThread1跑起来先
        System.out.println("============After waitingThread1 start============");
        waitingThread2.start();
        Thread.sleep(1000); // 让waitingThread2跑起来先，否则waitingThread2状态可能为 runnable
        printState(waitingThread1, waitingThread2);// 预期是 RUNNABLE  WAITING

        waitingThread1.stopThread();
        Thread.sleep(1000); // 让waitingThread1停下先，否则waitingThread2状态可能为 WAITING
        System.out.println("============After waitingThread1 stop============");
        printState(waitingThread1, waitingThread2);// 预期是 TERMINATED  RUNNABLE

        waitingThread2.stopThread();
        Thread.sleep(1000); // 让waitingThread2停下先，否则waitingThread2状态可能为 RUNNABLE
        System.out.println("============After waitingThread2 stop============");
        printState(waitingThread1, waitingThread2);// 预期是 TERMINATED  TERMINATED
    }

    private static void printState(Thread... waitingThread) {
        for (int i = 0; i < waitingThread.length; i++) {
            System.out.println(waitingThread[i].getName() + " state " + waitingThread[i].getState());
        }
    }
}
