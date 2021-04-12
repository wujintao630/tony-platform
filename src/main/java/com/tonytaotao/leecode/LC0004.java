package com.tonytaotao.leecode;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 *
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 *
 * 示例 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * 则中位数是 2.0
 *
 * 示例 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * 则中位数是 (2 + 3)/2 = 2.5
 *
 * 题目要求时间复杂度为O(log(m + n))。基本可以确定本题应该用二分查找，对于数组arr的中位数，如果数组长度为len，len为奇数，则中位数为第（len+1）/2 位，如果len为偶数，我们需要知道第 len/2和 len/2+1 个数。
 * 我们需要找出两个排序数组的第k个数的问题。比较两个数组的第k/2位，然后将第k/2位较小的数组中的前k/2位删除。 然后继续此过程
 *
 * A={1,3,4,9}   lenA=4
 * B={1,2,3,4,5,6,7,8,9}  lenB=9
 * lenA+lenB=13 ，因此找第7个数
 * 7/2 = 3   A的第3个数为4，B的第3个数为3，  因此接下来A={1,3,4,9}   B={4,5,6,7,8,9}  找第7-3=4个数，
 * 4/2=2 A的第2个数为3，B的第3个数为6，  因此接下来A={4,9}   B={4,5,6,7,8,9}   找第4-2=2个数，
 * 2/2=1 A的第1个数为4，B的第1个数为4，  因此接下来A={4}   B={5,6,7,8,9}   找第2-1=1个数，
 * 现在找第1个数，比较A[0]和B[0]谁更小即可，因此最后结果为4
 */
public class LC0004 {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int len1 = nums1.length;
        int len2 = nums2.length;

        int value1 = find(nums1, 0, len1 - 1, nums2, 0, len2 - 1, (len1 + len2 + 1) / 2);
        int value2 = find(nums1, 0, len1 - 1, nums2, 0, len2 - 1, (len1 + len2) / 2 + 1);



        return (value1 + value2) * 0.5;

    }

    private int find(int[] num1, int start1, int end1, int[] num2, int start2, int end2, int targetPosition) {

        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;

        // 确保num1的长度不大于num2的长度
        if (len1 > len2) {
            return find(num2, start2, end2, num1, start1, end1, targetPosition);
        }

        // 如果len1已经为空，直接从nums2找
        if (len1 == 0) {
            return num2[start2 + targetPosition - 1];
        }

        // 找第1个数，比较nums1[0]和nums2[0]谁更小即可
        if (targetPosition == 1) {
            return Math.min(num1[start1], num2[start2]);
        }

        // 因为nums1比较短，因此取位置时要考虑实际长度
        int pos1 = start1 + Math.min(targetPosition/2, len1) - 1;
        int pos2 = start2 + targetPosition/2 - 1;
        if (num1[pos1] > num2[pos2]) {
            return find(num1, start1, end1, num2, pos2 + 1, end2, targetPosition - targetPosition / 2);
        } else {
            return find(num1, pos1 + 1, end1, num2, start2, end2, targetPosition - Math.min(targetPosition/2, len1));
        }
    }

    public static void main(String[] args) {
        int[] num1 = {1,2};
        int[] num2 = {3,4};
        System.out.println(new LC0004().findMedianSortedArrays(num1, num2));
    }
}
