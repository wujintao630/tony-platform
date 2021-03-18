package com.tonytaotao.datastructure.linkedList;

public class JosephuDemo {

    public static void main(String[] args) {
        Josephu josephu = new Josephu();
        josephu.addBoy(5);
        josephu.showBoy();

        josephu.countBoy(1,2,5);
    }
}

class Josephu {
    private Boy first = null;

    // 添加小孩，构建环形链表
    public void addBoy(int nums) {
        if (nums < 1) {
            System.out.println("nums编号不正确");
            return;
        }

        Boy currentBoy = null; // 辅助节点，构建环形链表

        for (int i = 1 ; i <= nums; i++) {
            Boy boy = new Boy(i);

            if ( i == 1) {
                first = boy;
                first.setNext(first); // 构建环
                currentBoy = first;
            } else {
                currentBoy.setNext(boy);
                boy.setNext(first);
                currentBoy = boy;
            }
        }
    }

    public void showBoy() {
        if (first == null) {
            System.out.println("没有小孩");
            return;
        }

        Boy currentBoy = first;
        while (true) {
            System.out.printf("小孩编号%d \n", currentBoy.getNo());

            if (currentBoy.getNext() == first) {
                System.out.println("遍历完毕");
                break;
            }

            currentBoy = currentBoy.getNext();
        }
    }

    /**
     *
     * @param startNo 从第几个小孩开始数
     * @param countNo 数几下
     * @param nums 最初有多少个小孩
     */
    public void countBoy(int startNo, int countNo, int nums) {
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("参数输入有误");
            return;
        }

        Boy helper = first;
        while (true) {
            if (helper.getNext() == first) { // helper指向了最后一个节点
                break;
            }
            helper = helper.getNext();
        }

        // 移动到起始位
        for (int j = 0; j< startNo - 1; j++) {
            first = first.getNext();
            helper = helper.getNext();
        }

        while (true) {
            if (helper == first) {
                break;
            }

            for (int j = 0; j < countNo -1 ; j++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            System.out.printf("小孩%d出圈\n", first.getNo());
            first = first.getNext();
            helper.setNext(first);
        }

        System.out.printf("小孩%d出圈,结束\n", first.getNo());



    }
}

class Boy {
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
