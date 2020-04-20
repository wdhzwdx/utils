package com.compress;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * DEFLATE是同时使用了LZ77算法与哈夫曼编码（Huffman Coding）的一个无损数据压缩算法，DEFLATE压缩与解压的源代码可以在自由、
 * 通用的压缩库zlib上找到，zlib官网：http://www.zlib.net/
 *
 * jdk中对zlib压缩库提供了支持，压缩类Deflater和解压类Inflater，Deflater和Inflater都提供了native方法
 *
 * 
 */
public class DeflateTest extends AbstractCompress{


    @Override
    public String getSourceName() {
        return "deflate";
    }

    /**
     * 加密
     * @param info
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public String compress(String info) {
        byte[] input = info.getBytes();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //可选的级别有0（不压缩），以及1(快速压缩)到9（慢速压缩）。
        Deflater compressor = new Deflater(9);
        try {
            compressor.setInput(input);
            compressor.finish();
            final byte[] buf = new byte[2048];
            while(!compressor.finished()){
                int count = compressor.deflate(buf);
                bos.write(buf, 0, count);
            }
        }finally {
           compressor.end();
        }
        return new BASE64Encoder().encode(bos.toByteArray());
    }

    @Override
    public String uncompress(String encodeString) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Inflater decompressor = new Inflater();
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(encodeString);
            decompressor.setInput(bytes);
            final byte[] buf = new byte[2048];
            while (!decompressor.finished()) {
                int count = 0;
                try {
                    count = decompressor.inflate(buf);
                } catch (DataFormatException e) {
                    e.printStackTrace();
                }
                bos.write(buf, 0, count);
            }
        }catch (Exception e){
            
        }finally{
            decompressor.end();
        }
        return bos.toString();
    }

    public static void main(String[] args) {
        DeflateTest test = new DeflateTest();
//        test.testRquestData(100);
        /**
         * deflate字符串长度为：29001
         * 压缩耗时：1
         * deflate压缩耗时：2
         * deflate压缩后的字符串长度为----->480.0
         * 解压耗时：1
         * deflate解压耗时：2
         * deflate解压缩后的字符串长度为--->29001
         * deflate解压后的字符串是一样的。。。。
         */
        test.testResponseData(100);
        String d = "{}";
        JSONObject j = JSON.parseObject(d);

        System.out.println(j.isEmpty());
//        for(int i=0;i<10000;i++){
//            test.testResponseData(100);
//        }
    }
}
