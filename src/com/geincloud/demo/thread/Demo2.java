package com.geincloud.demo.thread;

import java.util.concurrent.TimeUnit;

/**
 * 多线程同步执行 synchronized
 * 手动上锁，自动解锁
 */
public class Demo2 {
    synchronized void m1(){
        for (int i=0;i<5;i++){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m1:"+i);
        }
    }
    synchronized void m2(){
        System.out.println("m2");
    }
    public static void main(String[] args) {
        Demo2 d1 = new Demo2();
        new Thread(d1::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(d1::m2).start();
    }
}
