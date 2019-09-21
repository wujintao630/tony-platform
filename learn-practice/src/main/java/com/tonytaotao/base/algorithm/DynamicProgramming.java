package com.tonytaotao.base.algorithm;

/**
 * 动态规划
 */
public class DynamicProgramming {


    public static void main(String[] args) {
        String strA = "abce";
        String strB = "bcde";

        findLCSeq(strA, strB);

        findLCStr(strA, strB);
    }

    public static void printArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }


    /**
     * 最长公共子序列
     * @param strA
     * @param strB
     */
    public static void findLCSeq(String strA, String strB) {

        int aLength = strA.length();
        int bLength = strB.length();

        char[] charA = strA.toCharArray();
        char[] charB = strB.toCharArray();

        int[][] lcs = new int[aLength + 1][bLength + 1];


        for (int i = 1; i <= aLength; i++) {
            for (int j = 1; j <= bLength; j++) {
                if (charA[i-1] == charB[j-1]) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                } else {
                    lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j-1]);
                }
            }
        }

        printArray(lcs);

        //  反推结果
        StringBuilder stringBuilder = new StringBuilder();
        while (aLength > 0 && bLength > 0) {
            if (charA[aLength - 1] == charB[bLength - 1]) {
                stringBuilder.append(charA[aLength - 1]);
                aLength--;
                bLength--;
            } else {
                if (lcs[aLength][bLength - 1] > lcs[aLength - 1][bLength]) {
                    bLength--;
                } else if (lcs[aLength][bLength - 1] <= lcs[aLength - 1][bLength]) {
                    aLength--;
                }
            }
        }

        System.out.println(stringBuilder.reverse().toString());

    }

    /**
     * 最长公共子串
     * @param strA
     * @param strB
     */
    public static void findLCStr(String strA, String strB) {

        int maxLength = 0;

        int index = 0;

        int aLength = strA.length();
        int bLength = strB.length();
        char[] charA = strA.toCharArray();
        char[] charB = strB.toCharArray();
        int[][] lcs = new int[aLength][bLength];

        for (int i = 0; i < aLength; i++) {
            for (int j = 0; j < bLength; j++) {
                if (i == 0 || j == 0) {
                    if (charA[i] == charB[j]) {
                        lcs[i][j] = 1;
                    }
                    if (lcs[i][j] > maxLength) {
                        maxLength = lcs[i][j];
                        index = i;
                    }
                } else {
                    if (charA[i] == charB[j]) {
                        lcs[i][j] = lcs[i-1][j-1] + 1;
                    }
                    if (lcs[i][j] > maxLength) {
                        maxLength = lcs[i][j];
                        index = i + 1 - maxLength;
                    }
                }
            }
        }

        System.out.println(strA.substring(index, index + maxLength));

    }


}
