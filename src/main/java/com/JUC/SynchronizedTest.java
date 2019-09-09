package com.JUC;

public class SynchronizedTest implements Runnable{
    private int num =0;

    public synchronized void test(){
        try {
                num ++;
                System.out.print(num+",");
//                Thread.sleep(2000);
            } catch (Exception e) {

            }
    }

    @Override
    public void run() {
        test();
    }


    public static void main(String[] args) {
        SynchronizedTest t = new SynchronizedTest();
        for(int i=0;i<10;i++){
            new Thread(t).start();
        }
    }

}
