package com.tonytaotao.datastructure.tree;

public class ArrayBinaryTreeDemo {

    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7};

        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(array);
        arrayBinaryTree.preOrder(0);
    }

}

class ArrayBinaryTree {
    private int[] array;

    public ArrayBinaryTree(int[] array) {
        this.array = array;
    }

    /**
     * 顺序存储二叉树的前序遍历
     * @param index 数组下标
     */
    public void preOrder(int index) {
        if (array == null || array.length == 0) {
            System.out.println("数组为空");
        }
        // 输出当前元素
        System.out.println(array[index]);
        // 向左递归
        if ((index * 2 + 1) < array.length) {
            preOrder(2*index+1);
        }
        // 向右递归
        if ((index * 2 + 2) < array.length) {
            preOrder(2*index + 2);
        }
    }
}