/**
43. Maximum Subarray III
Given an array of integers and a number k, find k non-overlapping subarrays which have the largest sum.

The number in each subarray should be contiguous.

Return the largest sum.

Example
Given [-1,4,-2,3,-2,3], k=2, return 8

Notice
The subarray should contain at least one number
*/

import java.util.*;
import java.io.*;

public class MaxSubarrayIII {
    /**
     * @param nums: A list of integers
     * @param k: An integer denote to find k non-overlapping subarrays
     * @return: An integer denote the sum of max k non-overlapping subarrays
     * Solution: 
     * - Use memorization/Dynamic Programming, store the prev K Maxes at each positions
     *	 to globalMax array.
     * - Compute the max of cur K at this pos, store in localMax array.
     *   By MAX(cur K max at pos, prev K max at pos), add the result max to cur val.
     *   memorize in localMax array.
     * - check if newly computed Max > cur Max in globalMax. Update accordingly.
     * - Repeat until you get the last glabalMax[k][len].
	 * ex: [1,-3,5,2,-1,2]
	 * LocalMax
	 * [[0, 0, 0, 0, 0, 0, 0], [-2147483648, 1, -2, 5, 7, 6, 8], [0, -2147483648, -2, 6, 8, 7, 9], [0, 0, -2147483648, 3, 8, 7, 10]]
	 * GlobalMax
	 * [[0, 0, 0, 0, 0, 0, 0], [0, 1, 1, 5, 7, 7, 8], [0, 0, -2, 6, 8, 8, 9], [0, 0, 0, 3, 8, 8, 10]]
	 * Time: O(K*N); Space: O(K+N);
     */
    public static int maxSubArray(int[] nums, int k) {
        //nums.len must >= k, need atleast k nums.
        if(nums.length < k)
        	return 0;
        int len = nums.length;
        //store cur k maxes that includes cur pos val. extra space for storing MIN.
        int [][] localMax = new int[k+1][len+1];
        //store all maxes for each k. Extra arr for comparing 0s.
        int [][] globalMax = new int[k+1][len+1];

        for(int i=1; i<=k; i++)
        {
        	localMax[i][i-1] = Integer.MIN_VALUE; //no max yet.
        	//ex: for k=3, need atleast k pos nums to get the ans.
        	for(int j=i; j<=len; j++)
        	{
        		//compute and memorize cur k max that include cur val. By comparing prev k max to cur k max at prev pos. Then add the cur val.
        		localMax[i][j] = Math.max(globalMax[i-1][j-1], localMax[i][j-1]) + nums[j-1];
        		if(i==j) //init, first max at k. 
        			globalMax[i][j] = localMax[i][j];
        		else //compare to prev Max, update if new val > max
        			globalMax[i][j] = Math.max(globalMax[i][j-1], localMax[i][j]);
        	}
        }
        return globalMax[k][len];
    }
    
    public static void main(String[] args) {
    	int [] nums = {1, -3, 5, 2, -1, 2};
    	int k = 3;
    	int ans = maxSubArray(nums, k);
    	System.out.println(ans);
    }
}