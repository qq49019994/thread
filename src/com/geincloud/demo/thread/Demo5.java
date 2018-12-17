package com.geincloud.demo.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程同步执行 ReentrantLock
 * lockInterruptibly 可以被打断
 */
public class Demo5 {
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 =new Thread(()->{
            try {
                lock.lockInterruptibly();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            } catch (Exception e) {
                System.out.println("t1 lockInterruptibly");
            }finally {
                lock.unlock();
            }
        });
        t1.start();
        Thread t2 =new Thread(()->{
            boolean locked = false;
            try {
                lock.lockInterruptibly();
                locked = true;
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            } catch (Exception e) {
                System.out.println("t2 lockInterruptibly");
            }finally {
                if(locked){
                    lock.unlock();
                }
            }
        });
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
    }
}
