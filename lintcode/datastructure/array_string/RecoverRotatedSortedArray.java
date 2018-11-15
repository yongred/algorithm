/**
39. Recover Rotated Sorted Array
Given a rotated sorted array, recover it to sorted array in-place.

Example
[4, 5, 1, 2, 3] -> [1, 2, 3, 4, 5]

Challenge
In-place, O(1) extra space and O(n) time.

Clarification
What is rotated array?

For example, the orginal array is [1,2,3,4], The rotated array of it can be [1,2,3,4], [2,3,4,1], [3,4,1,2], [4,1,2,3]
*/
import java.util.*;
import java.io.*;

public class RecoverRotatedSortedArray {
    /**
     * @param nums: An integer array
     * @return: nothing
     * Time: O(N): find to k pos, then swap n-k times = N, then k(n-k);
     * Space: O(1)
     */
    public static void recoverRotatedSortedArray(List<Integer> nums) {
        int i=0;
        //find the last ascending num
        while(i+1 < nums.size() && nums.get(i) <= nums.get(i+1))
        {
        	i++;
        }
        //swap the target to every num after it. Do the same with nums before target.
        while(i>=0)
        {
        	int j=i;
        	//until j encounters the already sorted, >= n[j]
        	while(j < nums.size()-1 && nums.get(j) >= nums.get(j+1))
        	{
        		int temp = nums.get(j);
        		nums.set(j, nums.get(j+1));
        		nums.set(j+1, temp);
        		j++;
        	}
        	i--;
        }
    }

    /*
    reverse the list
    */
    public static void reverse(List<Integer> nums, int start, int end)
    {
    	for(int i=start, j=end; i<j; i++, j--)
    	{
    		int temp = nums.get(i);
    		nums.set(i, nums.get(j));
    		nums.set(j, temp);
    	}
    }

    /**
     * @param nums: An integer array
     * @return: nothing
     * reverse the rotated part, and reverse the normal part, then reverse the whole thing.
     * ex: [4,5,1,2,3] -> [5,4,1,2,3] -> [5,4,3,2,1] -> [1,2,3,4,5]
     * Time: O(N); Space: O(1);
     */
    public static void recoverRotatedSortedArray2(List<Integer> nums)
    {
    	for(int i=0; i< nums.size()-1; i++)
    	{
    		if(nums.get(i) > nums.get(i+1))
    		{
    			reverse(nums, 0, i); //reverse the rotated part
    			reverse(nums, i+1, nums.size()-1); //reverse the normal part
    			reverse(nums, 0, nums.size()-1); //reverse the whole thing
    		}
    	}
    }

    public static void main(String[] args) {
    	List<Integer> nums = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    	recoverRotatedSortedArray2(nums);
    	System.out.println(Arrays.toString(nums.toArray()));
    }
}