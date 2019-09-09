package com.JUC;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest implements Runnable{
    private static final ReentrantLock lock  = new ReentrantLock();
    private int num =0;
    private int lsh = 0;
    private ThreadLocal<Integer> t = new ThreadLocal<>();

    private void setLsh(int lsh){
        this.lsh = lsh;
    }

    @Override
    public void  run() {
        try {
            t.set(lsh);
            tryLockTest();
        }catch (Exception e){

        }


//        lock.lock();
//        try {
//            try {
//                Thread.sleep(3000);
//            } catch (Exception e) {
//
//            }
//            System.out.println(Thread.currentThread().getName()+":"+ num++);
//            for(;;){
//                System.out.println(Thread.currentThread().getName());
//            }
//        }finally {
//            lock.unlock();
//        }
    }

    public void tryLockTest()throws InterruptedException{

        if(lock.tryLock()){
            try {
                Thread.sleep(1000);
                System.out.println("当前流水号："+t.get());
            }finally {
                lock.unlock();
            }
        }else{
            System.out.println("不能重复提交："+t.get());
        }
    }

    public static void lockTest(){
        ReentrantLockTest t = new ReentrantLockTest();
        for(int i=0;i<10000;i++){

            new Thread(t).start();

        }
    }


    public static void main(String[] args) {
//        ReentrantLockTest t = new ReentrantLockTest();
//        t.setLsh(1);
//
//        ReentrantLockTest t1 = new ReentrantLockTest();
//        t1.setLsh(2);
//        ReentrantLockTest t2 = new ReentrantLockTest();
//        t2.setLsh(1);
//        new Thread(t).start();
//        new Thread(t1).start();
//        new Thread(t2).start();


        for(int i=0;i<20;i++){
            ReentrantLockTest t = new ReentrantLockTest();
            t.setLsh(i);
            new Thread(t).start();
        }
    }

}
