package com.tonytaotao.algorithm;

/**
 * 汉诺塔
 */
public class Hanoi {

	public static void main(String[] args) {
		new Hanoi().move(3, "A", "B", "C");
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
