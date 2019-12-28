package com.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy1 implements MethodInterceptor{

	private ThreadLocal<Object> target = new ThreadLocal<>();
	
	public <T> T getProxy(T targetObject ,Class<T> t){
        target.set(targetObject);
		//1.工具类
		Enhancer enhancer = new Enhancer();
		//2.设置父类
		enhancer.setSuperclass(target.get().getClass());
		//3.设置回调函数
		enhancer.setCallback(this);
        Object object = enhancer.create();
		return t.cast(object);
	}

	@Override
	public Object intercept(Object arg0, Method method, Object[] args, MethodProxy proxy) throws Throwable {
	    try {
            String t = (String)args[0];
            if ("2".equals(t)){
                return "无法操作";
            }
            args[1] = "拦截返回数据赋值";
            Object result = method.invoke(target.get(), args);
            return result;
        } catch (Exception e){
            System.out.println("发生异常");
            throw e;
        } finally {
            System.out.println("最后");
            target.remove();
        }

	}

}
