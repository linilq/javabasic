package com.linilq.thread.runnable;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/21
 */
public class TestThread extends Thread {
    private volatile boolean stoped = false;

    public void stopThread() {
        System.out.println("stop notified");
        stoped = true;
    }

    @Override
    public void run() {
        while (true) {
            if (stoped) {
                System.out.println(Thread.currentThread().getName() + " will stop later");
                return;
            }
        }
    }
}
