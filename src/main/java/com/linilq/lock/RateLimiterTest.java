package com.linilq.lock;


import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lizhijian
 * @description guava简单的限流器使用样例；相比于信号量Semophore;它不需要做回收；自动生成等待消耗
 * @date 2020/3/27
 */
public class RateLimiterTest implements Runnable {

    public static RateLimiter rateLimiter = RateLimiter.create(1);

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            executorService.submit(new RateLimiterTest());
        }
        executorService.shutdown();
    }

    public void run() {
        rateLimiter.acquire();
        System.out.println(Thread.currentThread().getName() + " 我来工作");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
