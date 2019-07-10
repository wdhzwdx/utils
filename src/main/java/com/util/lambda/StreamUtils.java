package com.util.lambda;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * lamda表达式简单应用
 * @author wdh
 *
 */
public class StreamUtils {
	
	public static void main(String args[]){
//		listToMap();
		filter();
//		groupingBy();
//		sum();
//		findOne();
		System.exit(0);
	}

	/**
	 * List<T> -> Map
	 * 需要注意的是：
	 * toMap 如果集合对象有重复的key，会报错Duplicate key ....
	 *  可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
	 */
	public static void listToMap() {
		List<AObject> aList = new ArrayList<>();
		AObject aObject = new AObject();
		aObject.setA("a");
		aObject.setB("B");
		AObject aObject1 = new AObject();
		aObject1.setA("a1");
		aObject1.setB("B1");
		aList.add(aObject);
		aList.add(aObject1);
		
		Map<String, AObject> map = aList.stream().
				collect(Collectors.toMap(AObject::getA, AObject->AObject,(k1,k2)->k1));
		for(Map.Entry<String, AObject> entry : map.entrySet()){
			System.out.println("key:"+entry.getKey()+"  value:"+entry.getValue().toString());
		}
//		key:a1  value:a1 B1
//		key:a  value:a B
		
		Map<String, String> map1 = aList.stream().
				collect(Collectors.toMap(AObject::getA, AObject::getB,(k1,k2)->k1));
		for(Map.Entry<String, String> entry : map1.entrySet()){
			System.out.println("key:"+entry.getKey()+"  value:"+entry.getValue());
		}
//		key:a1  value:B1
//		key:a  value:B
		
	}
	
	/**
	 * 过滤
	 */
	public static void filter() {
		List<String> aList = new ArrayList<>();
		aList.add("1");
		aList.add("a");
		aList.add("a");
		aList.add("a1");
		List<String> slist =aList.stream().filter(e -> "a".equals(e)).collect(Collectors.toList());
		System.out.println(slist.toString());//		[a]
		
		List<AObject> list = new ArrayList<>();
		AObject aObject = new AObject();
		aObject.setA("110000");
		aObject.setB("北京");
		AObject aObject2 = new AObject();
		aObject2.setA("330000");
		aObject2.setB("浙江");
		list.add(aObject);
		list.add(aObject2);
		List<AObject> newList =list.stream().filter(e -> e.getA().equals("110000")).collect(Collectors.toList());
	
		System.out.println(newList.toString());//		[110000 北京]
		
		List<String> as = list.stream().map(AObject::getA).collect(Collectors.toList());
		System.out.println(as.toString());
	}
	
	/**
	 * 分组
	 */
	public static void groupingBy(){
		List<AObject> list = new ArrayList<>();
		AObject aObject = new AObject();
		aObject.setA("110000");
		aObject.setB("北京");
		AObject aObject1 = new AObject();
		aObject1.setA("110000");
		aObject1.setB("北京1");
		AObject aObject2 = new AObject();
		aObject2.setA("330000");
		aObject2.setB("浙江");
		AObject aObject3 = new AObject();
		aObject3.setA("330000");
		aObject3.setB("浙江1");
		list.add(aObject);
		list.add(aObject1);
		list.add(aObject2);
		list.add(aObject3);
		//groupingBy  只传key 默认把整个集合关联，  也可以把集合某个数据属性 当做集合取出来，如下groupBy1
		Map<String, List<AObject>> groupBy = list.stream().collect(Collectors.groupingBy(AObject::getA));
		for(Map.Entry<String, List<AObject>> item : groupBy.entrySet()){
			System.out.print(item.getKey()+" : ");
			for(AObject a : item.getValue()){
				System.out.print(a.toString()+";");
			}
			System.out.println();
		}
		//110000 : 110000 北京;110000 北京1;
		//330000 : 330000 浙江;330000 浙江1;
		
		Map<String, List<String>> groupBy1 = list.stream().collect(
				Collectors.groupingBy(AObject::getA, Collectors.mapping(AObject::getB, Collectors.toList())));
		for(Map.Entry<String, List<String>> item : groupBy1.entrySet()){
			System.out.print(item.getKey()+" : ");
			for(String a : item.getValue()){
				System.out.print(a+";");
			}
			System.out.println();
		}
//		110000 : 北京;北京1;
//		330000 : 浙江;浙江1;
	}
	
	/**
	 * 求和
	 */
	public static void sum(){
		List<AObject> list = new ArrayList<>();
		AObject aObject = new AObject();
		aObject.setMoney(new BigDecimal("100"));
		aObject.setNum(100);
		AObject aObject1 = new AObject();
		aObject1.setMoney(new BigDecimal("200"));
		aObject1.setNum(200);
		AObject aObject2 = new AObject();
		aObject2.setMoney(new BigDecimal("300"));
		aObject2.setNum(300);
		AObject aObject3 = new AObject();
		aObject3.setMoney(new BigDecimal("400"));
		aObject3.setNum(400);
		list.add(aObject);
		list.add(aObject1);
		list.add(aObject2);
		list.add(aObject3);
		//注意的是  求和的属性 值不能为null
		BigDecimal totalMoney = list.stream().map(AObject::getMoney).
				reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println("totalMoney:"+totalMoney); // totalMoney:1000
		Integer totalMoney1 = list.stream().map(AObject::getNum).
				reduce(0, Integer::sum);
		System.out.println("totalSum:"+totalMoney1); // totalSum:1000
		int sum = list.stream().mapToInt(AObject::getNum).sum();
		System.out.println("sum:"+sum);// sum:1000
	}
	
	public static void findOne(){
		List<String> aList = new ArrayList<>();
		aList.add("1");
		aList.add("a");
		aList.add("a");
		aList.add("a1");
		
		Optional<String> first = aList.stream().filter(e -> e.equals("a")).findFirst();
		//如果没有找到调用get方法会抛异常
		System.out.println(first.get());
	}
	
	
}
