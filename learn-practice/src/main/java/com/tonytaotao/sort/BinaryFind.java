package com.tonytaotao.sort;
/**
 * 二分查找(有序数组)
 * @author tonytaotao
 */
public class BinaryFind {
	public static void main(String[] args) {
		int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int index=new BinaryFind().binaryFind(array,1, 0, array.length - 1);
		System.out.println(index);
	}
	public Integer binaryFind(int[] array, int target, int low, int high) {
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
