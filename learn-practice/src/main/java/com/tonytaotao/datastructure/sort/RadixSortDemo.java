package com.tonytaotao.datastructure.sort;

import java.util.Arrays;

/**
 * 基数排序（桶排序的扩展）
 * @author tonytaotao
 */
public class RadixSortDemo {

    public static void main(String[] args) {

        int[] array = {53,3,542,748,14,214};
        radixSortFinal(array);

    }

    public static void radixSortFinal(int[] array) {
        // 定义二维数组，表示桶
        // 长度为array.length,防止溢出，典型的空间换时间算法
        int[][] bucket = new int[10][array.length];

        // 记录每个桶的数据的个数
        int[] bucketElementCountArray = new int[10];

        // 得到数据中的最大数
        int max = array[0];
        for (int i = 1; i< array.length;i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        int maxLength = (max + "").length();
        for (int i = 0 , n = 1; i< maxLength;i++, n *= 10) {
            // 针对个十百。。。。位处理
            for (int j = 0; j< array.length; j++) {
                // 每个数的i位
                int digitOfElement = array[j]/n%10;
                // 放入对应的桶中
                bucket[digitOfElement][bucketElementCountArray[digitOfElement]] = array[j];
                bucketElementCountArray[digitOfElement]++;
            }
            // 依次取出桶的数据，放入到原始数组
            int index = 0;
            for (int k = 0; k < bucketElementCountArray.length; k++) {
                if (bucketElementCountArray[k] != 0) {
                    for (int m = 0; m< bucketElementCountArray[k] ;m++) {
                        array[index++] = bucket[k][m];
                    }
                }
                // 标识桶中的数据已取
                bucketElementCountArray[k] = 0;
            }
            System.out.println(Arrays.toString(array));
        }
    }

    /**
     * 基数排序的推导过程
     * @param array
     */
    public static void radixSort(int[] array) {

        // 定义二维数组，表示桶
        // 长度为array.length,防止溢出，典型的空间换时间算法
        int[][] bucket = new int[10][array.length];

        // 记录每个桶的数据的个数
        int[] bucketElementCountArray = new int[10];

        // 第一轮
        for (int j = 0; j< array.length; j++) {
            // 每个数的个位
            int digitOfElement = array[j]%10;
            // 放入对应的桶中
            bucket[digitOfElement][bucketElementCountArray[digitOfElement]] = array[j];
            bucketElementCountArray[digitOfElement]++;
        }
        // 依次取出桶的数据，放入到原始数组
        int index = 0;
        for (int k = 0; k < bucketElementCountArray.length; k++) {
            if (bucketElementCountArray[k] != 0) {
                for (int m = 0; m< bucketElementCountArray[k] ;m++) {
                    array[index++] = bucket[k][m];
                }
            }
            // 标识桶中的数据已取
            bucketElementCountArray[k] = 0;
        }
        System.out.println(Arrays.toString(array));

        // 第二轮
        for (int j = 0; j< array.length; j++) {
            // 每个数的十位
            int digitOfElement = array[j]/10%10;
            // 放入对应的桶中
            bucket[digitOfElement][bucketElementCountArray[digitOfElement]] = array[j];
            bucketElementCountArray[digitOfElement]++;
        }
        // 依次取出桶的数据，放入到原始数组
        index = 0;
        for (int k = 0; k < bucketElementCountArray.length; k++) {
            if (bucketElementCountArray[k] != 0) {
                for (int m = 0; m< bucketElementCountArray[k] ;m++) {
                    array[index++] = bucket[k][m];
                }
            }
            // 标识桶中的数据已取
            bucketElementCountArray[k] = 0;
        }
        System.out.println(Arrays.toString(array));

        // 第三轮
        for (int j = 0; j< array.length; j++) {
            // 每个数的百位
            int digitOfElement = array[j]/100%10;
            // 放入对应的桶中
            bucket[digitOfElement][bucketElementCountArray[digitOfElement]] = array[j];
            bucketElementCountArray[digitOfElement]++;
        }
        // 依次取出桶的数据，放入到原始数组
        index = 0;
        for (int k = 0; k < bucketElementCountArray.length; k++) {
            if (bucketElementCountArray[k] != 0) {
                for (int m = 0; m< bucketElementCountArray[k] ;m++) {
                    array[index++] = bucket[k][m];
                }
            }
            // 标识桶中的数据已取
            bucketElementCountArray[k] = 0;
        }
        System.out.println(Arrays.toString(array));
    }
}
