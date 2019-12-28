package com.JUC;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier;

    static class CyclicBarrierThread extends Thread{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "点击确认");
            //等待
            try {
                Thread.sleep(1000);
                cyclicBarrier.await(1, TimeUnit.MILLISECONDS);
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + "可以进入战场，选择英雄了");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("都确认了，开局....");
            }
        });

        for(int i = 0 ; i < 5 ; i++){
            new CyclicBarrierThread().start();
        }
    }
}
