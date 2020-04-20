package com.compress;

import com.alibaba.fastjson.JSON;
import com.compress.dto.CompressDTO;
import com.compress.dto.ResCompressDTO1;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCompress {

    public String charestName = "UTF-8";

    /**
     * 返回接口自定义名称
     * @return
     */
    public abstract String getSourceName();

    /**
     * 压缩，并计算耗时
     * @param str
     * @return
     */
    public String zip(String str){
        long current = System.currentTimeMillis();
        str = compress(str);
        long end = System.currentTimeMillis();
        System.out.println(getSourceName()+"压缩耗时："+(end-current)+"毫秒");
        return str;
    }

    /**
     * 压缩
     * @param s
     * @return
     */
    public abstract String compress(String s);

    /**
     * 解压，并计算耗时
     * @param str
     * @return
     */
    public String unZip(String str){
        long current = System.currentTimeMillis();
        String result = uncompress(str);
        long end = System.currentTimeMillis();
        System.out.println(getSourceName()+"解压耗时："+(end-current)+"毫秒");
        return result;
    }

    /**
     * 解压
     * @param s
     * @return
     */
    public abstract String uncompress(String s);

    /**
     * 测试请求数据压缩解压情况
     * @param count
     */
    public void testRquestData(int count){
        List<CompressDTO> list = new ArrayList<>(count);
        for (int i=0;i<count;i++){
            list.add(CompressDTO.create());
        }
        String ysString = JSON.toJSONString(list);
        System.out.println(getSourceName()+"字符串长度为：" + ysString.length());

        String ysStr = zip(ysString);
//        System.out.println("\n压缩后的字符串为----->" + ysStr);
        float len1 = ysStr.length();
        System.out.println(getSourceName()+"压缩后的字符串长度为----->" + len1);

        String jyStr = unZip(ysStr);
//        System.out.println("\n解压缩后的字符串为--->" + jyStr);
        System.out.println(getSourceName()+"解压缩后的字符串长度为--->" + jyStr.length());

        //判断
        if(jyStr.equals(ysString)){
            System.out.println(getSourceName()+"解压后的字符串是一样的。。。。");
        }else{
            System.out.println(getSourceName()+"解压后的字符串不是一样的。。。。");
        }
    }

    /**
     * 测试返回数据压缩解压情况
     * @param count
     */
    public void testResponseData(int count){
        List<ResCompressDTO1> list = new ArrayList<>(count);
        for (int i=0;i<count;i++){
            list.add(ResCompressDTO1.create());
        }
        String ysString = JSON.toJSONString(list);
        System.out.println(getSourceName()+"字符串长度为：" + ysString.length());
//        try {
//            System.out.println("\nBase64后的字符串为----->" + new BASE64Encoder().encode(ysString.getBytes("utf-8")));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String ysStr = zip(ysString);

        float len1 = ysStr.length();
        System.out.println("压缩后的字符串："+ysStr);
        System.out.println(getSourceName()+"压缩后的字符串长度为----->" + len1);

        String jyStr = unZip(ysStr);
//        System.out.println("\n解压缩后的字符串为--->" + jyStr);
        System.out.println(getSourceName()+"解压缩后的字符串长度为--->" + jyStr.length());

        //判断
        if(jyStr.equals(ysString)){
            System.out.println(getSourceName()+"解压后的字符串是一样的。。。。");
        }else{
            System.out.println(getSourceName()+"解压后的字符串不是一样的。。。。");
        }
    }

}
