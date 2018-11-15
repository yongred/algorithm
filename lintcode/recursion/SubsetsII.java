/**
Subsets II:
Given a list of numbers that may has duplicate numbers, return all possible subsets

Example
If S = [1,2,2], a solution is:

[
  [2],
  [1],
  [1,2,2],
  [2,2],
  [1,2],
  []
]
*/

import java.io.*;
import java.util.*;

public class SubsetsII{
	 /*
     * @param nums: A set of numbers.
     * @return: A list of lists. All valid subsets.
     * Solution: vertically and horizontally go through all the vals. And skip if last element is same as current.
     * if S= [1,1,2,2,3]
     * ex: [] -> [1] -> [1,1] -> [1,1,2] -> [1,1,2,2] -> [1,1,2,2,3]
                                         -> [1,1,2,3]
     						  -> [1,1,2]skip 
     						  -> [1,1,3]

     				 -> [1,2] -> [1,2,2] -> [1,2,2,3]
                              -> [1,2,3]

     				 -> [1,2]skip
     				 -> [1,3] 
     				 		  	 
     		  -> [1] skip

     		  -> [2] -> [2,2] -> [2,2,3]
     		  		 -> [2,3]
			  -> [2] skip
     		  -> [3]
     */
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        ArrayList<Integer> cur_list = new ArrayList<Integer>();
        //sort for easy skip duplicates.
        Arrays.sort(nums);
        helper(result, cur_list, nums, 0);
        return result;
    }

     /*
     * @param nums: A set of numbers.
     * 		  result: final result double list.
     *  	  cur_list: ArrayList, for current iteration.
     * 		  pos: the layer index.
     * @return: void
     *
     * 
     */
    public static void helper(List<List<Integer>> result, ArrayList<Integer> cur_list, int[] nums, int pos){
    	//add list first, ex: [] -> [1], [2], [3]
    	result.add(new ArrayList<Integer>(cur_list));
    	//pos is the start index, the root. (horizontally)
    	for(int i = pos; i < nums.length; i++){
    		if(i != pos && nums[i] == nums[i-1]){
    			continue;
    		}
    		//current state add this val, ex: S=[1,1,2,3]; [1,1] -> [1,1,2]
    		cur_list.add(nums[i]);
    		//go one layer down, (vertically)
    		helper(result, cur_list, nums, i+1);
    		//go back to this base state, ex: [1,1,2] -> [1,1]
    		cur_list.remove(cur_list.size() - 1);
    	}
    }

    public static void main(String [] args){
		int [] arr = { 1, 2, 3, 2, 1};

		System.out.println( Arrays.toString(subsetsWithDup(arr).toArray()) );

	}

}