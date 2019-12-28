package com.proxy.cglib;

public class CgDao1 implements CgI1{
    @Override
	public String toInfo(String t, String j){
		System.out.println("cglib1");
        System.out.println(j);

        tt();
        return t;
	}

	public void tt(){
        System.out.println("a");
    }
}
