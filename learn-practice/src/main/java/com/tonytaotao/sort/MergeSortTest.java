package com.tonytaotao.sort;

/**
 * 归并排序
 * @author tonytaotao
 * 
 */
public class MergeSortTest {

	public static void main(String[] args) {

		int[] array = { 3, 5, 2, 4, 9, 7, 6, 8, 1, 10 };

		mergeSort(array, 0, array.length-1);

		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}
	}

	public static void mergeSort(int[] array, int low, int high) {
		if(low<high){
			int mid=(low+high)/2;
			mergeSort(array, low, mid);
			mergeSort(array, mid+1, high);
			int[] tempArray=new int[array.length];//定义临时数组，与原数组长度一样
			int m=low;//左数组的起始位置
			int n=mid+1;//右数组的起始位置
			int k=low;//临时数组的起始位置
			while(m<=mid&&n<=high){// 以mid为界，将数组分为左右两个，然后循环比较两个数组里，将最小的放在临时数组里
				if(array[m]<array[n]){
					tempArray[k++]=array[m++];
				}else{
					tempArray[k++]=array[n++];
				}
			}
			
			while(m<=mid){// 将左边未合并的放在临时数组里
				tempArray[k++]=array[m++];
			}
			
			while(n<=high){// 将右边未合并的放在临时数字里
				tempArray[k++]=array[n++];
			}
			
			while(low<=high){// 将临时数组中的存储到原数组中
				array[low]=tempArray[low++];
			}
		}
	}

}
