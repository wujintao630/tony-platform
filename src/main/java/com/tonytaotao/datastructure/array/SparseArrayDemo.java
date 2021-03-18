package com.tonytaotao.datastructure.array;

/**
 * 稀疏数组
 * @author tonytaotao
 */
public class SparseArrayDemo {

    public static void main(String[] args) {

        /*----------- 创建原始的二维数组 ---------------*/
        // 棋盘11*11 0-无棋子，1-黑子，2-蓝子
        int[][] chessArray = new int[11][11];
        chessArray[1][2] = 1;
        chessArray[2][3] = 2;
        System.out.println("原始二维数组：");
        for (int[] row : chessArray) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        /*------------- 转换成稀疏数组 ----------------------*/
        // 1、遍历二维数组，得到非0的个数
        int sum = 0;
        for (int i = 0; i<11 ;i++) {
            for(int j = 0; j< 11;j++) {
                if(chessArray[i][j] != 0) {
                    sum++;
                }
            }
        }
        // 2、创建稀疏数组
        int[][] sparseArray = new int[sum + 1][3];
        // 第一行表示 原始二维数组 行、列、非0数量
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;
        // 遍历原始二维数组，记录非0数据下标
        // 稀疏数组从第二行开始记录数据,表示非0数据在原始数组的下标行、列、数据
        int count = 1;
        for (int i = 0; i<11 ;i++) {
            for(int j = 0; j< 11;j++) {
                if(chessArray[i][j] != 0) {
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = chessArray[i][j];
                    count++;
                }
            }
        }
        // 3、输出稀疏数组
        System.out.println();
        System.out.println("稀疏数组：");
        for (int i = 0; i< sparseArray.length ; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArray[i][0], sparseArray[i][1], sparseArray[i][2]);
        }
        System.out.println();

        /*--------------- 还原成原始二维数组 ------------------*/
        // 1、读取稀疏数组的第一行，创建原始二维数组
        int[][] chessArrayBack = new int[sparseArray[0][0]][sparseArray[0][1]];
        // 2、从第二行开始，读取稀疏数组后几行的数据，并赋值给原始二维数组
        for (int i = 1; i< sparseArray.length ; i++) {
            chessArrayBack[ sparseArray[i][0] ] [ sparseArray[i][1] ] = sparseArray[i][2];
        }
        // 3、输出恢复后的原始数组
        System.out.println();
        System.out.println("恢复后的原始二维数组：");
        for (int[] row : chessArrayBack) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

    }

}
