package com.tonytaotao.leetcode;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 *
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 *
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
public class LC0002AddTwoNumbers {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode rn = l1;

        while (true) {

            int sum = l1.val + (l2 == null ? 0 : l2.val);
            int nextPlus = 0;
            if (sum < 10) {
                l1.val = sum;
            } else {
                l1.val = sum % 10;
                nextPlus = 1;
            }

            if (l1.next != null) {
                l1.next.val = l1.next.val + nextPlus;
            } else {
                if (l2 == null || l2.next == null) {
                    if (nextPlus == 1) {
                        l1.next = new ListNode(1);
                    }
                } else {
                    l1.next = l2.next;
                    l2.next = null;
                    l1.next.val = l1.next.val + nextPlus;
                }
            }

            if (l1.next == null) {
                break;
            }

            l1 = l1.next;
            l2 = l2 == null ? null : l2.next;
        }

        return rn;

    }

    public static void main(String[] args) {

        ListNode n1 = new ListNode(5);
        ListNode n2 = new ListNode(9);
        ListNode n3 = new ListNode(2);
        n2.next = n1;
        n3.next = n2;


        ListNode n4 = new ListNode(5);
        ListNode n5 = new ListNode(6);
        ListNode n6 = new ListNode(5);
        n5.next = n4;
        n6.next = n5;

        ListNode result = addTwoNumbers(n3, n6);

        System.out.println(result);

    }

}

class ListNode {
    int val;
    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

}
