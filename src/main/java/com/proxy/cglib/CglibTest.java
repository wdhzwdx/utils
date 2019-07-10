package com.proxy.cglib;


public class CglibTest {

	public static void main(String[] args) {
		CglibProxy proxy = new CglibProxy(new CgDao());
		CgDao cgDao = (CgDao)proxy.getProxy();
		cgDao.toInfo();
	}
}
