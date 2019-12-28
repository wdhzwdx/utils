package com.compress;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * bzip2是Julian Seward开发并按照自由软件／开源软件协议发布的数据压缩算法及程序。Seward在1996年7月第一次公开发布了bzip2 0.15版，在随后几年中这个压缩工具稳定性得到改善并且日渐流行，Seward在2000年晚些时候发布了1.0版。更多wikibzip2
 *
 * bzip2比传统的gzip的压缩效率更高，但是它的压缩速度较慢。
 *
 * jdk中没有对bzip2实现，但是在commons-compress中进行了实现，maven引入：
 *          <dependency>
 *             <groupId>org.apache.commons</groupId>
 *             <artifactId>commons-compress</artifactId>
 *             <version>1.18</version>
 *         </dependency>
 */
public class Bzip2Test extends AbstractCompress{
    
    @Override
    public String getSourceName() {
        return "bzip2";
    }

    @Override
    public String compress(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        
        byte[] out = null;
        ByteArrayOutputStream outputStream = null;
        BZip2CompressorOutputStream bcos = null;
        String result = "";
        try {
            out = primStr.getBytes(charestName);
            outputStream = new ByteArrayOutputStream();
            bcos = new BZip2CompressorOutputStream(outputStream);
            bcos.write(out);
            bcos.finish();
            bcos.flush();
            result = new BASE64Encoder().encode(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println("流关闭异常");
                }
            }
            if (bcos!=null) {
                try {
                    bcos.close();
                } catch (IOException e) {
                    System.out.println("流关闭异常");
                }
            }
        }
        return result;
    }

    @Override
    public String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        String result = "";
        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        BZip2CompressorInputStream bzip2 = null;
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(compressedStr);
            inputStream = new ByteArrayInputStream(bytes);
            bzip2 = new BZip2CompressorInputStream(inputStream);

            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int n;
            while ((n = bzip2.read(buffer)) >= 0) {
                outputStream.write(buffer, 0, n);
            }
            result = new String(outputStream.toByteArray(),charestName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static void main(String[] args) {
        Bzip2Test test = new Bzip2Test();
//         test.testRquestData(100);
        /**
         * bzip2字符串长度为：29001
         * bzip2压缩耗时：145
         * bzip2压缩后的字符串长度为----->606.0
         * bzip2解压耗时：21
         * bzip2解压缩后的字符串长度为--->29001
         * bzip2解压后的字符串是一样的。。。。
         */
        test.testResponseData(100); //100次 压缩后606长度，压缩时间156ms,解压15ms
    }


}
