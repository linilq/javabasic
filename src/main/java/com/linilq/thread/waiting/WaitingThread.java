package com.linilq.thread.waiting;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/21
 */
public class WaitingThread extends Thread {
    public static final String TYPE_LOCK_UNLOCK = "1";
    public static final String TYPE_OBJECT_WAIT = "2";
    public static final String TYPE_LOCKSUPPORT_PARK = "3";
    public static final String TYPE_THREAD_JOIN = "4";
    private volatile boolean stoped = false;
    public static ReentrantLock reentrantLock = new ReentrantLock();
    private String type;

    public WaitingThread(String name, String type) {
        super(name);
        this.type = type;
    }

    public void stopThread() {
        System.out.println(getName() + " stop notified");
        stoped = true;
    }

    @Override
    public void run() {
        if (type.equals(TYPE_LOCK_UNLOCK)) {
            lockUnlock();
        } else if (type.equals(TYPE_OBJECT_WAIT)) {
            objectWait();
        } else if (type.equals(TYPE_LOCKSUPPORT_PARK)) {
            lockSupportPark();
        } else if (type.equals(TYPE_THREAD_JOIN)) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void lockSupportPark() {
        LockSupport.park(); // 此种方式线程挂起
        realRun();
    }


    private void objectWait() {
        synchronized (reentrantLock) {

            System.out.println(getName() + "进入同步块代码");
            try {
                reentrantLock.wait(); // 此处执行后  锁是已经释放的；其他线程可以进入同步块，但同样也会因为此处进入waiting状态
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            realRun();
        }
    }

    private void lockUnlock() {
        reentrantLock.lock();
        {
            realRun();
        }
        reentrantLock.unlock();
    }

    private void realRun() {
        System.out.println(getName() + " got the lock");
        while (true) {
            if (stoped) {
                System.out.println(getName() + " will stop later");
                break;
            }
        }
    }
}
