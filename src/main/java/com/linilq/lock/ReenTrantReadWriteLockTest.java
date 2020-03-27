package com.linilq.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lizhijian
 * @description 简单的读写重入使用
 * @date 2020/3/26
 */
public class ReenTrantReadWriteLockTest {
    public static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    public static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    public static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    public static void main(String[] args) {
        ReenTrantReadWriteLockTest test = new ReenTrantReadWriteLockTest();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    readLock.lock();
                    test.readTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readLock.unlock();
                }
            }, "read-thread-" + i).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    writeLock.lock();
                    test.writeTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    writeLock.unlock();
                }
            }, "write-thread-" + i).start();
        }
    }

    private void writeTask() throws InterruptedException {

        System.out.println(Thread.currentThread().getName() + " 写内容");
        Thread.sleep(10000);
        System.out.println(Thread.currentThread().getName() + " 写内容完毕");

    }

    private void readTask() throws InterruptedException {

        System.out.println(Thread.currentThread().getName() + " 读取内容");
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + " 读取内容完毕");


    }

}
