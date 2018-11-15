/**
Description
Given an array nums of integers and an int k, partition the array (i.e move the elements in "nums") such that:

All elements < k are moved to the left
All elements >= k are moved to the right
Return the partitioning index, i.e the first index i nums[i] >= k.

You should do really partition in array nums instead of just counting the numbers of integers smaller than k.

If all elements in nums are smaller than k, then return nums.length

Example
If nums = [3,2,2,1] and k=2, a valid answer is 1.

Challenge
Can you partition the array in-place and in O(n)?
*/
import java.util.*;
import java.io.*;

public class PartitionArray {
    /**
     * @param nums: The integer array you should partition
     * @param k: An integer
     * @return: The index after partition
	 * Time: O(n); Space: O(1);
     */
    public static int partitionArray(int[] nums, int k) {
    	if(nums == null || nums.length == 0)
            return 0;
        int i=0, j= nums.length-1;
        while(i<=j)
        {
        	//until i is 1 passed the elm < k. if all < k, then i is at last pos, so i+1 = len.
            while(i<=j && nums[i]<k)
                i++;
            while(i<=j && nums[j]>=k)
                j--;
            if(i<=j)
            {
	            int temp = nums[i];
	            nums[i] = nums[j];
	            nums[j] = temp;
	            i++;
	            j--;
	        }
        }
        return i;
    }

    public static void main(String[] args) {
    	int [] nums = {9,9,9,8,9,8,7,9,8,8,8,9,8,9,8,8,6,9};
    	int k = 9;
    	int ans = partitionArray(nums, k);
    	System.out.println(Arrays.toString(nums));
    	System.out.println(ans);
    }
}