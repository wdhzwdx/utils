package com.JUC;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC2 {
    static int state = 0;
    private static Lock lock = new ReentrantLock(true);
    public static void main(String[] args) {

        new Thread(new ThreadC()).start();
        new Thread(new ThreadB()).start();
        new Thread(new ThreadA()).start();

    }

    public static class ThreadA implements Runnable {
        @Override
        public void run() {
            for(int i=0;i<5;){
                lock.lock();
//                System.out.println(Thread.currentThread().getName());
                if(state%3==0){
                    System.out.print("A");
                    state++;
                    i++;
                }
                lock.unlock();
            }
        }
    }



    public static class ThreadB implements Runnable {
        @Override
        public void run() {
            for(int i=0;i<5;){
                lock.lock();
//                System.out.println(Thread.currentThread().getName());
                if(state%3==1){
                    System.out.print("B");
                    state++;
                    i++;
                }
                lock.unlock();
            }
        }

    }

    public static class ThreadC implements Runnable {
        @Override
        public void run() {
            for(int i=0;i<5;){
                lock.lock();
//                System.out.println(Thread.currentThread().getName());
                if(state%3==2){
                    System.out.println("C");
                    state++;
                    i++;
                }
                lock.unlock();
            }
        }

    }

}
