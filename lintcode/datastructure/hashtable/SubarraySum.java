/**

138. Subarray Sum
Given an integer array, find a subarray where the sum of numbers is zero. Your code should return the index of the first number and the index of the last number.

Example
Given [-3, 1, 2, -3, 4], return [0, 2] or [1, 3].

Notice
There is at least one subarray that it's sum equals to zero.

*/

import java.util.*;
import java.io.*;

public class SubarraySum {
    /**
     * @param nums: A list of integers
     * @return: A list of integers includes the index of the first number and the index of the last number
     * Solution:
     * When I see sub arrs sum, immediately think of Prefix Sums and hash.
     * Use prefix Sums to find the pattern.
     * 1. if prefixSum goes to 0, we find the answer. (case of sum from 0->pos == 0)
     *		ex: [2,1,-3,5]; prefSum = [2,3,0,5]; 
     * 2. When there is a repeated # in prefixSum.
     *		ex: [2,1,-2,3,-1] prefSum = [2,(3),1,4,(3)] ans = -2,3,-1 = 0;
     *		b/c when a sum repeats, it must means the middle part is == 0;
     * Time: O(N); space: O(N)
     */
    public List<Integer> subarraySum(int[] nums) {
        HashMap<Integer, Integer> prefixSum = new HashMap<Integer, Integer>();
        List<Integer> ans = new ArrayList<Integer>();
        int curSum = 0;
        
        for (int i=0; i< nums.length; i++)
        {
            curSum+= nums[i];
            //find Zero from 0->pos
            if(curSum == 0)
            {
                ans.add(0);
                ans.add(i);
                break;
            }
            else if(prefixSum.containsKey(curSum))
            {
                ans.add(prefixSum.get(curSum) + 1);
                ans.add(i);
                break;
            }
            prefixSum.put(curSum, i);
        }
        return ans;
    }
}