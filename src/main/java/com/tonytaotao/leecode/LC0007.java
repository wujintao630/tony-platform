package com.tonytaotao.leecode;

/**
 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
 * 如果反转后整数超过 32 位的有符号整数的范围 [-2147483648,  2147483647] ，就返回 0。
 *
 */
public class LC0007 {

    public int reverse(int x) {

        int result = 0;

        while (x != 0) {

            // 当前数字的最后一位
            int pop = x % 10;

            // 已经反转的数值拼接后一位后，超过最大数
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }

            // 已经反转的数值拼接后一位后，小于最小数
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }

            result = result * 10 + pop;

            // 剩余待反转数
            x = x / 10;
        }

        return result;
    }

    public static void main(String[] args) {
        int x = -521;
        System.out.println(new LC0007().reverse(x));
    }



}
