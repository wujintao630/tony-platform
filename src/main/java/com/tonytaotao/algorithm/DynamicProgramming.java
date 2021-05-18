package com.tonytaotao.algorithm;

/**
 * 动态规划
 */
public class DynamicProgramming {

    /**
     * 0-1背包问题
     */
    /*
    public static void main(String[] args) {
        int capacity = 10;
        int amount = 5;
        int[] weight = {0,2,2,6,5,4};
        int[] value = {0,6,3,5,4,6};
        zeroOnePackage(capacity, amount, weight, value);
    }
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
    }*/

    /**
     * 最大子序和
     */
    /*
    public static void main(String[] args) {
        int[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubSequenceSum(array));
    }
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
    */

    /**
     * 最长公共子序列
     */
    /*public static void main(String[] args) {
        String strA = "abce";
        String strB = "bcde";
        char[] charA = strA.toCharArray();
        char[] charB = strB.toCharArray();
        int lenA = charA.length;
        int lenB = charB.length;
        int[][] tempPath = findLongestCommonSubsequence(charA, charB, lenA, lenB);
        for (int[] ints : tempPath) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        printLongestCommonSubsequence(charA, tempPath, lenA, lenB);
    }
    public static int[][] findLongestCommonSubsequence(char[] charA, char[] charB, int lenA, int lenB) {

        // 存储匹配时当前公共子序列的长度，初始化第一行第一列为0
        int[][] tempLength = new int[lenA + 1][lenB + 1];
        for(int i = 0; i <= lenA; i++) {
            tempLength[i][0] = 0;
        }
        for(int i = 0; i <= lenB ; i++) {
            tempLength[0][i] = 0;
        }

        // 存储匹配时当前的匹配方向（1：占用，0：向上，-1：向左），初始化第一行第一列为0
        int[][] tempPath = new int[lenA + 1][lenB + 1];
        for(int i = 0; i <= lenA; i++) {
            tempPath[i][0] = 0;
        }
        for(int i = 0; i <= lenB; i++) {
            tempPath[0][i] = 0;
        }

        for (int i = 1 ; i <= lenA; i++) {
            for (int j = 1; j <= lenB; j++) {
                if (charA[i-1] == charB[j-1]) {
                    tempLength[i][j] = tempLength[i-1][j-1] + 1;
                    tempPath[i][j] = 1;
                } else {
                    if (tempLength[i-1][j] >= tempLength[i][j-1]) {
                        tempLength[i][j] = tempLength[i-1][j];
                        tempPath[i][j] = 0;
                    } else {
                        tempLength[i][j] = tempLength[i][j-1];
                        tempPath[i][j] = -1;
                    }
                }
            }
        }

        return tempPath;
    }

    public static void printLongestCommonSubsequence(char[] charA, int[][] tempPath, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (tempPath[i][j] == 1) {
            printLongestCommonSubsequence(charA, tempPath, i-1, j-1);
            System.out.print(charA[i-1]);
        } else if (tempPath[i][j] == 0) {
            printLongestCommonSubsequence(charA, tempPath, i-1, j);
        } else if (tempPath[i][j] == -1) {
            printLongestCommonSubsequence(charA, tempPath, i, j-1);
        }
    }*/


    /**
     * 最长公共子串
     */

    /*public static void main(String[] args) {
        String strA = "eplm";
        String strB = "people";
        findLongestCommonStr(strA, strB);

    }
    public static void findLongestCommonStr(String strA, String strB) {

        char[] charA = strA.toCharArray();
        char[] charB = strB.toCharArray();
        int lenA = charA.length;
        int lenB = charB.length;

        int commonStrIndexEnd = 0;
        int maxLength = 0;
        int[][] tempLength = new int[lenA + 1][lenB + 1];
        for(int i = 0; i <= lenA; i++) {
            tempLength[i][0] = 0;
        }
        for(int i = 0; i <= lenB ; i++) {
            tempLength[0][i] = 0;
        }

        for (int i = 1; i <= lenA; i++) {
            for (int j = 1; j <= lenB; j++) {
                if (charA[i-1] == charB[j-1]) {
                    tempLength[i][j] = tempLength[i-1][j-1] + 1;

                    // 比较长度
                    if (tempLength[i][j] > maxLength) {
                        maxLength = tempLength[i][j];
                        commonStrIndexEnd = i;
                    }
                } else {
                    tempLength[i][j] = 0;
                }
            }
        }

        System.out.println("最长公共子串长度为：" + maxLength);
        System.out.println("最长公共子串为：" + strA.substring(commonStrIndexEnd-maxLength, commonStrIndexEnd));
    }*/

    /**
     * 最短路径
     */
   /* public static void main(String[] args) {

        int[][] pathGrid = {{1,3,4,8}, {3,2,2,4}, {5,7,1,9}, {2,3,2,3}};
        System.out.println(minPath(pathGrid));

    }
    public static int minPath(int[][] pathGrid) {

        if (pathGrid == null || pathGrid.length == 0 || pathGrid[0].length == 0) {
            return 0;
        }

        int row = pathGrid.length;
        int column = pathGrid[0].length;

       int[][] minPathGrid  = new int[row][column];

        minPathGrid[0][0] = pathGrid[0][0];

       for (int i = 1; i<row; i++) {
           minPathGrid[i][0] = minPathGrid[i-1][0] + pathGrid[i][0];
       }

        for (int i = 1; i<column; i++) {
            minPathGrid[0][i] = minPathGrid[0][i-1] + pathGrid[0][i];
        }

        for(int i = 1; i< row; i++) {
            for (int j = 1 ;j<column;j++) {
                minPathGrid[i][j] = Math.min(minPathGrid[i-1][j], minPathGrid[i][j-1]) + pathGrid[i][j];
            }
        }

        return minPathGrid[row-1][column-1];
    }*/
}
