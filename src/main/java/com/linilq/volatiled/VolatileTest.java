package com.linilq.volatiled;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/23
 */
public class VolatileTest {
    private int k = 0;

    public int getK(){
        return k;
    }
    public void addK() {
//        System.out.println(Thread.currentThread().getName()+"自增前k=" + k);
        k++;
//        System.out.println(Thread.currentThread().getName()+"自增后k=" + k);
    }

    public static void main(String[] args) {
        VolatileTest t1 = new VolatileTest();
        new Thread(() -> {
            for(int i=0;i<5;i++){
                t1.addK();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread thread2=  new Thread(() -> {
            for(int i=0;i<5;i++){
                t1.addK();
            }

        });


        System.out.println(Thread.currentThread().getName()+"自增后k=" + t1.getK());
    }
}
