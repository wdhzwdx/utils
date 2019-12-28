package com.syn;

/**
 *  同级类锁，共用一个，同对象锁一样
 *
 */
public class SynchronizedTest4 {
    public void test1(){
        synchronized(SynchronizedTest4.class){
            for(int i=0;i<5;i++){
                System.out.println(Thread.currentThread().getName()+":"+i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void test2(){
        synchronized(SynchronizedTest4.class){
            for(int i=0;i<5;i++){
                System.out.println(Thread.currentThread().getName()+":"+i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SynchronizedTest4 noSynchronized = new SynchronizedTest4();
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
