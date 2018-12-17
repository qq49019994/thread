package com.geincloud.demo.thread;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟场景
 * 一个固定容量的为100的同步容器，内部有get和put，getCount方法
 * 能够支持20个生产者以及10个消费者线程的阻塞调用
 * synchronized
 * wait
 * notifyAll
 */
public class Demo9<T> {
    final private LinkedList<T> lists = new LinkedList<T>();
    final private int MAX = 100;

    public synchronized void put(T t) {
        while (lists.size() == MAX) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lists.add(t);
        this.notifyAll();
    }

    public synchronized T get() {
        T t = null;
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        this.notifyAll();
        return t;
    }

    public synchronized int getCount() {
        return lists.size();
    }
    public static void main(String[] args) {
        Demo9 demo9 = new Demo9();

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
