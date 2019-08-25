package com.tonytaotao.proxy;

import java.lang.reflect.Method;

public class ProxyTest {

	public static void main(String[] args) {
		People people=(People) MyProxy.createProxyInstance(People.class.getClassLoader(), People.class, new MyProxyHander(new XiaoMing()));
		try {
			people.eat();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class XiaoMing implements People {

	@Override
	public void eat() {
		System.out.println("这菜好吃");
	}

}

class MyProxyHander implements MyInvocationHandler{

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
