package com.JUC;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    private static final Semaphore lock = new Semaphore(1);

    static class ThreadTest implements Runnable{
        @Override
        public void run() {
            try {
                if(!lock.tryAcquire()){
                    System.out.println("已满");
                }
                Thread.sleep(3000);
            }catch (Exception e){

            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<4;i++){
            new Thread(new ThreadTest()).start();
        }
    }

}
