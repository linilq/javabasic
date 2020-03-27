package com.linilq.lock;

import java.util.concurrent.*;

/**
 * @author lizhijian
 * @description 简单说就像一个线程计数器；知道多少个线程执行完毕；
 * 才允许countDownLatch.await()释放；一次定义，一次使用；
 * 使用->释放
 * @date 2020/3/23
 */
public class CountDownLatchTest implements Runnable {

    public static CountDownLatch countDownLatch = new CountDownLatch(5);

    private int count;

    public void addCount() {
        int i = 0;
        while (i++ < 1000) {
            count++;
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName() + "任务执行完毕");
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("中断异常");
        }finally {

        }
    }

    public static void main(String[] args) throws InterruptedException {
//        countDown5Times();
        countDownAwaitTimely();
    }

    private static void countDownAwaitTimely() throws InterruptedException {
        CountDownLatchTest test = new CountDownLatchTest();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(test);
        }
        System.out.println("取锁等待任务执行");
        boolean gotWait = countDownLatch.await(5000, TimeUnit.MILLISECONDS);
        if (gotWait) {
            System.out.println("任务执行完毕;gotWait= true");
        } else {
            System.out.println("没拿到锁，放弃;state=" + Thread.currentThread().getState());
        }
        executorService.shutdown();
    }

    private static void countDown5Times() throws InterruptedException {
        CountDownLatchTest test = new CountDownLatchTest();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(test);
        }
        System.out.println("取锁等待任务执行");
        countDownLatch.await();
        System.out.println("任务执行完毕");
        executorService.shutdown();
    }
}
