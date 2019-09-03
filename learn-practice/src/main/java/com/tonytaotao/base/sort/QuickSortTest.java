package com.tonytaotao.base.sort;

/**
 * 快速排序
 * @author tonytaotao
 * 
 */
public class QuickSortTest {

	public static void main(String[] args) {

		int[] array = { 3, 5, 2, 4, 9, 7, 6, 8, 1, 10 };

		quickSort(array, 0, array.length-1);

		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}

	}

	public static void quickSort(int[] array, int low, int high) {
		if(low<high){
			int temp=array[low];//默认数组的第一个为中轴，此数将数组划为两个分数组，左边的比此值小，右边的比此值大
			int i=low,j=high;//i代表从左往右开始扫描，j代表从右往左开始扫描
			while(i!=j){
				while(array[j]>=temp&&i<j){//从右往左扫描，如果遇到比中轴值小，则停止扫描
					j--;
				}
				while(array[i]<=temp&&i<j){//从左往右扫描，如果遇到比中轴值大，则停止扫描
					i++;
				}
				if(i<j){//如果扫描点i和j没有相遇，则将两个扫描点的数据交换
					int p=array[i];
					array[i]=array[j];
					array[j]=p;
				}
			}
			array[low]=array[i];//此时i和j相遇了，则一次扫描结束，此时i=j，则在i处，i左边的比中轴值小，i右边的比中轴值要大，所以将i和初始中轴的值进行交换
			array[i]=temp;
			
			quickSort(array,low,i-1);//此时递归进行快排
			quickSort(array,i+1,high);
		}
	}

}
