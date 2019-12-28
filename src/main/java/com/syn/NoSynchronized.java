package com.syn;

public class NoSynchronized {
    public void test1(){
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
        NoSynchronized noSynchronized = new NoSynchronized();
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
