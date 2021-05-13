package com.tonytaotao.algorithm.leecode;

/**
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 示例 1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 *
 * 示例 2：
 * 输入：s = "cbbd"
 * 输出："bb"
 *
 * 示例 3：
 * 输入：s = "a"
 * 输出："a"
 *
 * 示例 4：
 * 输入：s = "ac"
 * 输出："a"
 *
 * 我们用一个 boolean dp[l][r] 表示字符串从 i 到 j 这段是否为回文。试想如果 dp[l][r]=true，我们要判断 dp[l-1][r+1] 是否为回文。只需要判断字符串在(l-1)和（r+1)两个位置是否为相同的字符
 * 初始状态，l=r 时，此时 dp[l][r]=true。
 * 状态转移方程，dp[l][r]=true 并且(l-1)和（r+1)两个位置为相同的字符，此时 dp[l-1][r+1]=true
 */
public class LC0005 {

    public String longestPalindrome(String s) {

        if (s == null || s.length() < 2) {
            return s;
        }

        int length = s.length();
        int maxStart = 0;
        int maxEnd = 0;
        int maxLength = 1;

        boolean[][] dp = new boolean[length][length];
        for (int right = 1; right < length; right++) {
            for (int left = 0; left < right; left++) {
                if (s.charAt(left) == s.charAt(right) && (right - left <= 2 || dp[left + 1][right - 1] == true)) {
                    dp[left][right] = true;
                    if (right - left + 1 > maxLength) {
                        maxLength = right - left + 1;
                        maxStart = left;
                        maxEnd = right;
                    }
                }
            }
        }

        return s.substring(maxStart, maxEnd + 1);

    }

    public static void main(String[] args) {
        String s = "babad";
        System.out.println(new LC0005().longestPalindrome(s));
    }

}
