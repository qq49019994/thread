package com.geincloud.demo.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程同步执行 ReentrantLock
 * 公平锁
 */
public class Demo8 extends Thread{
    static Lock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i=0;i<100;i++){
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"获得锁");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Demo8 r1 = new Demo8();
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r1);
        Thread t3 = new Thread(r1);
        Thread t4 = new Thread(r1);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
