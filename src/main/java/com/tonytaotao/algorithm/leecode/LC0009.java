package com.tonytaotao.algorithm.leecode;

/**
 * 回文数
 * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
 * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。例如，121 是回文，而 123 不是
 */
public class LC0009 {

    public boolean isPalindrome(int x) {

        int ordinal = x;

        if (x <0) {
            return false;
        }

        int result = 0;
        while (x != 0) {
            int pop = x % 10;
            x = x / 10;

            result = result * 10 + pop;
        }

        return ordinal == result ? true : false;

    }

    public static void main(String[] args) {
        int n = 12421;
        System.out.println(new LC0009().isPalindrome(n));
    }

}
