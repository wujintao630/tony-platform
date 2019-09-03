package com.tonytaotao.base.datastructure;

/**
 * 二叉树  https://www.cnblogs.com/ysocean/p/8032642.html
 */
public class BinaryTreeTest {

    // 根节点
    private Node root;

    public static void main(String[] args) {
        BinaryTreeTest binaryTree = new BinaryTreeTest();
        binaryTree.insert(5);
        binaryTree.insert(2);
        binaryTree.insert(6);
        binaryTree.insert(4);
        binaryTree.insert(7);
        binaryTree.insert(8);
        binaryTree.insert(9);
        binaryTree.insert(1);
        binaryTree.insert(3);

        binaryTree.find(3).printNodeValue();


    }


    /**
     * 插入节点
     */
    public boolean insert(int data) {
        Node newNode = new Node(data);
        if (root == null) {
            root = newNode;
            return true;
        }

        // 当前节点
       Node current = root;

        // 当前节点的父节点
        Node parentNode = null;

        while (current != null) {

            parentNode = current;

            if (current.getData() > data) { // 当前值比插入值大，搜索左节点
                current = current.getLcNode();
                if (current == null) {
                    parentNode.setLcNode(newNode);
                    return true;
                }
            } else if (current.getData() < data) { // 当前值比插入值小，搜索右节点
                current = current.getRcNode();
                if (current == null) {
                    parentNode.setRcNode(newNode);
                    return true;
                }
            } else {
                System.out.println("二叉树中已有该值：" + data);
                return false;
            }
        }

        return false;
    }

    /**
     *  查找某个节点
     */
    public Node find(int data) {

        Node current = root;

        while (current != null) {
            if (current.getData() > data) {
                current = current.getLcNode();
            } else if (current.getData() < data) {
                current = current.getRcNode();
            } else {
                return current;
            }
        }

        return null;
    }





}


class Node {

    private int data; // 节点数据

    private Node lcNode; // 左子节点

    private Node rcNode; // 右子节点

    public Node(int data) {
        this.data = data;
    }

    /**
     * 打印节点值
     */
    public void printNodeValue() {
        System.out.println(data);
    }


    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getLcNode() {
        return lcNode;
    }

    public void setLcNode(Node lcNode) {
        this.lcNode = lcNode;
    }

    public Node getRcNode() {
        return rcNode;
    }

    public void setRcNode(Node rcNode) {
        this.rcNode = rcNode;
    }
}