package com.tonytaotao.algorithm.leecode;

/**
 * 字符串转换整数
 * 实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数
 */
public class LC0008 {

    public int myAtoi(String s) {
        int length = s.length();
        char[] charArray = s.toCharArray();

        int index = 0;
        while (index < length && charArray[index] == ' ') {
            index++;
        }

        if (index == length) {
            return 0;
        }

        boolean nagetive = false;
        if (charArray[index] == '-') {
            nagetive = true;
            index++;
        } else if (charArray[index] == '+') {
            index++;
        } else if (!Character.isDigit(charArray[index])) {
            return 0;
        }

        int result = 0;
        while (index < length && Character.isDigit(charArray[index])) {
            int digit = charArray[index] - '0';
            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return nagetive ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return nagetive ? -result : result;
    }

    public static void main(String[] args) {
        String s=  "  -1234wore";
        System.out.println(new LC0008().myAtoi(s));
    }

}
