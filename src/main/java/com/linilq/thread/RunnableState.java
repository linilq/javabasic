package com.linilq.thread;

import com.linilq.thread.runnable.TestThread;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/21
 */
public class RunnableState {
    public static void main(String[] args) throws InterruptedException {
        TestThread testThread = new TestThread();
        testThread.start();
        System.out.println(testThread.getState());
        Thread.sleep(1000);
        testThread.stopThread();
        Thread.sleep(2000);
        System.out.println(testThread.getState());
    }
}
