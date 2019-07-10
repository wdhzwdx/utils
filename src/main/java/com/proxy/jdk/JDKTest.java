package com.proxy.jdk;

public class JDKTest {

	public static void main(String[] args) {
		JDKService service =  new JDKServiceImpl();
		JDKHandler handler = new JDKHandler(service);
		JDKService service1 = (JDKService)handler.getProxy();
		service1.toInfo();
		service1.toInfo1();
	}
}
