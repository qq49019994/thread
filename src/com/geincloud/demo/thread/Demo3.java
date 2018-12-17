package com.geincloud.demo.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程同步执行 ReentrantLock
 * 手动上锁，手动解锁
 */
public class Demo3 {
    Lock lock = new ReentrantLock();
     void m1(){
         try {
             lock.lock();
             for (int i=0;i<5;i++){
                 try {
                     TimeUnit.SECONDS.sleep(1);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 System.out.println("m1:"+i);
             }
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             lock.unlock();
         }
     }
     void m2(){
         try {
             lock.lock();
             System.out.println("m2");
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             lock.unlock();
         }
     }
    public static void main(String[] args) {
        Demo3 d1 = new Demo3();
        new Thread(d1::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(d1::m2).start();
    }
}
