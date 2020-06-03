package com.tonytaotao.datastructure.recursion;

public class EightQueenDemo {

    // 8个皇后
    int max = 8;

    // 数组下标表示第几个皇后，值表示皇后放置的位置
    int[] array = new int[max];

    static int count = 0;

    public static void main(String[] args) {

        EightQueenDemo eightQueenDemo = new EightQueenDemo();
        eightQueenDemo.check(0);
        System.out.printf("一共有%d种解法", count);

    }

    /**
     * check 是每一次递归时，都会有一次for循环，产生回溯
     * @param n
     */
    private void check (int n) {
        if (n == max) { // 其实8个皇后就已经放好了
            print();
            return;
        }

        // 依次放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            // 先把当前第n个皇后，放在第一列
            array[n] = i;
            if (judge(n)) { // 不冲突
                // 接着放第n+1个皇后
                check(n+1);
            }
            // 如果冲突，就继续执行array[n] = i; 即将第n个皇后，放置在本行的后移一个位置
        }
    }

    /**
     * 放置第n个皇后，检测和已经放置的皇后是否冲突
     * @param n
     * @return
     */
    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            // n是递增的，所以不可能在同一行
            // array[i] == array[n] 判断是否在同一列
            // Math.abs(n-i) == Math.abs(array[n] - array[i]) 是否在同一斜线
            if (array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    // 打印皇后摆放位置
    private void print() {
        count++;
        for(int i = 0 ; i< array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}
