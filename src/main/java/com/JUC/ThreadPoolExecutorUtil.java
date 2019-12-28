package com.JUC;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author wdh
 */
public class ThreadPoolExecutorUtil {
    private static ExecutorService executorService;

    public static void init(){
        executorService =  new ThreadPoolExecutor(
                1,
                3,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5)
        );
    }

    public static void init1(){
        executorService =  new ThreadPoolExecutor(
                1,
                3,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

    public static void init2(){
        executorService =  new ThreadPoolExecutor(
                1,
                3,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(5),
                new ThreadFactoryBuilder().setNameFormat("pool-%d").build()

        );
    }

    public static void init3(){
        executorService = new ThreadPoolExecutor(
                1,
                1,
                0L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                new MyRejectedExecutionHandler3());
    }

    static class MyRejectedExecutionHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //DiscardOldestPolicy模式
            if(!executor.isShutdown()){
                //丢弃最前面节点，即head
                executor.getQueue().poll();
                executor.execute(r);
                System.out.println("决绝策略：丢弃最靠前的任务，并把新任务加入到线程池中执行");
            }
        }
    }

    static class MyRejectedExecutionHandler1 implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if(!executor.isShutdown()){
                try {
                    System.out.println("拒绝策略1：把任务放入阻塞队列中，由于阻塞队列已满，线程阻塞中，等待入队");
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }

    static  class MyRejectedExecutionHandler2 implements RejectedExecutionHandler{
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //什么也不做，即抛弃任务DiscardPolicy模式
            System.out.println("拒绝策略2：即DiscardPolicy，丢弃任务");
        }
    }

    static class MyRejectedExecutionHandler3 implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            r.run();
            System.out.println("拒绝策略3：我自己跑");
        }
    }

    static class MyRejectedExecutionHandler4 implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("拒绝策略4：丢弃任务");
        }
    }

    static class MyRejectedExecutionHandler5 implements  RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("拒绝策略5：");
        }
    }

    static class MyRejectedExectionHandler6 implements  RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("拒绝策略6");
        }
    }

    static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("异常："+e.getMessage());
        }
    }

    static class MyUncaughExceptionHandler2 implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("无法捕获的异常");
        }
    }

    static class Test implements Runnable{
        Test setUncatchException(){
            Thread.currentThread().setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
            return this;
        }

        @Override
        public void run() {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            setUncatchException();
            System.out.println(1/0);
            System.out.println("我在执行");
        }
    }

    static void myShutdown(ExecutorService executorService){
        executorService.shutdown();
        //防止任务还没执行完就返回了，造成不一致表现
        while (!executorService.isTerminated()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("线程池关闭了");
    }

    public static void main(String[] args) {
        init3();
//        try {
            for(int i=0;i<5;i++){
                executorService.execute(new Test());
            }
//        }  finally {
//            myShutdown(executorService);
//        }
    }

}
