package com.tonytaotao.datastructure.linkedList;

public class SingleLinkedListDemo {

    public static void main(String[] args) {

        SingleNode hero1 = new SingleNode(1,"宋江","及时雨");
        SingleNode hero2 = new SingleNode(2,"卢俊义","玉麒麟");
        SingleNode hero3 = new SingleNode(3,"吴用","智多星");
        SingleNode hero4 = new SingleNode(4,"林冲","豹子头");

        SingleLinkedList singleLinkedList = new SingleLinkedList();


        /*singleLinkedList.add(hero1);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero4);*/

        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);

        singleLinkedList.list();
        System.out.println();

        SingleNode newSingleNode = new SingleNode(2,"卢俊义（小卢）","玉麒麟（~~）");
        singleLinkedList.edit(newSingleNode);

        singleLinkedList.list();
        System.out.println();

        singleLinkedList.del(hero1);
        singleLinkedList.list();
    }
}

class SingleLinkedList {

    // 定义头节点
    private SingleNode head = new SingleNode(0, "", "");

    /**
     * 添加节点到单向链表
     * @param singleNode
     */
    public void add(SingleNode singleNode) {

        SingleNode temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }

        // 将节点加到最后
        temp.next = singleNode;

    }

    /**
     * 添加节点到有序链表
     * @param singleNode
     */
    public void addByOrder(SingleNode singleNode) {

        SingleNode temp = head;
        // 标识添加的编号是否存在
        boolean flag = false;

        while (true) {
            if (temp.next == null) {
                break;
            }

            if (temp.next.no > singleNode.no) {
                break;
            } else if (temp.next.no == singleNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }

        if (flag) {
            System.out.printf("英雄编号%d已存在，不能插入\n", singleNode.no);
        } else {
            singleNode.next = temp.next;
            temp.next = singleNode;
        }

    }

    /**
     * 编辑节点
     * @param newSingleNode
     */
    public void edit(SingleNode newSingleNode) {

        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        SingleNode temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }

            if (temp.no == newSingleNode.no) {
                flag = true;
                break;
            }

            temp = temp.next;
        }

        if (flag) {
            temp.name = newSingleNode.name;
            temp.nickName = newSingleNode.nickName;
        } else {
            System.out.printf("没有找到编号%d的节点，不能修改\n", newSingleNode.no);
        }

    }

    /**
     * 删除节点
     * @param delSingleNode
     */
    public void del(SingleNode delSingleNode) {

        SingleNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }

            if (temp.next.no == delSingleNode.no) {
                flag = true;
                break;
            }

            temp = temp.next;
        }

        if (flag) {
            temp.next = temp.next.next;
        } else {
            System.out.printf("没有找到编号%d的节点\n", delSingleNode.no);
        }
    }

    /**
     * 输出链表
     */
    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        SingleNode temp = head.next;
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
class SingleNode {

    public int no;
    public String name;
    public String nickName;
    public SingleNode next; // 指向下一个节点

    public SingleNode(int no, String name, String nickName) {
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
