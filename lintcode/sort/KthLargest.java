/**
Find K-th largest element in an array.
*/
import java.util.*;
import java.io.*;

public class KthLargest{
	//return index of the parent node, @para node = index of childnode
	public static int parent(int node){
		return (node-1) / 2;  //ex: 3 is parent of 6,7; 1 id parent of 2,3
	}
	//return index of the left node, @para node = index of parentNode
	public static int left(int node){
		return 2 * node + 1;
	}
	//return index of the right node, @para node = index of parentNode
	public static int right(int node){
		return 2 * node + 2;
	}

	//index i is the root of subtree needed to minHeapify, O(lg n), height
	public static void maxHeapify(int [] arr, int i, int elementNum){
		int l = left(i);
		int r = right(i);
		int largest = i; //largest index among 3 
		//find the largest index
		if( l <= elementNum -1){ //within # of elements now
			if(arr[l] > arr[largest])
				largest = l;
		}
		if( r <= elementNum -1 ){
			if(arr[r] > arr[largest])
				largest = r;
		}
		//if root is not the largest, swap elements and heapify subtree of largest
		if(largest != i){
			swap(arr, i, largest);
			maxHeapify(arr, largest, elementNum);
		}
	}
	//heapify from bottom up, sort the array in minHeap order, O(lg n) for each, n/2 total, so n/2 * lgn = O(n lg n);
	public static void buildMaxHeap(int [] arr, int elementNum){

		int lastNotLeaf =  parent(elementNum -1); //arr.length is last element, arr.length / 2 == parent of last element with children
		for(int i= lastNotLeaf; i >= 0; i--){
			maxHeapify(arr, i, elementNum);
		}
		//return arr;
	}

	public static int extractMax(int [] heap, int elementNum){
		if(elementNum == 0)
			return Integer.MIN_VALUE;
		int root = heap[0];
		//use last element to root and heapify, it will goes to appropriate level, other elements will also be in the right level
		if(elementNum > 1){
			heap[0] = heap[elementNum-1];
			maxHeapify(heap, 0, elementNum);
		}

		return root;

	}

	public static void printHeap(int [] heap, int elementNum){
		for(int i =0; i< elementNum; i++){
			System.out.print(heap[i] + " ");
		}
		System.out.println();
	}

	//swap 2 index elements
	public static void swap(int [] arr, int i, int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
		//System.out.println(arr[i] + " " + arr[j]);
	}

	/**
	build MaxHeap arr first, O(n) time. extractMax k-1 times, to get the kth max. O(k) time.
	O(n + k*lg(n)); heapify worst case if lg(n) time. but not all nodes, as it goes up the time of heapify decreases.
	*/
	public static int kthLargestElement(int k, int[] nums) {
    
    int elementNum = nums.length;
    buildMaxHeap(nums, elementNum);
    for(int i=1; i<= k-1; i++){
    	extractMax(nums, elementNum);
    	elementNum--;
    }
    return nums[0];
  }

  /**
	quick sort solution: use partition to go either left or right, 
	averge case: O(n);  
	worst case: O(n^2); target is the last one to be pivot, ex: arr is sorted decrement, 10-1, and k is find 1st largest. partition will goes from right: 1-10, so 10 is the last to be pivot. so partition n-times, each time loop through n (the worst)
  */
  public static int kthLargestElementQuick(int k, int [] nums){
  	return kthLargestQuickHelper(k, nums, 0, nums.length-1);
  }

  public static int kthLargestQuickHelper(int k, int [] nums, int l, int r){
  	//make sure k is within arr length
  	if(k > 0 && k <= r - l + 1){
  		int pivotInd = decrementPartition(nums, l, r);
  		System.out.println("pivot: " + pivotInd);
  		if(k-1 == pivotInd - l)
  			return nums[pivotInd];
  		//when pivot is smaller(right side) than kth, kth should be on the left of pivot, since partition move all larger #s to left.
  		else if(k-1 < pivotInd - l)
  			return kthLargestQuickHelper(k, nums, l, pivotInd-1);
  		//when pivot is larger(left side) of kth, kth goes to right, kth should be updated to k- pivot b/c leftInd is updated. k-pivot-1 (0 indexed) + l (left is 1st index), ex: l=2 k=8 pivot=5 8-5-1 = 2(3rd index 0,1,2) + 2 = 4(3rd index from left, 2,3,4)
  		else
  			return kthLargestQuickHelper( k-pivotInd-1 + l, nums, pivotInd+1, r);
  	}

  	return Integer.MIN_VALUE;
  	
  }

  //from largest to smallest partition
  public static int decrementPartition(int [] arr, int l, int r){
  	System.out.println("arr: " + Arrays.toString(arr));
  	int pivot = arr[r];
  	int leftInd = l;
  	int rightInd = r-1;
  	//all num larger than pivot on the left: 10, 9, 8 ... 2, 1
  	while(true){
  		
  		//loop until find arr[leftInd] < pivot
  		while(arr[leftInd] >= pivot && leftInd < r){
  			System.out.println("leftInd: " + leftInd);
  			leftInd++;
  			
  		}
  		//loop until find arr[rightInd] > pivot
  		while(arr[rightInd] <= pivot && rightInd > l){
  			System.out.println("rightInd: " + rightInd + " pivot " + pivot + " " + arr[rightInd]);
  			rightInd--;

  		}

  		if(leftInd >= rightInd){
  			break;
  		}else{
  			//swap the small val in left with large val in right
  			System.out.println("swap leftInd: " + leftInd + " rightInd " + rightInd);
  			swap(arr, leftInd, rightInd);
  			leftInd++;
  			rightInd--;
  		}

  		
  	}
  	//put pivot value to the correct location, leftInd is the correct pos for pivot
  	swap(arr, leftInd, r);
  	System.out.println("leftInd: " + leftInd + " rightInd " + rightInd);
  		System.out.println(pivot);
  	System.out.println(Arrays.toString(arr));
  	return leftInd;
  }

  public static int [] randomArray(int size, int max){
		int [] arr = new int[size];
		Random rand = new Random();
		for(int i= 0; i< size; i++){
			arr[i] = rand.nextInt(max);
		}
		return arr;
	}



	public static void main(String [] args){
		Scanner reader = new Scanner(System.in);
		int [] arr = {0, 90, 100, 20, 10, 50, 30, 40, 60, 80, 70};
		//int [] arr = randomArray(10, 10);

		System.out.println("kth: ");
		int k = reader.nextInt();

		System.out.println(Arrays.toString(arr));
		// buildMaxHeap(arr, arr.length);
		//int ans = kthLargestElement(k, arr);
		//System.out.println("ans: " + ans);
		int ans2 = kthLargestElementQuick(k, arr);
		System.out.println("quick ans: " + ans2);
		//printHeap(arr, arr.length);
	}
}