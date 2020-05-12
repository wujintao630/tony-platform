package com.tonytaotao.algorithm;

/**
 * 冒泡排序
 * @author tonytaotao
 */
public class SortBuddle {

	public static void main(String[] args) {

		int[] array = { 3, 5, 2, 4, 9, 7, 6, 8, 1, 10 };

		buddleSort(array);
		
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}

	}

	public static void buddleSort(int[] array) {
		if(array!=null&&array.length>1){
			for(int i=0;i<array.length;i++){
				for(int j=0;j<array.length-i-1;j++){
					if(array[j]>array[j+1]){
						int temp=array[j];
						array[j]=array[j+1];
						array[j+1]=temp;
					}
				}
			}
		}
	}

}
