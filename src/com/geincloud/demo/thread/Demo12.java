package com.geincloud.demo.thread;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 模拟场景
 * 卖票
 */
public class Demo12 {
    static Queue<String> tikets = new ConcurrentLinkedQueue<>();

    static {
        for (int i=10000;i<20000;i++){
            tikets.add("T-"+i);
        }
    }

    public static void main(String[] args) {
        for (int i=0;i<50;i++){
            new Thread(()->{
                while (true){
                    String s = tikets.poll();
                    if(s==null){
                        break;
                    }else{
                        System.out.println("卖出:"+s);
                    }
                }
            }).start();
        }
    }
}
