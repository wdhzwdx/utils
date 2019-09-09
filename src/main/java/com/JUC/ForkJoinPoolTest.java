package com.JUC;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinPoolTest {

    private static ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    static class SumTask extends RecursiveTask<Long>{
        private long[] numbers;
        private int start;
        private int end;

        SumTask(long[] numbers,int start,int end){
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }
        @Override
        protected Long compute() {
            Long total = 0L;
            if(end-start<=100){
                for(int i=start ;i<=end;i++){
                    total += numbers[i];
                }
                return total;
            }else {
                int mid = (end+start)/2;
                SumTask left = new SumTask(numbers,start,mid);
                SumTask right = new SumTask(numbers,mid+1,end);
                left.fork();
                right.fork();
                return left.join()+right.join();
            }
        }
    }

    public static void main(String[] args) {
        int start = 1;
        int end = 1000000;
        long total = 0L;
        long[] numbers = LongStream.rangeClosed(start, end).toArray();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long stime = System.currentTimeMillis();

                long etime = System.currentTimeMillis();
            }
        });
        for(int i = 0;i<10;i++){
            new Thread(new Runnable() {
                long total = 0L;
                @Override
                public void run() {
                    long stime = System.currentTimeMillis();
                    for(long item : numbers){
                        total+=item;
                    }
                    long etime = System.currentTimeMillis();
                    System.out.println("原始"+total+" 耗时："+(etime-stime));
                }
            }).start();
        }

        for(int i = 0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long stime1 = System.currentTimeMillis();
                    Long result = forkJoinPool.invoke(new SumTask(numbers,0,numbers.length-1));
                    long etime1 = System.currentTimeMillis();
                    System.out.println("任务"+result+" 耗时："+(etime1-stime1));
                }
            }).start();
        }


//        ForkJoinTask<Long> r =  forkJoinPool.submit(new SumTask(numbers,0,numbers.length-1));
//        try {
//            System.out.println(r.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        forkJoinPool.shutdown();
    }
}
