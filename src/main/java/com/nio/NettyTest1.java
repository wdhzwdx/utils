package com.nio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class NettyTest1 {
    public static void main(String[] args) throws Exception {
        File file = new File("D://db.txt");
        long length = file.length();
        byte[] bs = new byte[(int)length];
        FileInputStream inputStream = new FileInputStream(file);
        MappedByteBuffer mappedByteBuffer = inputStream.getChannel().map(FileChannel.MapMode.READ_ONLY,
                0,length);
        for (int offset=0;offset<length;offset++) {
            byte b = mappedByteBuffer.get();
            bs[offset] = b;
        }

        Scanner scanner = new Scanner(new ByteArrayInputStream(bs)).useDelimiter(" ");
        while(scanner.hasNext()){
            System.out.println(scanner.next()+" ");
        }

    }
}
