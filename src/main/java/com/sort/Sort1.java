package com.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sort1 {
    private static final int maxNum = 5000000;

    public static void test1(){
        long s = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<maxNum;i++){
            list.add((int)(1+Math.random()*maxNum));
        }
        list.sort((a,b)->b.compareTo(a));
        long stime = System.currentTimeMillis();
        System.out.println("耗时"+(stime-s));
    }

    public static void test2(){
        long s = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<maxNum;i++){
//            list.add((int)(1+Math.random()*maxNum));

        }
        list= list.stream().sorted((a,b)->b.compareTo(a)).collect(Collectors.toList());
        long stime = System.currentTimeMillis();
        System.out.println("耗时"+(stime-s));
    }

    public static void test3(){
        long s = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<maxNum;i++){
            list.add((int)(1+Math.random()*maxNum));
        }
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        long stime = System.currentTimeMillis();
        System.out.println("耗时"+(stime-s));
    }

    public static void test4(){
        long s = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<maxNum;i++){
            list.add((int)(1+Math.random()*maxNum));
        }
        list = list.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList());
        long stime = System.currentTimeMillis();
//        System.out.println("数据："+list.toString());
        System.out.println("耗时"+(stime-s));
    }

    public static void main(String[] args) {

//        test1();
        test2();
//        test3();
//        test4();
        
    }


}
