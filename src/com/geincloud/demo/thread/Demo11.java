package com.geincloud.demo.thread;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal 用于保存某个线程共享变量:对于同一个static ThreadLocal,不同线程只能从中get,set,remove自己的变量,而不会影响其他线程的变量
 * ThreadLocal 效率较高 以空间换时间
 * ThreadLocal 可能会导致内存溢出
 */
public class Demo11 {
    static ThreadLocal<Userinfo> tl = new ThreadLocal<>();
    public static void main(String[] args) {
        new Thread(()->{
            System.out.println(tl.get());
        }).start();
        new Thread(()->{
            tl.set(new Userinfo());
            System.out.println(tl.get());
        }).start();
    }
    static class Userinfo{
        String name = "zhangsan";

        @Override
        public String toString() {
            return "name:"+name;
        }
    }
}
