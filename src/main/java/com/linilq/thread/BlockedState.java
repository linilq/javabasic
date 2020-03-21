package com.linilq.thread;

import com.linilq.thread.blocked.BlokedThread;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/21
 */
public class BlockedState {

    public static void main(String[] args) throws InterruptedException {
        BlokedThread blokedThread1 = new BlokedThread("blockedThread-1");
        BlokedThread blokedThread2 = new BlokedThread("blockedThread-2");

        blokedThread1.start();
        Thread.sleep(1000);// 让blokedThread1跑起来先
        System.out.println("============After blokedThread1 start============");
        blokedThread2.start();
        Thread.sleep(1000); // 让blokedThread2跑起来先，否则blokedThread2状态可能为 runnable
        printState(blokedThread1, blokedThread2);// 预期是 RUNNABLE  BLOCKED

        blokedThread1.stopThread();
        Thread.sleep(1000); // 让blokedThread1停下先，否则blokedThread2状态可能为 blocked
        System.out.println("============After blokedThread1 stop============");
        printState(blokedThread1, blokedThread2);// 预期是 TERMINATED  RUNNABLE

        blokedThread2.stopThread();
        Thread.sleep(1000); // 让blokedThread2停下先，否则blokedThread2状态可能为 runnable
        System.out.println("============After blokedThread2 stop============");
        printState(blokedThread1, blokedThread2);// 预期是 TERMINATED  TERMINATED
    }

    private static void printState(BlokedThread blokedThread1, BlokedThread blokedThread2) {
        System.out.println(blokedThread1.getName() + " state " + blokedThread1.getState());
        System.out.println(blokedThread2.getName() + " state " + blokedThread2.getState());
    }
}
