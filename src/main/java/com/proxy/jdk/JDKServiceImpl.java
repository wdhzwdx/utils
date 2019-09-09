package com.proxy.jdk;

public class JDKServiceImpl implements JDKService{
    @Override
	public String toInfo() {
		System.out.println("JDK");
		return "JDK";
	}
	
	@Override
	public String toInfo1() {
		System.out.println("JDK1");
		return "JDK1";
	}

}
