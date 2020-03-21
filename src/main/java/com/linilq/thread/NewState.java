package com.linilq.thread;

/**
 * @author lizhijian
 * @description
 * @date 2020/3/21
 */
public class NewState {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println(thread.getState());
    }
}
