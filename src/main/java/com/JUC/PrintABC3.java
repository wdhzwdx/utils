package com.JUC;

import java.util.concurrent.atomic.AtomicInteger;

public class PrintABC3 {
    static int state = 0;
    private static AtomicInteger lock = new AtomicInteger(0);
    public static void main(String[] args) {
//        ExecutorService ser = Executors.newCachedThreadPool();
//        ser.submit(new ThreadA());
//        ser.submit(new ThreadB());
//        ser.submit(new ThreadC());
//
//        ser.shutdown();

        new Thread(new ThreadC()).start();
        new Thread(new ThreadB()).start();
        new Thread(new ThreadA()).start();

    }

    public static class ThreadA implements Runnable {
        @Override
        public void run() {
            for(int i=0;i<5;){
                System.out.println(Thread.currentThread().getName());
                if(state%3==0&&lock.compareAndSet(0,1)){
                    System.out.print("A");
                    state++;
                    i++;
                }

            }

        }
    }



    public static class ThreadB implements Runnable {
        @Override
        public void run() {
            for(int i=0;i<5;){
                System.out.println(Thread.currentThread().getName());
                if(state%3==1&&lock.compareAndSet(1,2)){
                    System.out.print("B");
                    state++;
                    i++;
                }
            }
        }

    }

    public static class ThreadC implements Runnable {
        @Override
        public void run() {
            for(int i=0;i<5;){
                System.out.println(Thread.currentThread().getName());
                if(state%3==2&&lock.compareAndSet(2,0)){
                    System.out.println("C");
                    state++;
                    i++;
                }
            }
        }

    }

}
