/**
45. Maximum Subarray Difference
Given an array with integers.

Find two non-overlapping subarrays A and B, which |SUM(A) - SUM(B)| is the largest.

Return the largest difference.

Example
For [1, 2, -3, 1], return 6.

Challenge
O(n) time and O(n) space.

Notice
The subarray should contain at least one number
*/

import java.util.*;
import java.io.*;

public class MaxSubarrayDiff {
    /**
     * @param nums: A list of integers
     * @return: An integer indicate the value of maximum difference between two substrings
     * Solution: similar to MaxSubarray II. We need to find Max Sum and Min sum.
     * ex: [1,2,-3,1]
     * Find Maxes and Mins from left->right. LeftMax [1,3,3,3], leftMin [1,1,-3,-3]. 
     * Find Maxes and Mins from right->left. rightMax [3,2,1,1], rightMin [-3,-3,-3,1].
     * Find LeftMaxDiff by leftMax[i] - rightMin[i+1]; [(1,2),(-3),1] = 3 - -3 = 6;
     * Find RightMaxDiff by j->0 rightMax[j] - leftMin[j-1]; [1,2,(-3),(1)] = 1 - -3 = 4;
     * ans = 6; Basically [Max, Min] or [Min, Max] cover both posibility.
     *
     * Time: T(3N) = O(N); Space: T(4N) = O(N)
     */
    public static int maxDiffSubArrays(int[] nums) {
        if(nums == null || nums.length < 2)
            return 0;

        int len = nums.length;
        int [] leftMax = new int[len];
        int [] leftMin = new int[len];
        int [] rightMax = new int[len];
        int [] rightMin = new int[len];
        int curMax = 0, curMin = 0;
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        //Find Maxes and Mins from left->right. LeftMax [1,3,3,3], leftMin [1,1,-3,-3].
        for(int i=0; i< len; i++)
        {
            curMax += nums[i];
            curMin += nums[i];
            if(curMax > max)
                max = curMax;
            if(curMin < min)
                min = curMin;
            if(curMax < 0)
                curMax = 0;
            if(curMin > 0)
                curMin = 0;
            leftMax[i] = max;
            leftMin[i] = min;
        }
        
        curMin = 0;
        curMax = 0;
        max = Integer.MIN_VALUE;
        min = Integer.MAX_VALUE;
        //Find Maxes and Mins from right->left. rightMax [3,2,1,1], rightMin [-3,-3,-3,1].
        for(int j=len-1; j>=0; j--)
        {
            curMax += nums[j];
            curMin += nums[j];
            if(curMax > max)
                max = curMax;
            if(curMin < min)
                min = curMin;
            if(curMax < 0)
                curMax = 0;
            if(curMin > 0)
                curMin = 0;
            rightMax[j] = max;
            rightMin[j] = min;
        }
        //calc LeftMaxDiffs and RightMaxDiffs, then find out which side has the Max. [Max, Min] or [Min, Max]
        int ans = Integer.MIN_VALUE;
        for(int i=0, j=len-1; i< len-1; i++, j--)
        {
            int leftDiff = leftMax[i] - rightMin[i+1];
            int rightDiff = rightMax[j] - leftMin[j-1];
            ans = Math.max(ans, Math.max(leftDiff, rightDiff));
        }
        
        return ans;
    }

    public static void main(String[] args) {
        int [] nums = {1, -3, 5, 2, -1, 2};
        int ans = maxDiffSubArrays(nums);
        System.out.println(ans);
    }
}