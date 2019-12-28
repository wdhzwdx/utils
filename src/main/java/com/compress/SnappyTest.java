package com.compress;

import org.xerial.snappy.Snappy;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Snappy（以前称Zippy）是Google基于LZ77的思路用C++语言编写的快速数据压缩与解压程序库，并在2011年开源。
 * 它的目标并非最大压缩率或与其他压缩程序库的兼容性，而是非常高的速度和合理的压缩率。更多wikisnappy
 * <dependency>
 *     <groupId>org.xerial.snappy</groupId>
 *     <artifactId>snappy-java</artifactId>
 *     <version>1.1.7.1</version>
 * </dependency>
 */
public class SnappyTest extends AbstractCompress{

    @Override
    public String getSourceName() {
        return "snappy";
    }

    @Override
    public String compress(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }

        byte[] out = null;
        try {
            out = Snappy.compress(primStr.getBytes(charestName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BASE64Encoder().encode(out);
    }

    @Override
    public String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        byte[] out = null;
        String result = "";
        try {
            byte[] input = new BASE64Decoder().decodeBuffer(compressedStr);
            out = Snappy.uncompress(input);
            result = new String(out,charestName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        SnappyTest test = new SnappyTest();
//        test.testRquestData(100);

        /**
         * snappy字符串长度为：29001
         * 压缩耗时：762
         * snappy压缩耗时：764
         * snappy压缩后的字符串长度为----->2196.0
         * 解压缩耗时：1
         * snappy解压耗时：2
         * snappy解压缩后的字符串长度为--->29001
         * snappy解压后的字符串是一样的。。。。
         */
        test.testResponseData(100);
//        for(int i=0;i<10000;i++){
//            test.testResponseData(100);
//        }
    }


}
