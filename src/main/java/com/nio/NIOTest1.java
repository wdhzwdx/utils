package com.nio;


import org.apache.tomcat.util.http.fileupload.util.Streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NIOTest1 {
    private static ExecutorService threadPool = null;
    private static int threadNum = 3;
    private static CountDownLatch lock = new CountDownLatch(threadNum);
    static {
        threadPool = new ThreadPoolExecutor(3,10,0, TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(10));
    }
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        getTime(start);

//        test1();
        test2();
    }

    public static void getTime(long start){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long end = System.currentTimeMillis();
                System.out.println("耗时:"+(end -start));
                threadPool.shutdown();
            }
        }).start();
    }

    public static void test1() throws Exception {
        Random r = new Random();
        long start = System.currentTimeMillis();
        for(int i=0 ;i<threadNum;i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File file = new File("D://软件包//powerdesigner 16.5.rar");
                        FileInputStream inputStream = new FileInputStream(file);
                        FileOutputStream outputStream = new FileOutputStream("D:\\test\\test"+r.nextInt(100000)+".rar");
                        Streams.copy(inputStream,outputStream,true);
                        lock.countDown();
                    } catch (Exception e){
                        System.out.println("复制文件失败");
                    }
                }
            });
            threadPool.execute(thread);
        }
    }

    public static void test2() throws Exception {
        File file = new File("D://软件包//powerdesigner 16.5.rar");

        long length = file.length();
        byte[] bs = new byte[(int)length];

        FileInputStream inputStream = new FileInputStream(file);
        FileChannel fileChannel = inputStream.getChannel();
        Random r = new Random();

        for(int i=0;i<threadNum;i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        FileOutputStream outputStream = new FileOutputStream("D:\\test1\\test_"+r.nextInt(100000)+".rar");
                        fileChannel.transferTo(0,length, Channels.newChannel(outputStream));
                        outputStream.close();
                        lock.countDown();
                    } catch (Exception e){
                        System.out.println("fileChannel复制文件失败");
                    }
                }
            });
            threadPool.execute(thread);
        }
     
//        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY,
//                0,length);
//        for (int offset=0;offset<length;offset++) {
//            byte b = mappedByteBuffer.get();
//            bs[offset] = b;
//        }
//
//        Scanner scanner = new Scanner(new ByteArrayInputStream(bs)).useDelimiter("");
//        while(scanner.hasNext()){
//            System.out.print(scanner.next());
//        }
    }


}
