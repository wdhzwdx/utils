package com.proxy.jdk;

public class JDKTest {

	public static void main(String[] args) {
		JDKService service =  new JDKServiceImpl();
		JDKHandler handler = new JDKHandler(service);
        service = (JDKService)handler.getProxy();
		service.toInfo();


	}
}
