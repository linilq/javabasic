package com.linilq.lock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/22
 */
public class ReentrantLockTest {
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static ReentrantLock reentrantLockFair = new ReentrantLock(true);

    public static Condition writeLetter = reentrantLockFair.newCondition();
    public static Condition writeNum = reentrantLockFair.newCondition();

    int count;

    public void addCount() {
        if (Thread.currentThread().getName().equals("pool-1-thread-1")) {
            reentrantLockFair.unlock();
        }
        reentrantLockFair.lock();
        System.out.println(Thread.currentThread().getName() + " 开始计算");
        for (int i = 0; i < 10; i++) {
            count++;
        }
        reentrantLockFair.unlock();
    }

    public static void writeLetter() {
        reentrantLockFair.lock();
        try {
            for (int i = 65; i < 90; i++) {
                System.out.println((char) i);
                writeNum.signal();
                if (i == 89)
                    break;
                writeLetter.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLockFair.unlock();
        }
    }

    public static void writeNum() {
        reentrantLockFair.lock();
        try {
            for (int i = 1; i < 25; i++) {
                writeNum.await();
                System.out.println(i);
                writeLetter.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLockFair.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        reEntry();

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
    private static void reEntry() {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        ReentrantLockTest test = new ReentrantLockTest();
        test.addCount();
        Future future1 = executor.submit(() -> test.addCount());

//        Future future2 = executor.submit(() -> test.addCount(), "thread2");
//        Future future3 = executor.submit(() -> test.addCount(), "thread3");

        try {
            future1.get();
//            future2.get();
//            future3.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(test.count);
        executor.shutdown();
    }
}
