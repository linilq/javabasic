package com.linilq.lock;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/21
 */
public class SynchronizedTest {
    private int count = 0;
    public static int classInt = 0;

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        SynchronizedTest s1 = new SynchronizedTest();
//        SynchronizedTest s2 = new SynchronizedTest();

//        differSelfLock(s1, s2); // 只能锁住自己的变量，类变量无法把握
//        differClassLock(s1, s2);// 自己的变量、类变量都能正确把握
        // 因为操作的对象相同，所以对象锁能锁住类变量
        sameSelfLock(s1);
//        sameClassLock(s1);
        // 当synchronized修饰非静态方法时，相当于对象锁修饰代码块
        // 当synchronized修饰静态方法时，相当于类锁修饰代码块

    }

    private static synchronized void differClassLock(SynchronizedTest s1, SynchronizedTest s2) {
        new Thread(() -> s1.addOne1()).start();
        new Thread(() -> s2.addOne1()).start();
    }

    private static void differSelfLock(SynchronizedTest s1, SynchronizedTest s2) {
        new Thread(() -> s1.addOne2()).start();
        new Thread(() -> s2.addOne2()).start();
    }

    private static void sameSelfLock(SynchronizedTest s1) {
        new Thread(() -> {
            s1.addOne2();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            s1.addOne2();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("count = "+s1.getCount()+" classInt = "+SynchronizedTest.classInt);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void sameClassLock(SynchronizedTest s1) {
        new Thread(() -> s1.addOne1()).start();
        new Thread(() -> s1.addOne1()).start();
    }

    /**
     * 未加锁
     */
    public void addOne0() {
        realAdd();
    }

    public void addOne1() {
        synchronized (SynchronizedTest.class) {
            realAdd();
        }

    }

    public void addOne2() {
        synchronized (this) {
            realAdd();
        }

    }

    private void realAdd() {
        for (int i = 0; i < 100000; i++) {
            count++;
            classInt++;
        }
        System.out.println(Thread.currentThread().getName() + " count=  " + count + " classInt=  " + classInt);
    }
}
