package com.compress;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipTest extends AbstractCompress{

    @Override
    public String getSourceName() {
        return "gzip";
    }

    @Override
    public String compress(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip=null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(gzip!=null){
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new BASE64Encoder().encode(out.toByteArray());
    }

    @Override
    public String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = new BASE64Decoder().decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);

            byte[] buffer = new byte[2048];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        return decompressed;
    }
    
    public static void main(String[] args) {
        GzipTest test = new GzipTest();
//        test.testRquestData(100);
        /**
         * gzip字符串长度为：29001
         * 压缩耗时：2
         * gzip压缩耗时：3
         * gzip压缩后的字符串长度为----->496.0
         * 解压耗时：2
         * gzip解压耗时：2
         * gzip解压缩后的字符串长度为--->29001
         * gzip解压后的字符串是一样的。。。。
         */
        test.testResponseData(100);
//        for(int i=0;i<10000;i++){
//            test.testResponseData(100);
//        }
    }
    
}
