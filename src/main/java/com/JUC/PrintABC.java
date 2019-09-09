package com.JUC;

public class PrintABC {
    static int state = 0;
    private static Object o = new Object();
    public static void main(String[] args) {
//        ExecutorService ser =  Executors.newCachedThreadPool();
//        ser.submit(new ThreadA());
//        ser.submit(new ThreadB());
//        ser.submit(new ThreadC());
//
//        ser.shutdown();

        new Thread(new ThreadA()).start();
        new Thread(new ThreadB()).start();
        new Thread(new ThreadC()).start();
    }

    public static class ThreadA implements Runnable {

        @Override
        public void run() {

            for(int i=0;i<10;i++){
                synchronized (o) {
                    while(state%3!=0){
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("A");
                    state++;
                    o.notifyAll();
                }

            }
        }

    }

    public static class ThreadB implements Runnable {
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                synchronized (o) {
                    while(state%3!=1){
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("B");
                    state++;
                    o.notifyAll();
                }
            }
        }

    }

    public static class ThreadC implements Runnable {
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                synchronized (o) {
                    while(state%3!=2){
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("C");
                    state++;
                    o.notifyAll();
                }
            }
        }

    }

}
