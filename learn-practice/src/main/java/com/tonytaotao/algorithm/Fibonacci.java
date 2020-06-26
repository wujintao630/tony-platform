package com.tonytaotao.algorithm;

/**
 * 斐波拉契数列求和 （递归）
 */
public class Fibonacci {

	public static void main(String[] args) {
		System.out.println(fibonacci(10));

	}
	
	public static int fibonacci(int n){
		if(n==1||n==2){
			return 1;
		}else{
			return fibonacci(n-1)+fibonacci(n-2);
		}
	}
}
