package com.JUC;

public class ReentrantLockNo implements Runnable{
    private int num =0;

    @Override
    public void run() {
//            try {
//                Thread.sleep(3000);
//            } catch (Exception e) {
//
//            }
            System.out.println(Thread.currentThread().getName()+":"+ num++);

    }


    public static void main(String[] args) {
        ReentrantLockNo t = new ReentrantLockNo();
        for(int i=0;i<20;i++){
            new Thread(t).start();
        }
    }

}
