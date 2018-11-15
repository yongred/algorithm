package dataStruct;

import java.util.Arrays;

public class MaxSubarray2 {
	public int [] bruteMaxSub(int[] A){
		int leftMax= 0, rightMax= 0;
		int tmpsum= 0;
		int sum= Integer.MIN_VALUE;
		if(A.length == 1){
			return new int[] {0,0,A[0]};
		}
		
		for(int i = 0; i< A.length-1; i++){
			tmpsum=A[i];
			if(tmpsum > sum){
				System.out.println("temp: " + tmpsum);
				sum = tmpsum;
				leftMax= i;
				rightMax= i;
			}
			for(int j=i+1; j<A.length; j++){
				tmpsum+= A[j];
				System.out.println("i: " + i + " j: " + j);
				System.out.println("temp: " + tmpsum);
				if(tmpsum > sum){
					sum = tmpsum;
					leftMax= i;
					rightMax= j; //since start with (i+1), but the comparisons should be 0-0, 0-1
				}
				System.out.println("sum: " + sum);
				
			}
			System.out.println("-------------");
		}
		
		return new int[]{leftMax, rightMax, sum};
	}
	public static void main(String[] args){
		int [] gains = {-10, 5, 15, -20, -5, 30, 35, 20, -10, 70};
		int [] gains2= {-10,-5,-15,-30,-20,10, 25,-5};
		int [] negs = {-10, -5, -15, -20, -5, -30, -35, -10, -70};
		MaxSubarray2 obj = new MaxSubarray2();
		int ans[]= obj.bruteMaxSub(new int[] {20,-10,-10});
		System.out.println(Arrays.toString(ans));
	}
}
