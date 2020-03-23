package com.linilq.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/22
 */
public class ReentrantLockTest {
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static ReentrantLock reentrantLockFair = new ReentrantLock(true);

    private int count = 0;

    public static void main(String[] args) {

        reEntry();

    }

    /**
     * 简单的可从重入测试
     */
    private static void reEntry() {

        ReentrantLockTest test = new ReentrantLockTest();
        reentrantLock.lock();
        reentrantLock.lock();
        for (int i = 0; i < 10; i++) {
            test.count++;
        }
        reentrantLock.unlock();
        reentrantLock.unlock();

        System.out.println(test.count);
    }
}
