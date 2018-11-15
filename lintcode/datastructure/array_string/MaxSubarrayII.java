/**
Given an array of integers, find two non-overlapping subarrays which have the largest sum.
The number in each subarray should be contiguous.
Return the largest sum.

Example
For given [1, 3, -1, 2, -1, 2], the two subarrays are [1, 3] and [2, -1, 2] or [1, 3, -1, 2] and [2], they both have the largest sum 7.
*/

import java.util.*;
import java.io.*;

public class MaxSubarrayII {
    /**
     * @param nums: A list of integers
     * @return: An integer denotes the sum of max two non-overlapping subarrays
     * Solution: ex: [2,5,-1,-2,7,-3,0]
     * - find max at each pos from left to right. [2, 7, 7, 7, 11, 11, 11]
     * - find max at each pos from right to left. [11, 9, 7, 7, 7, 0, 0]
     * - find the middle cutoff by adding up the max combo at each pos. 
     *      ex: [(2)][(5,-1,-2,7),-3,0] = 2+9=11; [(2,5)][-1,-2,(7),-3,0] = 7+7=14 ...
     * - find the max out of all left[i] + right[i+1] combos.
     * Time: O(N); Space: O(N);
     */
    public static int maxTwoSubArrays(ArrayList<Integer> nums) {
        int size = nums.size();
        int [] left = new int[size];
        int [] right = new int[size];
        int max = Integer.MIN_VALUE, curSum = 0;
        //find max at each pos from left to right.
        for(int i=0; i< size; i++)
        {
            curSum += nums.get(i);
            if(curSum > max)
                max = curSum;
            if(curSum < 0)
                curSum = 0;
            left[i] = max;
        }
        //find max at each pos from right to left.
        max = Integer.MIN_VALUE;
        curSum = 0;
        for(int i = size-1; i>=0; i--)
        {
            curSum += nums.get(i);
            if(curSum > max)
                max = curSum;
            if(curSum < 0)
                curSum = 0;
            right[i] = max;
        }
        System.out.println(Arrays.toString(left));
        System.out.println(Arrays.toString(right));
        //find all combos [(2)][(5,-1,-2,7),-3,0] = 2+9=11; [(2,5)][-1,-2,(7),-3,0] = 7+7=14;
        max = Integer.MIN_VALUE;
        for(int i=0; i< size-1; i++)
        {
            max = Math.max(max, left[i] + right[i+1]);
        }
        return max;
    }

    public static void main(String[] args) {
        ArrayList<Integer> nums = new ArrayList<Integer>(Arrays.asList(2,5,-1,-2,7,-3,0));
        int ans = maxTwoSubArrays(nums);
        System.out.println(ans);
    }
}