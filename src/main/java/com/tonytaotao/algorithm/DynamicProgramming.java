package com.tonytaotao.algorithm;

/**
 * 动态规划
 */
public class DynamicProgramming {


    public static void main(String[] args) {

        int capacity = 10;
        int amount = 5;
        int[] weight = {0,2,2,6,5,4};
        int[] value = {0,6,3,5,4,6};
        zeroOnePackage(capacity, amount, weight, value);

        /*
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubSequenceSum(array));
        */

        /*String strA = "abce";
        String strB = "bcde";
        findLCSeq(strA, strB);
        findLCStr(strA, strB);*/


    }


    /**
     * 0-1背包问题
     */
    public static void zeroOnePackage(int capacity, int amount, int[] weight, int[]value) {

        int[] x = new int[amount+1];// 记录物品装入的情况，0表示不装入，1表示装入

        int[][] max = new int[amount+1][capacity + 1]; // 需要维护的二维表，为了方便计算多加入一行一列，其中第0列表示背包容量为0时背包的最大价值为0

        for (int i = 0; i <= capacity; i++) {
            max[0][i] = 0;
        }

        for (int i = 0; i <= amount; i++) {
            max[i][0] = 0;
        }

        // 背包开始放入物品，从第一行第一列开始
        for (int i = 1; i<= amount; i++) {
            // j表示背包处于当前容量的大小
            for (int j = 1; j<= capacity; j++) {
                // 默认物品放不进去
                max[i][j] = max[i-1][j];

                // 当前背包能放入该物品，则需要比较放入之后的最大价值
                if (weight[i] < j) {

                    // 该物品放入之前的最大价值
                    int beforeMax = max[i-1][j-weight[i]];
                    int currentMax = beforeMax + value[i];
                    if (currentMax > max[i-1][j]) {
                        max[i][j] = currentMax;
                    }
                }
            }
        }

        // 打印全部物品的放入情况
        for (int i=0; i<=amount; i++){
            for (int j=0; j<=capacity; j++) {
                System.out.printf("%-4d", max[i][j]);

            }
            System.out.println();
        }

        System.out.println("装入背包的最大价值是：" + max[amount][capacity]);

        // 找出价值最大时，放入的物品
        for (int i = amount; i > 0; i--) {
            if (max[i][capacity] > max[i-1][capacity]) {
                x[i] = 1;
                capacity = capacity - weight[i];
            } else {
                x[i] = 0;
            }
        }

        System.out.println("装入背包的物品编码是：");
        for (int i = 1; i<= amount;i++) {
            if (x[i] == 1) {
                System.out.printf("%2d", (i));
            }
        }
    }

    /**
     * 最大子序和
     * @param array
     */
    public static int maxSubSequenceSum(int[] array){
        if (array.length == 0) {
            return 0;
        }

        // 初始最大值
        int max = array[0];

        // 记录每加入一个数参与计算时，当前的最大值
        int[] result = new int[array.length];
        result[0] = array[0];

        for (int i = 1; i<array.length;i++) {
            int tmp = result[i-1] + array[i];
            result[i] = tmp > array[i] ? tmp : array[i];
            if (max < result[i]) {
                max = result[i];
            }
        }
        return max;
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
