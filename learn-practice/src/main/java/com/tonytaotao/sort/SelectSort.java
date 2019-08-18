package com.tonytaotao.sort;

/**
 * 选择排序
 * @author tonytaotao
 * 
 */
public class SelectSort {
	public static void main(String[] args) {
		int[] array = { 3, 5, 2, 4, 9, 7, 6, 8, 1, 10 };
		new SelectSort().selectSort(array);
		
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}
	}

	public void selectSort(int[] array) {
		if(array!=null&&array.length>1){
			for(int i=0;i<array.length-1;i++){
				int minIndex=i;
				for(int j=i+1;j<array.length;j++){
					if(array[j]<array[minIndex]){
						minIndex=j;
					}
				}
				if(minIndex!=i){
					int temp=array[minIndex];
					array[minIndex]=array[i];
					array[i]=temp;
				}
			}
		}
	}

}
