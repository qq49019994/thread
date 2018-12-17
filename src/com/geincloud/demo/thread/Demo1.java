package com.geincloud.demo.thread;

import java.util.concurrent.TimeUnit;

/**
 * 多线程执行顺序
 */
public class Demo1 {
    void m1(){
        for (int i=0;i<5;i++){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m1:"+i);
        }
    }
    void m2(){
        System.out.println("m2");
    }
    public static void main(String[] args) {
        Demo1 d1 = new Demo1();
        new Thread(d1::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(d1::m2).start();
    }
}
