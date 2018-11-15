/**
Maximum Subarray:

Given an array of integers, find a contiguous subarray which has the largest sum.

Example
Given the array [−2,2,−3,4,−1,2,1,−5,3], the contiguous subarray [4,−1,2,1] has the largest sum = 6.
*/

import java.util.*;
import java.io.*;

public class MaxSubarray{
	/*
     * @param nums: A list of integers
     * @return: A integer indicate the sum of max subarray
     * Solution1: Using divide and conquer; 
       -the max subarr is either find on the left, right, or across middle.
       -Ex: [-1, 2] => -1, -1+2, 2 ==> 2 is the largest;
     * Time: O(N lg N);
     */
    public static int maxSubArray(int[] nums) {
    	int N = nums.length;
    	return helper(nums, 0, N-1);
    }

    //Time: 2 * T(N/2) + O(N) = NlgN
    public static int helper(int [] nums, int start, int end){

    	if(start >= end)
    		return nums[start];
    	int mid = (start + end) / 2;
    	int left = helper(nums, start, mid); //T(N/2)
    	int right = helper(nums, mid+1, end); //T(N/2)
    	int cross = crossSum(nums, start, mid, end); //N

    	return Math.max(Math.max(left, right), cross);
    }

    // sum cross the middle of array, add mid->left + mid->right. 
    // pick the largest starting from middle. ex: left[-1, 2] right[-1,4] => (2 + 3); left (2) > (2+ -1); right(-1+4 > -1); 
    public static int crossSum(int [] nums, int start, int mid, int end){
    	//find max starting from mid->left
    	int leftMax = Integer.MIN_VALUE;
    	int curSum = 0;
    	//mid -> left;
    	for (int i = mid; i >= start; i--) {
    		curSum += nums[i];
    		if(curSum > leftMax)
    			leftMax = curSum;
    	}
    	//find max starting from mid->right
    	curSum = 0;
    	int rightMax = Integer.MIN_VALUE;
    	//mid->right
    	for (int i= mid+1; i <= end; i++) {
    		curSum += nums[i];
    		if(curSum > rightMax)
    			rightMax = curSum;
    	}
    	//ex: left[-1, 2] right[-1,4]; 
    	//either (2, -1) or (-1,2,-1) or (2,-1,4) or (-1,2,-1,4); (2,-1,4) is the answer
    	return leftMax + rightMax;
    }

    /*
	Solution2: Kadane's algorithm; 
	-look for all positive contiguous segments of the array. (positive_sum_carryover)
	-keep track of maximum sum contiguous segment among all positive segments. (max_so_far)
	-Each time we get a positive sum compare it with max_so_far and update max_so_far
    -Check for max before curSum set to 0, that way all negative numbers can also be considered.
	-Negative Values carry over will make future vals smaller. (dont want to track that, b/c we are looking for largest)
	-Positive Values carry over will make future vals larger. (We want that, since we are looking for the largest)
    
    time: O(N)
    */

    static int maxSubArray2(int nums[])
    {
        int max = Integer.MIN_VALUE;
        int positive_sum_carryover = 0;
 
        for (int i = 0; i < nums.length; i++)
        {
            positive_sum_carryover += nums[i];
            if (positive_sum_carryover > max)
                max = positive_sum_carryover;
            //if carryover_sum becomes negative; reset to 0; we don't want to make future nums samller
            if (positive_sum_carryover < 0)
                positive_sum_carryover = 0;
        }
        return max;
    }

    public static void main(String[] args){
    	int [] arr = {-2,2,-3,4,-1,2,1,-5,3};
    	System.out.println(maxSubArray2(arr));
    }
}