package com.linilq.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author lizhijian
 * @description 定义可以支持的信号数量，使得限制多少线程可以工作，可以重复使用；类似RateLimiter
 * 获取-->操作-->回收
 * @date 2020/3/23
 */
public class SemaphoreTest implements Runnable {
    public static Semaphore semaphore = new Semaphore(5);

    private boolean gotSeaPhore = true;
    private boolean actTry = false;
    private boolean giveupAcquireFail = false;

    public SemaphoreTest(boolean actTry) {
        this(actTry, false);
    }

    public SemaphoreTest(boolean actTry, boolean giveupAcquireFail) {
        this.actTry = actTry;
        this.giveupAcquireFail = giveupAcquireFail;
    }

    public static void main(String[] args) {
        derectllyRun(false, false);
//        derectllyRun(true, false);
//        derectllyRun(true, true);

    }

    private static void derectllyRun(boolean actTry, boolean giveupAcquireFail) {
        if (actTry == false && giveupAcquireFail != false) {
            System.out.println("正常执行不允许放弃任务");
            return;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            SemaphoreTest test = new SemaphoreTest(actTry,giveupAcquireFail);
            executorService.submit(test);
        }
        executorService.shutdown();
    }

    @Override
    public void run() {
        try {
            if (actTry) {
                for (; ; ) {
                    gotSeaPhore = semaphore.tryAcquire();
                    if (gotSeaPhore) {
                        break;
                    }
                    if(giveupAcquireFail){
                        System.out.println(Thread.currentThread().getName()+"放弃任务");
                        break;
                    }
                }
                /*do {
                    gotSeaPhore = semaphore.tryAcquire();
                }
                while (!gotSeaPhore && !giveupAcquireFail);*/
            } else {
                semaphore.acquire();
            }
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "任务执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (gotSeaPhore)
                semaphore.release();
        }
    }
}
