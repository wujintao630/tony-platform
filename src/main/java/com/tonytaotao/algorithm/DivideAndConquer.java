package com.tonytaotao.algorithm;

/**
 * 分治（汉诺塔）
 */
public class DivideAndConquer {

	public static void main(String[] args) {
		new DivideAndConquer().move(3, "A", "B", "C");
	}

	public void move(int n, String A, String B, String C) {
		if (n == 1) {
			System.out.println("盘" + n + "从" + A + "移到" + C);
		} else {
			move(n - 1, A, C, B);
			System.out.println("盘" + n + "从" + A + "移到" + C);
			move(n - 1, B, A, C);
		}
	}
}
