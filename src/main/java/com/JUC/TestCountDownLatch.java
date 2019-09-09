package com.JUC;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch：闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 */
public class TestCountDownLatch {

    public static void main(String[] agrs) {
        // 表示有5个线程要等待
        CountDownLatch latch = new CountDownLatch(5);
        LatchDemo ld = new LatchDemo(latch);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(ld).start();
        }

        try {
            latch.await(); // 等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("耗费时间为：" + (end - start) + "ms");
    }

}

class LatchDemo implements Runnable {
    private CountDownLatch latch;

    public LatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
//                    System.out.println(Thread.currentThread().getName()+":"+i);
                    System.out.print(i+"|");
                    System.out.println(Thread.currentThread().getName()+":"+i);
                }
            }
        } finally { // 必须执行的操作
            latch.countDown();
        }
    }
}
