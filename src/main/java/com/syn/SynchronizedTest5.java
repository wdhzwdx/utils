package com.syn;

/**
 *  
 * 修饰静态方法，也是类锁
 */
public class SynchronizedTest5 {
    public synchronized static void test1(){
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
        synchronized(SynchronizedTest5.class){
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
        SynchronizedTest5 noSynchronized = new SynchronizedTest5();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedTest5.test1();
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
