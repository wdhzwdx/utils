package com.proxy.cglib;

public class CgDao implements CgI{
    @Override
	public String toInfo(String t, String j){
		System.out.println("cglib");
        System.out.println(j);

        tt();
        return t;
	}

	public void tt(){
        System.out.println("a");
    }
}
