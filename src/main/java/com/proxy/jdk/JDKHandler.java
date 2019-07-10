package com.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKHandler implements InvocationHandler{
	
	private Object target;
	
	public JDKHandler(Object target){
		this.target=target;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("start");
		Object result = method.invoke(target, args);
		System.out.println("end");
		return result;
	}
	
	public Object getProxy(){
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), this);
	}

}
