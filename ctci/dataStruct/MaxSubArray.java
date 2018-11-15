package dataStruct;

import java.util.Arrays;

public class MaxSubArray {
	
	public int[] findMaxSubarray(int[] A, int lo, int hi){
		//base case, divided into only one element, return that value
		if(lo >= hi)
			return new int[] {lo, hi, A[lo]};
		int mid = (lo+hi)/2;
		//System.out.println("mid:"+ mid);
		
		int leftSum[] = findMaxSubarray(A, lo, mid);
		int rightSum[]= findMaxSubarray(A, mid+1, hi);
		/*
		crossSum is like merge in mergeSort, where magic happens
		start at (0,1) to (0,len-1), leftSum & rightSum are all 
		returned by crossSum
		*/
		int crossSum[]= findCrossSubarray(A, lo, mid, hi);
		
		System.out.println("ls: " + Arrays.toString(leftSum));
		System.out.println("rs: " + Arrays.toString(rightSum));
		System.out.println("cs: " + Arrays.toString(crossSum));
		System.out.println("------------------");
		//one of them is store in ls or rs, value carry on to the next round
		if(leftSum[2] >= rightSum[2] && leftSum[2] >= crossSum[2])
			return leftSum;
		else if(rightSum[2] >= leftSum[2] && rightSum[2] >= crossSum[2])
			return rightSum;
		else
			return crossSum;
		//biggest value at the final round is turned to main
	}

	private int[] findCrossSubarray(int[] A, int lo, int mid, int hi) {
		int []crossSum;
		int leftSum = Integer.MIN_VALUE; //sum for the leftside
		int rightSum = Integer.MIN_VALUE; //sum for the rightside
		int leftMax= mid, rightMax= mid+1; //index leftmost, rightmost
		int tempSum = 0;
		
		for(int i= mid; i >= lo; i--){
			//System.out.println("i:"+ i);
			//System.out.println("sum:"+ sum);
			tempSum+= A[i];

			if(tempSum > leftSum){
				leftSum = tempSum;
				leftMax= i;
			}
			System.out.println("lm: "+ leftMax);
			System.out.println("lsum:"+ leftSum);
		}
		
		tempSum=0;
		
		for(int j= mid+1; j <= hi; j++){
			tempSum+= A[j];
			//System.out.println("x:"+ j);
			//System.out.println("rsum:"+ sum);
			if(tempSum > rightSum){
				rightSum = tempSum;
				rightMax= j;
			}
			System.out.println("rm: "+ rightMax);
			System.out.println("rsum:"+ rightSum);
		}
		crossSum = new int[]{leftMax,rightMax,leftSum + rightSum};
		System.out.println("csum: " + crossSum[2]);
		System.out.println("--------");
		return crossSum;
	}
	
	public static void main(String[] args){
		int [] gains = {-10, 5, 15, -20, -5, 30, 35, 20, -10, 70};
		int [] gains2= {-10,-5,-15,-30,-20,10, 25,-5};
		int [] negs = {-10, -5, -15, -20, -5, -30, -35, -10, -70};
		MaxSubArray obj = new MaxSubArray();
		int ans[]= obj.findMaxSubarray(gains, 0, gains.length-1);
		System.out.println(Arrays.toString(ans));
	}

}


