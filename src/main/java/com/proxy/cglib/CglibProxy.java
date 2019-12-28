package com.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor{

	private Object target;
	
	public void setCglibProxy(Object target){
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

	@Override
	public Object intercept(Object arg0, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("start");
        String t = (String)args[0];
        if ("2".equals(t)){
            return "无法操作";
        }
        args[1] = "拦截返回数据赋值";
		Object result = method.invoke(target, args);
		System.out.println("end");

		return result;
	}

}
