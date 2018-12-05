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

	/**
	 * ASC loop check <= pivot
	 */
	public void quickSort2(int[] A, int start, int end) {
    if (start >= end) {
      return;
    }
    // pivot is in correct position.
    int pivot = partition2(A, start, end);
    // sort left and right side of pivot.
    quickSort2(A, start, pivot - 1);
    quickSort2(A, pivot + 1, end);
  }
  
  /**
	 * ASC loop check <= pivot
	 */
  public int partition2(int[] A, int start, int end) {
    // make endVal = pivot, or any from start -> end.
    int pivot = A[end];
    // pivotIndex is the left pos for <= pivot elm.
    int pivotIndex = start;
    // find elms <= pivot and put to left.
    for (int hi = start; hi <= end - 1; hi++) {
      // if lower val found, swap to left.
      if (A[hi] <= pivot) {
        // swap
        int temp = A[pivotIndex];
        A[pivotIndex] = A[hi];
        A[hi] = temp;
        // increment pivotIndex. Finished cur pos.
        pivotIndex++;
      }
    }
    // now insert pivot to the correct position, left all <= pivot, right > pivot.
    // pivotIndex is at the 1st right position, (1st elm > pivot or pivot itself).
    int temp = A[pivotIndex];
    A[pivotIndex] = A[end];
    A[end] = temp;
    
    return pivotIndex;
  }

  
	
	/**
	 * left and right index compare.
	 */
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

	/**
	 * left and right index compare.
	 */
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
