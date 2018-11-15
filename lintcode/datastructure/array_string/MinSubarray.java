/**
44. Minimum Subarray
Given an array of integers, find the subarray with smallest sum.

Return the sum of the subarray.

Example
For [1, -1, -2, 1], return -3.

Notice
The subarray should contain one integer at least.
*/

import java.util.*;
import java.io.*;

public class MinSubarray {
	/*
	prefix sum solution:
	- find the curSum all sums from [0-pos];
	- find the max positive sum carryover, from [0-pos];
	- find the Min with the cur val, by curSum - max_positive_carryover
		ex: [(-1,4,-1,2),-5,-2,1...] Min with cur val = -2 - 4 = -6;
	- compare with the Min so far, Min = -7 < -6; still -7;
	*/
	public static int minSubArray(List<Integer> nums) {
		if (nums == null || nums.size() == 0)
			return 0;
		int min = Integer.MAX_VALUE;
		int curSum = 0;
		// max positive sum from [0-i]; use to get rid of to calc Min with cur val.
		int maxPositiveCarryover = 0;
		for(int val : nums)
		{
			curSum += val;
			//compare Min so far with Min include cur val.
			min = Math.min(min, curSum - maxPositiveCarryover);
			maxPositiveCarryover = Math.max(maxPositiveCarryover, curSum); //check larger carryover
		}
		return min;
	}

	public static void main(String[] args){
    	List<Integer> nums = new ArrayList<Integer>(Arrays.asList(-2,2,-3,4,-4,2,1,-5,3));
    	System.out.println(minSubArray(nums));
    }
}