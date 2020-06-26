package com.tonytaotao.algorithm;

/**
 * 动态规划
 */
public class DynamicProgramming {


    public static void main(String[] args) {

        ZeroOnepackage();

        /*String strA = "abce";
        String strB = "bcde";
        findLCSeq(strA, strB);
        findLCStr(strA, strB);*/


    }


    /**
     * 0-1背包问题
     */
    public static void ZeroOnepackage() {
        int[] w = {1, 4, 3};
        int [] val = {1500, 3000, 2000};
        int m = 4; //背包容量
        int n = val.length; // 物品的个数

        // 记录商品放入的情况
        int[][] path = new int[n+1][m+1];

       //创建二维数组
        //v[i][j] 表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] v = new int[n+1][m+1];

        // 初始化第一行第一列，默认都是0，表示未装入物品时的价值
        for(int i = 0; i < v.length; i++) {
            v[i][0] = 0; // 第一列设置为0
        }
        for(int i = 0; i < v[0].length; i++) {
            v[0][i] = 0; // 第一行设置为0
        }

        // 动态规划处理
        for(int i = 1; i<v.length;i++) { // 不处理第一行
            for(int j = 1; j < v[0].length; j++) { // 不处理第一列

                if(w[i-1] > j) {  //因为程序是从1开始的，因此原来公式中的w[i] 修改成 w[i-1]
                    v[i][j] = v[i-1][j];
                } else {
                    //为了记录商品放入背包的情况
                    //v[i][j] = Math.max(v[i-1][j], val[i-1] + v[i-1][j-w[i-1]]);
                    if (v[i-1][j] < val[i-1] + v[i-1][j-w[i-1]]) {
                        v[i][j] = val[i-1] + v[i-1][j-w[i-1]];
                        // 记录当前放入的商品
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i-1][j];
                    }
                }
            }
        }

        // 输出目前背包的情况
        for(int i = 0; i < v.length; i++) {
            for(int j = 0; j < v[i].length; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();

        // 输出最后放入的商品
        int i = path.length - 1;
        int j = path[0].length - 1;
        while(i > 0 && j > 0) { // 从path的最后还是找
            if(path[i][j] == 1) {
                System.out.printf("第%d个商品放入到背包\n", i);
                j -= w[i-1];
            }
            i--;
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

        for (int i = 0; i < lcs.length; i++) {
            for (int j = 0; j < lcs[i].length; j++) {
                System.out.print(lcs[i][j]);
            }
            System.out.println();
        }

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
