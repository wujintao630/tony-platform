package com.tonytaotao.datastructure.sort;

import java.util.Arrays;

/**
 * 希尔排序（插入排序的优化版本）
 * @author tonytaotao
 */
public class ShellSortDemo {
    public static void main(String[] args) {
        int[] array = {8,9,1,7,10,2,3,5,4,6,0};

        //shellSortGapExchange(array);
        shellSortGapMove(array);

    }

    /**
     * 移位法
     * @param array
     */
    public static void shellSortGapMove(int[] array) {
        for(int gap = array.length / 2; gap > 0; gap /= 2) {
            // 从第gap个元素，对所在的组进行直接插入排序
            for (int i = gap; i < array.length; i++) {
                int j = i;
                int temp = array[j];
                if(array[j] < array[j-gap]) {
                    while(j - gap >= 0 && temp < array[j-gap]) {
                        // 移动
                        array[j] = array[j-gap];
                        j = j-gap;
                    }
                    array[j] = temp;
                }
            }
            System.out.println("每轮排序结果：" + Arrays.toString(array));
        }
    }

    /**
     * 交换法
     * @param array
     */
    public static void shellSortGapExchange(int[] array) {
        int temp = 0;

        for(int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
                //共5组，每组2个元素，步长5
                for(int j = i - gap; j >= 0; j -= gap) {
                    // 比较当前元素 和加上步长的 的那个元素
                    if (array[j] > array[j+gap]) {
                        temp = array[j];
                        array[j] = array[j+gap];
                        array[j+gap] = temp;
                    }
                }
            }
            System.out.println("每轮排序结果：" + Arrays.toString(array));
        }
    }

    public static void shellSort(int[] array) {

        int temp = 0;

        // 第1轮排序，分成五组
        for (int i = 5; i < array.length; i++) {
            //共5组，每组2个元素，步长5
            for(int j = i - 5; j >= 0; j -= 5) {
                // 比较当前元素 和加上步长的 的那个元素
                if (array[j] > array[j+5]) {
                    temp = array[j];
                    array[j] = array[j+5];
                    array[j+5] = temp;
                }
            }
        }
        System.out.println("第1轮排序结果：" + Arrays.toString(array));

        // 第2轮排序
        for (int i = 2; i < array.length; i++) {
            //共2组，每组5个元素，步长2
            for(int j = i - 2; j >= 0; j -= 2) {
                // 比较当前元素 和加上步长的 的那个元素
                if (array[j] > array[j+2]) {
                    temp = array[j];
                    array[j] = array[j+2];
                    array[j+2] = temp;
                }
            }
        }
        System.out.println("第2轮排序结果：" + Arrays.toString(array));

        // 第3轮排序
        for (int i = 1; i < array.length; i++) {
            //共1组，每组10个元素，步长1
            for(int j = i - 1; j >= 0; j -= 1) {
                // 比较当前元素 和加上步长的 的那个元素
                if (array[j] > array[j+1]) {
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        System.out.println("第3轮排序结果：" + Arrays.toString(array));

    }

}
