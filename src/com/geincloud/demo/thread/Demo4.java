package com.geincloud.demo.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程同步执行 ReentrantLock
 * 尝试lock
 */
public class Demo4 {
    Lock lock = new ReentrantLock();
     void m1(){
         try {
             lock.lock();
             for (int i=0;i<10;i++){
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
         boolean loacked = false;
         try {
             loacked = lock.tryLock(5,TimeUnit.SECONDS);
             System.out.println("m2  loacked:"+loacked);
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             if(loacked){
                 lock.unlock();
             }
         }
     }
    public static void main(String[] args) {
        Demo4 d1 = new Demo4();
        new Thread(d1::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(d1::m2).start();
    }
}
