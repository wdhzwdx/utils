package com.syn;

/**
 *  方法锁和类锁 不共用，互不影响
 *
 */
public class SynchronizedTest3 {
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

    public void test2(){
        synchronized(SynchronizedTest3.class){
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
        SynchronizedTest3 noSynchronized = new SynchronizedTest3();
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
