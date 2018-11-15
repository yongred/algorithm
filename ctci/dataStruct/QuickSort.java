package dataStruct;

/**
 * Quick Sort: find a pivot value and compare the left most value and right most value to it
 * if left is bigger and right is smaller, switch position. loop until left index surpass right
 * index, and return that index as the pivot index, then sort (do the same) left and right separately
 * breaking it down until it is completely sorted
 */

import java.util.Random;

public class QuickSort {
	
	public int [] randomArray(int size, int max){
		int [] arr = new int[size];
		Random rand = new Random();
		for(int i= 0; i< size; i++){
			arr[i] = rand.nextInt(max);
		}
		return arr;
	}
	
	public void quickSort(int []arr, int left, int right){
		int pivotIndex = partition(arr, left, right);
		//break them down to 2 parts and sort
		if(left < pivotIndex)
			quickSort(arr, left, pivotIndex-1);
		if(right > pivotIndex)
			quickSort(arr, pivotIndex, right);
		else
			return;
	}
	
	public int partition(int [] arr, int left, int right){
		int pivot = arr[right];
		int leftPointer = left;
		int rightPointer = right;
		//left side should have all smaller values than pivot, right side is larger than pivot
		while(true){
			//keep looping from left to right until it find a value larger than pivot
			while(arr[leftPointer] < pivot){
				leftPointer++;
			}
			//keep looping from right to left until it find a value less than pivot
			while(rightPointer > 0 && arr[rightPointer] > pivot){
				rightPointer--;
			}
			//needs a break or else cause infinite loop
			//ends when 2 pointers meet
			if(leftPointer >= rightPointer)
				break;
			
			//swap values of the leftside and rightside found above
			else{
				int temp = arr[leftPointer];
				arr[leftPointer] = arr[rightPointer];
				arr[rightPointer] = temp;
				leftPointer++;
				rightPointer--;
			}
		}
		return leftPointer;
	}
	
	public static void main(String [] args){
		QuickSort obj = new QuickSort();
		int [] randArr = obj.randomArray(10, 100);
		System.out.println("Before sort");
		for(int i : randArr){
			System.out.print(i + ", ");
		}
		System.out.println();
		System.out.println("After sort");
		obj.quickSort(randArr, 0, randArr.length -1);
		for(int i : randArr){
			System.out.print(i + ", ");
		}
	}
}
