package com.util.map;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

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
	
	public static void main(String args[]){
		Map<String,String> aMap=  Maps.newHashMapWithExpectedSize(4);
		Map<String,String> bMap = new HashMap<String, String>(getNum(4));
		
		System.out.println(getNum(96));//128 -128
		System.out.println(getNum(100));//134 -256
	}
	
}
