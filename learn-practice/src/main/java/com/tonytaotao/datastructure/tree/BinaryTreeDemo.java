package com.tonytaotao.datastructure.tree;

/**
 * 二叉树
 */
public class BinaryTreeDemo {

    public static void main(String[] args) {

        BinaryTree binaryTree = new BinaryTree();
        BNode root = new BNode(1, "宋江");
        BNode BNode2 = new BNode(2, "吴用");
        BNode BNode3 = new BNode(3, "卢俊义");
        BNode BNode4 = new BNode(4, "林冲");
        BNode BNode5 = new BNode(5, "关胜");

        binaryTree.setRoot(root);

        root.setLeft(BNode2);
        root.setRight(BNode3);
        BNode3.setRight(BNode4);
        BNode3.setLeft(BNode5);

        System.out.println("前序遍历");
        binaryTree.preOrder();
        System.out.println();

        System.out.println("中序遍历");
        binaryTree.infixOrder();
        System.out.println();

        System.out.println("后序遍历");
        binaryTree.postOrder();
        System.out.println();

        BNode result = binaryTree.preOrderSearch(15);
        if (result != null) {
            System.out.printf("找到了，信息为no = %d, name = %s", result.getNo(), result.getName());
        } else {
            System.out.println("没有找到");
        }


    }

}

class BinaryTree {
    private BNode root;
    public void setRoot(BNode root) {
        this.root = root;
    }

    public void preOrder() {
        if(this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空");
        }
    }

    public void infixOrder() {
        if(this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空");
        }
    }

    public void postOrder() {
        if(this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空");
        }
    }

    public BNode preOrderSearch(int n) {
        if(this.root != null) {
            return this.root.preOrderSearch(n);
        } else {
            return null;
        }
    }

    public BNode infixOrderSearch(int n) {
        if(this.root != null) {
            return this.root.infixOrderSearch(n);
        } else {
            return null;
        }
    }
    public BNode postOrderSearch(int n) {
        if(this.root != null) {
            return this.root.postOrderSearch(n);
        } else {
            return null;
        }
    }
}


class BNode {

    private int no;

    private String name;

    private BNode left;

    private BNode right;

    public BNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        // 先输入父节点
        System.out.println(this);
        // 递归左子树
        if (this.left != null) {
            this.left.preOrder();
        }
        // 递归右子树
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        // 递归左子树
        if (this.left != null) {
            this.left.infixOrder();
        }
        // 输出父节点
        System.out.println(this);
        // 递归右子树
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        // 递归左子树
        if(this.left != null) {
            this.left.postOrder();
        }
        // 递归右子树
        if(this.right != null) {
            this.right.postOrder();
        }
        // 输出父节点
        System.out.println(this);
    }

    public BNode preOrderSearch(int n) {
        if(this.no == n) {
            return this;
        }

        BNode resBNode = null;
        if(this.left != null) {
            resBNode = this.left.preOrderSearch(n);
        }
        if(resBNode != null) {
            return resBNode;
        }

        if(this.right != null) {
            resBNode = this.right.preOrderSearch(n);
        }
        return resBNode;

    }

    public BNode infixOrderSearch(int n) {

        BNode resBNode = null;
        if(this.left != null) {
            resBNode = this.left.infixOrderSearch(n);
        }
        if(resBNode != null) {
            return resBNode;
        }

        if(this.no == n) {
            return this;
        }

        if(this.right != null) {
            resBNode = this.right.infixOrderSearch(n);
        }
        return resBNode;

    }

    public BNode postOrderSearch(int n) {

        BNode resBNode = null;
        if(this.left != null) {
            resBNode = this.left.infixOrderSearch(n);
        }
        if(resBNode != null) {
            return resBNode;
        }

        if(this.right != null) {
            resBNode = this.right.infixOrderSearch(n);
        }
        if(resBNode != null) {
            return resBNode;
        }

        if(this.no == n) {
            return this;
        }

        return resBNode;

    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BNode getLeft() {
        return left;
    }

    public void setLeft(BNode left) {
        this.left = left;
    }

    public BNode getRight() {
        return right;
    }

    public void setRight(BNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}