package com.tonytaotao.algorithm.leecode;

/**
 *给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 * 示例 2:
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *
 * 示例 3:
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 */
public class LC0003 {

    public int lengthOfLongestSubstring(String s) {

        // 最终无重复子串长度
        int result = 0;

        // 字符串总长度
        int maxLength = s.length();

        int maxIndex = maxLength - 1;

        // 当前无重复子串起始下标
        int beginIndex = 0;

        // 当前无重复子串长度
        int length = 1;

        // 重复字符在当前子串中的相对位置
        int repeatCharlocation;

        while (beginIndex < maxLength) {

            while (true) {

                // 要匹配的下一个字符下标
                int nextCharIndex = beginIndex + length;

                // 判断匹配是否已经到达原字符串末位
                if (nextCharIndex > maxIndex) {

                    return length > result ? length : result;
                }

                // 判断下一个字符是否在子串中，并得到其在子串中的相对位置
                repeatCharlocation = s.substring(beginIndex, nextCharIndex).indexOf(s.charAt(nextCharIndex));

                if (repeatCharlocation < 0) {
                    // 下一个字符不在子串中，则子串继续变长
                    length++;

                } else {
                    // 先记下当前最长的子串长度
                    if (length > result) {
                        result = length;
                    }

                    //将begin移到location的下一个位置
                    // 将length归置为当前begin位置之后，已知的最长无重复字符子串的长度
                    beginIndex = beginIndex + repeatCharlocation + 1;
                    length = length - repeatCharlocation;
                    break;
                }
            }
        }

        return result;

    }

    public static void main(String[] args) {

        String s = "pwwkew";

        System.out.println(new LC0003().lengthOfLongestSubstring(s));
    }


}
