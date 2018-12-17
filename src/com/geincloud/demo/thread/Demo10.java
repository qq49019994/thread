package com.geincloud.demo.thread;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟场景
 * 一个固定容量的为100的同步容器，内部有get和put，getCount方法
 * 能够支持20个生产者以及10个消费者线程的阻塞调用
 * Lock Condition 精确解锁
 * await
 * signalAll
 */
public class Demo10<T> {
    final private LinkedList<T> lists = new LinkedList<T>();
    final private int MAX = 100;
    private static Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();
    public  void put(T t) {
        try {
            lock.lock();
            while (lists.size() == MAX) {
                producer.await();
            }
            lists.add(t);
            consumer.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public  T get() {
        T t = null;
        try {
            lock.lock();
            while (lists.size() == 0) {
                consumer.await();
            }
            t = lists.removeFirst();
            producer.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public synchronized int getCount() {
        return lists.size();
    }
    public static void main(String[] args) {
        Demo10 demo9 = new Demo10();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + ":    消费数据:" + demo9.get() +"    剩余数据:"+demo9.getCount());
                }
            }, "c" + i).start();
        }

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                while (true) {
                    String data = UUID.randomUUID().toString();
                    System.out.println(Thread.currentThread().getName()+":    生产数据:"+data+"    剩余数据:"+demo9.getCount());
                    demo9.put(data);
                }
            }, "p" + i).start();
        }

    }
}
