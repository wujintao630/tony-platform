package com.tonytaotao.datastructure.linkedList;

public class DoubleLinkedListDemo {

    public static void main(String[] args) {

        DoubleNode hero1 = new DoubleNode(1,"宋江","及时雨");
        DoubleNode hero2 = new DoubleNode(2,"卢俊义","玉麒麟");
        DoubleNode hero3 = new DoubleNode(3,"吴用","智多星");
        DoubleNode hero4 = new DoubleNode(4,"林冲","豹子头");

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();


        doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);

        doubleLinkedList.list();
        System.out.println();

        DoubleNode newHero = new DoubleNode(2,"卢俊义（小卢）","玉麒麟（~~）");
        doubleLinkedList.edit(newHero);

        doubleLinkedList.list();
        System.out.println();

        doubleLinkedList.del(hero1);
        doubleLinkedList.list();
    }
}

class DoubleLinkedList {

    // 定义头节点
    private DoubleNode head = new DoubleNode(0, "", "");

    public DoubleNode getHeadNode() {
        return head;
    }

    /**
     * 添加节点到单向链表
     * @param doubleNode
     */
    public void add(DoubleNode doubleNode) {

        DoubleNode temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }

        // 将节点加到最后
        temp.next = doubleNode;
        doubleNode.pre = temp;

    }

    /**
     * 编辑节点
     * @param newDoubleNode
     */
    public void edit(DoubleNode newDoubleNode) {

        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        DoubleNode temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }

            if (temp.no == newDoubleNode.no) {
                flag = true;
                break;
            }

            temp = temp.next;
        }

        if (flag) {
            temp.name = newDoubleNode.name;
            temp.nickName = newDoubleNode.nickName;
        } else {
            System.out.printf("没有找到编号%d的节点，不能修改\n", newDoubleNode.no);
        }

    }

    /**
     * 删除节点
     * @param delDoubleNode
     */
    public void del(DoubleNode delDoubleNode) {

        if (head == null) {
            System.out.println("链表为空，无法删除");
        }

        DoubleNode temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }

            if (temp.no == delDoubleNode.no) {
                flag = true;
                break;
            }

            temp = temp.next;
        }

        if (flag) {

            temp.pre.next = temp.next;

            // 如果是删除最后一个节点，就有空指针异常
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }


        } else {
            System.out.printf("没有找到编号%d的节点\n", delDoubleNode.no);
        }
    }

    /**
     * 输出链表
     */
    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
        }

        DoubleNode temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }

            System.out.println(temp);
            // 扫描节点后移
            temp = temp.next;
        }
    }
}

/**
 * 定义节点
 */
class DoubleNode {

    public int no;
    public String name;
    public String nickName;
    public DoubleNode next; // 指向下一个节点
    public DoubleNode pre; // 指向上一个节点

    public DoubleNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
