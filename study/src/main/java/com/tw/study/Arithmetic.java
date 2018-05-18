package com.tw.study;

public class Arithmetic {
	//二分查找法
	public int halfFind(int[] arr,int key){
		//低位
		int low = 0;
		//高位
		int high = arr.length - 1;
		int middle = 0;			//定义middle
		
		if(key < arr[low] || key > arr[high] || low > high){
			return -1;				
		}
		
		while(low <= high){
			middle = (low + high) / 2;
			//左边，重设高位
			if(arr[middle] > key){
				high = middle - 1;
			//右边，重设低位
			}else if(arr[middle] < key){
				low = middle + 1;
			}else{
				return middle;
			}
		}
		return -1;		//最后仍然没有找到，则返回-1
	}
}
