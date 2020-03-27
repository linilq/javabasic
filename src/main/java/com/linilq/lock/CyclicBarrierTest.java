package com.linilq.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author lizhijian
 * @description 这个循环栅栏就不得了了；
 * @date 2020/3/26
 */
public class CyclicBarrierTest {

    public static class Soldier {
        private String name;
        CyclicBarrier cyclicBarrier;

        public Soldier(String name, CyclicBarrier cyclicBarrier) {
            this.name = "士兵-" + name;
            this.cyclicBarrier = cyclicBarrier;
        }

        public void gather() {
            try {

                    System.out.println(name + "： 到！");
                    cyclicBarrier.await();
                    work();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

        public void work() {
            try {
                System.out.println(name + " 开始工作");
                Thread.sleep(1000);
                System.out.println(name + " 工作结束");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }
    }

    public static class ProcessRunnale implements Runnable {
        int flag = 0;

        public ProcessRunnale() {
        }

        @Override
        public void run() {
            if (flag == 0) {
                System.out.println("集合完毕");
                flag++;
            } else {
                System.out.println("工作完成，解散");
            }
        }
    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        Soldier[] soldiers = new Soldier[10];
        Thread[] threads = new Thread[10];
        CyclicBarrier cyclicBarrier = new CyclicBarrier(soldiers.length, new ProcessRunnale());
//        work(soldiers, threads, cyclicBarrier);
        workInterruptly(soldiers, threads, cyclicBarrier);
    }

    /**
     * 士兵正常集合-->工作-->解散
     * @param soldiers
     * @param threads
     * @param cyclicBarrier
     */
    private static void work(Soldier[] soldiers, Thread[] threads, CyclicBarrier cyclicBarrier) {

        System.out.println("司令下令：集合！");
        for (int i = 0; i < soldiers.length; i++) {
            soldierWork(soldiers, threads, cyclicBarrier, i, -1);
        }
    }

    /**
     * 士兵集合过程时有人中断：await()时被中断，抛出BrokenBarrierException
     * @param soldiers
     * @param threads
     * @param cyclicBarrier
     */
    private static void workInterruptly(Soldier[] soldiers, Thread[] threads, CyclicBarrier cyclicBarrier) {
        System.out.println("司令下令：集合！");
        int interruptAfterSolderIndex = 5;
        for (int i = 0; i < soldiers.length; i++) {
            soldierWork(soldiers, threads, cyclicBarrier, i, interruptAfterSolderIndex);

        }
    }




    private static void soldierWork(Soldier[] soldiers, Thread[] threads, CyclicBarrier cyclicBarrier, int i, int interruptAfterSolderIndex) {
        Soldier soldier = new Soldier(i + "", cyclicBarrier);
        boolean interrupt = (i == interruptAfterSolderIndex - 1);
        soldiers[i] = soldier;
        threads[i] = new Thread(() -> soldier.gather());
        threads[i].start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (interrupt) {
            threads[i].interrupt();
        }
    }
}
