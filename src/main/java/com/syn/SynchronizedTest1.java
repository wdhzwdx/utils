package com.syn;

/**
 *  方法锁和对象锁 其实是同一个锁
 * 修饰方法，代表获取都是同一个锁，也称对象锁
 */
public class SynchronizedTest1 {
    public synchronized void test1(){
        for(int i=0;i<5;i++){
            System.out.println(Thread.currentThread().getName()+":"+i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void test2(){
        for(int i=0;i<5;i++){
            System.out.println(Thread.currentThread().getName()+":"+i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SynchronizedTest1 noSynchronized = new SynchronizedTest1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                noSynchronized.test1();
            }
        },"test1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                noSynchronized.test2();
            }
        },"test2").start();
    }
    
}
