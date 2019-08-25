package com.tonytaotao.sort;

/**
 * 插入排序
 * @author tonytaotao
 */
public class InsertSortTest {

	public static void main(String[] args) {

		int[] array = { 3, 5, 2, 4, 9, 7, 6, 8, 1, 10 };

		insertSort(array);

		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}

	}

	public static void insertSort(int[] array) {
		if(array!=null&&array.length>1){
			for(int i=1;i<array.length;i++){
				int temp=array[i];
				int j;
				for(j=i-1;j>=0;j--){
					if(array[j]>temp){
						array[j+1]=array[j];
					}else{
						break;
					}
				}
				array[j+1]=temp;
			}
		}
	}

}
