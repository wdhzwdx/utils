package com.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy2 implements MethodInterceptor{

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

    /**
     *
     * @param proxy 代理对象
     * @param method 方法
     * @param args 方法参数
     * @param methodProxy 方法代理
     * @return
     * @throws Throwable
     */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
	    try {
            String t = (String)args[0];
            args[1] = "拦截返回数据赋值";

            /**
             * methodProxy
             *  invoke方法一定要使用被代理的对象也就是上文中的target
             *  调用invokeSuper方法，则一定要使用被代理后的proxy对象。
             */
//            Object result = methodProxy.invoke(proxy, args); //不能用这个，会死循环
              Object result = methodProxy.invoke(target.get(), args); //FastClass机制，比JDK动态代理通过反射强
//            Object result = methodProxy.invokeSuper(proxy, args);//会拦截代理类的所有方法，慎用

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
