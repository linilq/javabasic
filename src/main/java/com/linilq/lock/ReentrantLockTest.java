package com.linilq.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/22
 */
public class ReentrantLockTest {
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static Condition awaitCondition;
    public static Condition writeNumCondition;
    public static Condition writeLetterCondition;
    private int count;

    public static void main(String[] args) throws InterruptedException {
//        reEntryTest(false);
//        lockInterruptlyThrowException(false);
        conditionAwaitIntercepted(false);
//        writeNumLetter();
//        tryLockTimely(false);

    }

    public static void tryLockTimely(boolean useFair) {
        ReentrantLockTest test = new ReentrantLockTest(useFair);
        Thread t1 = new Thread(() -> {
            try {
                reentrantLock.lock();
                Thread.sleep(5000);
                test.addCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

        }, "thread-1");
        t1.start();
        Thread t2 = new Thread(() -> {
            boolean gotTheLock = false;
            try {
                gotTheLock = reentrantLock.tryLock(2000, TimeUnit.MICROSECONDS);
                if (gotTheLock) {
                    test.addCount();
                } else {
                    System.out.println(Thread.currentThread().getName() + " 2秒内没法获取锁资源，自动放弃");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (gotTheLock)
                    reentrantLock.unlock();
            }
        }, "thread-2");
        t2.start();

    }

    /**
     * 使用Condition的前提条件是必先获取锁；当condition.await()时，锁资源将会释放，但线程应处于waiting状态
     *
     * @param useFair
     * @throws InterruptedException
     */
    private static void conditionAwaitIntercepted(boolean useFair) throws InterruptedException {
        ReentrantLockTest test = new ReentrantLockTest(useFair);
        awaitCondition = reentrantLock.newCondition();
        Thread t1 = new Thread(() -> {
            try {
                reentrantLock.lockInterruptibly();
                System.out.println("t1获得锁，准备释放锁并挂起");
                awaitCondition.await();
                test.addCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("t1中断进入异常");
            } finally {
                System.out.println("t1结束");
                awaitCondition.signal();
                reentrantLock.unlock();
            }

        }, "Thread-1");
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                reentrantLock.lockInterruptibly();
                System.out.println("t2获得锁");
                test.addCount();
                awaitCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

        }, "Thread-2");
        t2.start();

        Thread.sleep(1000);
        t1.interrupt();
    }

    /**
     * 中断抛异常
     *
     * @param useFair
     * @throws InterruptedException
     */
    private static void lockInterruptlyThrowException(boolean useFair) throws InterruptedException {
        ReentrantLockTest test = new ReentrantLockTest(useFair);
        Thread t1 = new Thread(() -> {
            try {
                reentrantLock.lockInterruptibly();
                System.out.println("t1获得锁，休息3秒");
                Thread.sleep(3000);
                test.addCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("t1中断进入异常");
            } finally {
                reentrantLock.unlock();
            }

        }, "Thread-1");
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                reentrantLock.lockInterruptibly();
                System.out.println("t2获得锁");
                test.addCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }

        }, "Thread-2");
        t2.start();

        Thread.sleep(1000);
        t1.interrupt();

    }

    public ReentrantLockTest(boolean userFairLock) {
        if (reentrantLock == null) {
            reentrantLock = new ReentrantLock(userFairLock);
        } else if (userFairLock != reentrantLock.isFair()) {
            reentrantLock = new ReentrantLock(userFairLock);
        }

    }

    public void addCount() {
        System.out.println(Thread.currentThread().getName() + " 开始计算自增");
        for (int i = 0; i < 1000; i++) {
            count++;
        }
        System.out.println(Thread.currentThread().getName() + " 自增结束，count = " + count);
    }

    public void minusCount() {
        System.out.println(Thread.currentThread().getName() + " 开始计算自减");
        for (int i = 0; i < 100; i++) {
            count--;
        }
    }

    /**
     * 交替写字母、数字
     *
     * @throws InterruptedException
     */
    private static void writeNumLetter() throws InterruptedException {
        writeNumCondition = reentrantLock.newCondition();
        writeLetterCondition = reentrantLock.newCondition();
        new Thread(() -> {
            writeNum();
        }, "thread-1").start();
        Thread.sleep(1000);
        new Thread(() -> {
            writeLetter();

        }, "thread-2").start();
    }

    /**
     * 简单的可从重入测试
     */
    private static void reEntryTest(boolean useFair) {
        ReentrantLockTest test = new ReentrantLockTest(useFair);
        reentrantLock.lock();
        System.out.println(Thread.currentThread().getName() + " 获得锁第一次");
        reentrantLock.lock();
        System.out.println(Thread.currentThread().getName() + " 获得锁第二次");
        test.addCount();
        reentrantLock.unlock();
        System.out.println(Thread.currentThread().getName() + " 释放锁第一次");
        reentrantLock.unlock();
        System.out.println(Thread.currentThread().getName() + " 释放锁第二次，程序退出");
    }

    public static void writeLetter() {
        reentrantLock.lock();
        try {
            for (int i = 65; i < 90; i++) {
                System.out.println((char) i);
                writeNumCondition.signal();
                if (i == 89)
                    break;
                writeLetterCondition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void writeNum() {
        reentrantLock.lock();
        try {
            for (int i = 1; i < 25; i++) {
                writeNumCondition.await();
                System.out.println(i);
                writeLetterCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }
}
