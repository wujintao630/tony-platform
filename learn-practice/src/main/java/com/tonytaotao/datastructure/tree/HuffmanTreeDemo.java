package com.tonytaotao.datastructure.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuffmanTreeDemo {

    public static void main(String[] args) {
        int[] array = {13,7,8,3,29,6,1};
        HTNode root = createHuffmanTree(array);

        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("空树");
        }
    }

    public static HTNode createHuffmanTree(int[] array) {

        List<HTNode> nodes = new ArrayList<>();
        for (int value:array) {
            nodes.add(new HTNode(value));
        }
        // 排序
        Collections.sort(nodes);

        System.out.println("nodes = " + nodes);

        while (nodes.size() > 1) {

            // 取出最小的节点
            HTNode left = nodes.get(0);
            HTNode right = nodes.get(1);

            // 形成父节点
            HTNode parent = new HTNode(left.value + right.value);
            parent.left = left;
            parent.right = right;

            // 移除已处理的节点
            nodes.remove(left);
            nodes.remove(right);

            // 形成的节点加入参与处理
            nodes.add(parent);

            // 重新由小到大排序
            Collections.sort(nodes);

        }

        // 返回哈弗曼树的root节点
        return nodes.get(0);

    }

}

class HTNode implements Comparable<HTNode> {
    public int value;
    public HTNode left;
    public HTNode right;

    public HTNode(int value) {
        this.value = value;
    }

    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(HTNode o) {
        // 从小到大排序
        return this.value - o.value;
    }
}
