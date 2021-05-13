package com.tonytaotao.algorithm;

/**
 * 斐波拉契数列求和 （递归）
 */
public class Fibonacci {

	public static void main(String[] args) {
		System.out.println(fibonacci(10));

	}

	/**
	 * 方法一：递归方式，时间复杂度O(N2)
	 * @param n
	 * @return
	 */
	/*public static int fibonacci(int n){
		if(n==1||n==2){
			return 1;
		}else{
			return fibonacci(n-1)+fibonacci(n-2);
		}
	}*/

	/**
	 * 方法二：临时状态，时间复杂度O(N), 空间复杂度O(1)
	 * @param n
	 * @return
	 */
	public static int fibonacci(int n) {

		if (n <= 0) {
			return 0;
		}

		if (n == 1 || n == 2) {
			return 1;
		}

		int pre1 = 1, pre2 = 1;
		for (int i = 3; i <= n; i++) {
			int sum = pre1 + pre2;
			pre1 = pre2;
			pre2 = sum;
		}
		return pre2;
	}
}
