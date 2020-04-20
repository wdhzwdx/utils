package com.JUC;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueTest {
    static ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(2);

    static class AbqPutTest extends Thread{
        private String lsh;
        AbqPutTest(String lsh){
            this.lsh = lsh;
        }

        @Override
        public void run() {
            try {
                this.rd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void rd() throws InterruptedException {
            if(!arrayBlockingQueue.contains(lsh)){
                arrayBlockingQueue.put(lsh);
                System.out.println("流水号"+lsh+"入队了");
            }else{
                System.out.println("流水号"+lsh+"已经执行了");
            }
        }
    }

    public static void main(String[] args) {
        new AbqPutTest("1").start();
        new AbqPutTest("1").start();
        new AbqPutTest("3").start();
        new AbqPutTest("4").start();
        new AbqPutTest("5").start();
    }


}
