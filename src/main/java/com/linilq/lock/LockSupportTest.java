package com.linilq.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lizhijian
 * @description 这个LockSupport基本上就是所有java代码实现锁的基础；而进一步它又是依赖Unsafe类来实现CAS计算的；
 * 这里简单展示他的两种使用
 * @date 2020/3/27
 */
public class LockSupportTest {

    public static void main(String[] args) {
//        park(-1L);
//        parkThenInterrupt(-1);
//        park(5*1000*1000*1000L);
//        parkThenInterrupt(5*1000*1000*1000L);
        parkAfterUnpark();
    }

    /**
     * 先unpark再park   不会阻塞线程  不会抛出异常
     */
    private static void parkAfterUnpark() {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 我开始工作咯");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + " 我工作完成了");
        }, "thread-1");
        t1.start();

        LockSupport.unpark(t1);
    }

    /**
     * park方法之后线程中断不会抛出异常
     */
    private static void parkThenInterrupt(long naros) {
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 开始工作咯");
            if (naros > 0) {
                LockSupport.parkNanos(naros);
            } else {
                LockSupport.park();
            }
            System.out.println(Thread.currentThread().getName() + " 工作完成了");
        }, "thread-2");
        t2.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 执行中断");
        t2.interrupt();
    }

    private static void park(long naros) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 我开始工作咯");
            if (naros > 0) {
                LockSupport.parkNanos(naros);
            } else {
                LockSupport.park();
            }
            System.out.println(Thread.currentThread().getName() + " 我工作完成了");
        }, "thread-1");
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t1);

    }


}
