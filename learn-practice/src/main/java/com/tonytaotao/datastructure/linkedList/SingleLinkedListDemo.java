package com.tonytaotao.datastructure.linkedList;

public class SingleLinkedListDemo {

    public static void main(String[] args) {

        HeroNode hero1 = new HeroNode(1,"宋江","及时雨");
        HeroNode hero2 = new HeroNode(2,"卢俊义","玉麒麟");
        HeroNode hero3 = new HeroNode(3,"吴用","智多星");
        HeroNode hero4 = new HeroNode(4,"林冲","豹子头");

        SingleLinkedList singleLinkedList = new SingleLinkedList();


        singleLinkedList.add(hero1);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);
        singleLinkedList.add(hero4);

        singleLinkedList.list();
    }
}

class SingleLinkedList {

    // 定义头节点
    private HeroNode head = new HeroNode(0, "", "");

    // 添加节点到单向链表
    public void add(HeroNode heroNode) {

        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }

        // 将节点加到最后
        temp.next = heroNode;

    }

    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
        }

        HeroNode temp = head.next;
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
class HeroNode {

    public int no;
    public String name;
    public String nickName;
    public HeroNode next; // 指向下一个节点

    public HeroNode(int no, String name, String nickName) {
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
