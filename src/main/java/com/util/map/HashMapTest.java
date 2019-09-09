package com.util.map;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

	/**
	 * 根据已知数量出计算hashmap初始化容量大小
	 * @param a
	 * @return
	 */
	public static int getNum(int a){
		int n = a -1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		int result = n+1;
		BigDecimal data = new BigDecimal(a).divide(new BigDecimal("0.75"),0,BigDecimal.ROUND_HALF_UP);
		int flag = data.intValue();
		if(flag>result){
			return result<<1;
		}else{
			return result;
		}
	}

    /**
     * 原生的
     * @param a
     * @return
     */
	public static int getNum1(int a){
        int n = a - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    /**
     *
     * @param a
     * @return
     */
    public static int getNum2(int a){
	    a = a+a/3;
        return getNum1(a);
    }
	
	public static void main(String args[]){
		Map<String,String> aMap=  Maps.newHashMapWithExpectedSize(4);
		Map<String,String> bMap = new HashMap<String, String>(getNum(4));

        System.out.println(getNum(97));//128 -128
        System.out.println(getNum1(97));
        System.out.println(getNum2(97));
		System.out.println(getNum(96));//128 -128
        System.out.println(getNum1(96));
        System.out.println(getNum2(96));
		System.out.println(getNum(100));//134 -256
        System.out.println(getNum1(100));
        System.out.println(getNum1(getNum2(134)));

        System.out.println(getNum(127));
	}
	
}
