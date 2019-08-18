package com.tonytaotao.proxy;

import java.lang.reflect.Method;

public class MyProxyHander implements MyInvocationHandler{
	
	People people=null;
	
	public MyProxyHander(People people) {
		this.people=people;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object args)
			throws Throwable {
		beforeEat();
		method.invoke(people, null);
		afterEat();
		return null;
	}
	
	public void beforeEat(){
		System.out.println("吃饭之前要洗手");
	}
	
	public void afterEat(){
		System.out.println("吃完后要洗碗");
	}
}
