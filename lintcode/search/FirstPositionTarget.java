/**
First Position of Target:
For a given sorted array (ascending order) and a target number, find the first index of this number in O(log n) time complexity.

If the target number does not exist in the array, return -1.

Example
If the array is [1, 2, 3, 3, 4, 5, 10], for given target 3, return 2.
*/

import java.io.*;
import java.util.*;

public class FirstPositionTarget{
	
	 /**
     * @param nums: The integer array.
     * @param target: Target to find.
     * @return: The first position of target. Position starts from 0.
     */
    public static int firstPositionSearch(int[] nums, int target) {
        int val_ind = binarySearch(nums, target, 0, nums.length - 1);
        //int val_ind = binarySearchIter(nums, target);
        if(val_ind == -1)
        	return -1;
        while(nums[val_ind] == target){
        	if(val_ind == 0)
        		return val_ind;
        	val_ind--;

        }
        return val_ind+1;
    }

    //Space O(lgN) time: O(lgN)
    public static int binarySearch(int [] nums, int target, int beg, int end){
    	if(end < beg)
    		return -1;
    	if(beg == end){
    		if(target == nums[beg])
    			return beg;
    		else
    			return -1;
    	}

    	int mid = (end + beg)/2;
    	System.out.println(beg + " " + end);
    	if(nums[mid] == target)
    		return mid;
    	else if(target < nums[mid]) //target on the left
    		return binarySearch(nums, target, beg, mid-1);
    	else // target > nums[mid], on the right
    		return binarySearch(nums, target, mid+1, end);
    	
    }

    /*
    Space: O(1) Time: O(lgN)
    modify binarySearch to 
	end = mid when find the val;
	so it will search [beg - mid(finded)]
	ex: 1,2,3,3,[3],4,5,6,7
	 find in  1,2,[3],3,3
	 find in 1,[2],3 -> right
	 find int [3] -> return nums[start];
	 */

     public static int firstPositionSearch2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = (end + start) / 2;
            System.out.println(start + " " + end);
            if (nums[mid] == target) {
            	//make end = mid, so we can find val == target on the leftside.
                end = mid;
            } else if (nums[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        
        //only when the start_ind is == target, then we can confirm the leftmost val is found.
        if (nums[start] == target) {
            return start;
        }
        
        return -1;
    }

    public static void main(String[] args){
		int [] nums = {1,2,3,3,3,6,7,8,9,10};
		System.out.println("ans: " + firstPositionSearch(nums, 3));
		System.out.println("ans: " + firstPositionSearch2(nums, 3));
		
	}


}