package com.proxy.cglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor{

	private Object target;
	
	public CglibProxy(Object target){
		this.target = target;
	}
	
	public Object getProxy(){
		//1.工具类
		Enhancer enhancer = new Enhancer();
		//2.设置父类
		enhancer.setSuperclass(target.getClass());
		//3.设置回调函数
		enhancer.setCallback(this);
		
		return enhancer.create();
	}
	
	public Object intercept(Object arg0, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("start");
		Object result = method.invoke(target, args);
		System.out.println("end");
		return result;
	}

}
