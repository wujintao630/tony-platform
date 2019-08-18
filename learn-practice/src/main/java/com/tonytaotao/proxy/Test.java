package com.tonytaotao.proxy;

public class Test {

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
