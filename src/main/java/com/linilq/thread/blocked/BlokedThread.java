package com.linilq.thread.blocked;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/21
 */
public class BlokedThread extends Thread {
    private volatile boolean stoped = false;
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public BlokedThread(String name) {
        super(name);
    }

    public void stopThread() {
        System.out.println(getName()+" stop notified");
        stoped = true;
    }

    @Override
    public void run() {
        synchronized (BlokedThread.class) {
            System.out.println(getName() + " got the lock");
            while (true) {
                if (stoped) {
                    System.out.println(getName() + " will stop later");
                    return;
                }
            }
        }
    }
}
