/**
Next Greater Element:
You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. Find all the next greater numbers for nums1's elements in the corresponding places of nums2.

The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. If it does not exist, output -1 for this number.

Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
Output: [-1,3,-1]
Explanation:
    For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
    For number 1 in the first array, the next greater number for it in the second array is 3.
    For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
Example 2:
Input: nums1 = [2,4], nums2 = [1,2,3,4].
Output: [3,-1]
Explanation:
    For number 2 in the first array, the next greater number for it in the second array is 3.
    For number 4 in the first array, there is no next greater number for it in the second array, so output -1.
Note:
All elements in nums1 and nums2 are unique.

*/

import java.util.*;
import java.io.*;

public class NextGreatElement{
	/*
	Brute Force Solution: 
	-Loop through nums1 and search every element in nums2;
	Time: O(n * m); Space: O(n)
	*/
	public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int [] res = new int[nums1.length];
        int index = 0; //res index
        //find nums1 elements one by one in nums2
        for(int i=0; i< nums1.length; i++){
        	int next = -1;
        	boolean locate = false;
        	for(int j=0; j< nums2.length; j++){
        		//if found nums1[i], and following nums2[j] is greater
        		if(locate && nums2[j] > nums1[i]) {
        			next = nums2[j];
        			break;
        		}
        		//found nums1[i] in nums2
        		if(nums1[i] == nums2[j]){
        			locate = true;
        		}

        	}
        	//store next greater element in res
        	res[index] = next;
        	index++;
        }

        return res;
    }


    /*
	Better solution: finding raise in decreasing order.
	ex: 7,6,8,3,2,1,5 => down[7->6] up(8), down[3->2->1] up(5)
	so 7 and 6 have NGE of 8, and 3,2,1 have NGE of 5.
	-Use Stack to pop previous smaller integers into a Map that matches them to the cur int (NGE).
	Time: O(n), Space: O(n)
    */
	public static int[] nextGreaterElement2(int[] nums1, int[] nums2) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Stack<Integer> stack = new Stack<Integer>();

		for(int i=0; i< nums2.length; i++){
			//check if cur int is larger than previous ints
			while(!stack.isEmpty() && nums2[i] > stack.peek() ){
				//ex: map down[3->2->1] to up(5);
				map.put(stack.pop(), nums2[i]);
			}
			//push cur int to check next value
			stack.push(nums2[i]);
		}

		for (int j=0; j< nums1.length; j++) {
			//either there is a match (NGE) stored, or -1
			nums1[j] = map.getOrDefault(nums1[j], -1);
		}

		return nums1;
	}


    public static void main(String [] args){
    	int [] nums1 = {4,1,2};
    	int [] nums2 = {1,3,4,2};
    	int [] res = nextGreaterElement2(nums1, nums2);

    	System.out.println(Arrays.toString(res));
    }
}



