package com.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKHandler1 implements InvocationHandler{

	private Object target;

	public JDKHandler1(Object target){
		this.target=target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("start");
		Object result = method.invoke(target, args);
		System.out.println("end");
		return result;
	}
	
	public <T> T getProxy(T tO, Class<T> t){
	    this.target = tO;
        Object ob = Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
		return t.cast(ob);
	}

}
