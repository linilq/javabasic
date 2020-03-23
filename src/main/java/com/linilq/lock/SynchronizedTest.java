package com.linilq.lock;

/**
 * 这里主要展示对象锁、类锁
 *
 * @author lizhijian
 * @description
 * @date 2020/3/21
 */
public class SynchronizedTest {
    private int count = 0;
    public static int classInt = 0;

    public int getCount() {
        count++;
        return count;
    }

    public static void main(String[] args) {
        SynchronizedTest s1 = new SynchronizedTest();
//        SynchronizedTest s2 = new SynchronizedTest();

//        differSelfLock(s1, s2); // 只能锁住自己的变量，类变量无法把握
//        differClassLock(s1, s2);// 自己的变量、类变量都能正确把握
        // 因为操作的对象相同，所以对象锁能锁住类变量
//        sameSelfLock(s1);
//        sameClassLock(s1);
        // 当synchronized修饰非静态方法时，相当于对象锁修饰代码块
        // 当synchronized修饰静态方法时，相当于类锁修饰代码块
        sameNoLock(s1);

    }

    /**
     * 场景：多线程操作不同对象时，使用类锁
     * 1、对于不同对象内的成员变量操作，这把锁可加可不加
     * 2、对于类的静态变量，那么类锁能正确控制它的操作
     *
     * @param s1
     * @param s2
     */
    private static synchronized void differClassLock(SynchronizedTest s1, SynchronizedTest s2) {
        new Thread(() -> s1.addOne1()).start();
        new Thread(() -> s2.addOne1()).start();
    }

    /**
     * 场景：多线程操作不同对象时，使用对象锁
     * 1、对于不同对象内的成员变量操作，这把锁可加可不加
     * 2、对于类的静态变量，那么对象锁无法能正确控制它的操作
     *
     * @param s1
     * @param s2
     */
    private static void differSelfLock(SynchronizedTest s1, SynchronizedTest s2) {
        new Thread(() -> s1.addOne2()).start();
        new Thread(() -> s2.addOne2()).start();
    }

    /**
     * 场景：多线程操作相同对象时，使用对象锁
     * 1、对象内的成员变量操作，对象锁可正确控制
     * 2、对于类的静态变量，对象锁不能正确控制它的操作
     *
     * @param s1
     */
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
                    System.out.println("count = " + s1.getCount() + " classInt = " + SynchronizedTest.classInt);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 场景：多线程操作相同对象时，使用类锁
     * 1、对象内的成员变量操作，类锁可正确控制
     * 2、对于类的静态变量，类锁能正确控制它的操作
     *
     * @param s1
     */
    private static void sameClassLock(SynchronizedTest s1) {
        new Thread(() -> s1.addOne1()).start();
        new Thread(() -> s1.addOne1()).start();
    }

    /**
     * @param s1
     */
    private static void sameNoLock(SynchronizedTest s1) {
        new Thread(() -> s1.addOne0(), "thread1").start();
        new Thread(() -> s1.addOne0(), "thread2").start();
        new Thread(() -> s1.addOne0(), "thread3").start();
        new Thread(() -> s1.addOne0(), "thread4").start();
        new Thread(() -> s1.addOne0(), "thread5").start();
        System.out.println(s1.getCount());
    }

    /**
     * 未加锁
     */
    public void addOne0() {
        realAdd();
    }

    /**
     * 使用类锁
     */
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
        System.out.println(Thread.currentThread().getName() + "开始： count=  " + count + " classInt=  " + classInt);
        for (int i = 0; i < 100000; i++) {
            count++;
            classInt++;
        }
        System.out.println(Thread.currentThread().getName() + "结束： count=  " + count + " classInt=  " + classInt);
    }

}
