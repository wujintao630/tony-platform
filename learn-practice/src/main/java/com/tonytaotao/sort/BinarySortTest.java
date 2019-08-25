package com.tonytaotao.sort;

public class BinarySortTest {
	public static void main(String[] args) {

		int[] array = { 3, 5, 2, 4, 9, 7, 6, 8, 1, 10 };

		 binarySort(array);

		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}

	}

	// 二分排序(乱序)
	public static void binarySort(int[] array) {
		int low, high;
		int temp;// 临时变量
		if (array != null && array.length > 0) {
			for (int i = 1; i < array.length; i++) {
				temp = array[i];
				low = 0;
				high = i - 1;
				int index = binaryFind(array, temp, low, high);
				for (int j = i; j > index; j--) {
					array[j] = array[j - 1];
				}
				array[index] = temp;
			}
		}
	}

	// 二分查找(有序)
	public static int binaryFind(int[] array, int target, int low, int high) {
		if(low<high){
			int mid=(low+high)/2;
			if(array[mid]==target){
				return mid;
			}
			else if(array[mid]<target){
				return binaryFind(array, target, mid+1, high);
			}else{
				return binaryFind(array, target, low, mid-1);
			}
		}else{
			if(array[low]>=target){
				return low;
			}else{
				return low+1;
			}
		}
	}

}
