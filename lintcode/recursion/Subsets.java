/**
Subsets:
Given a set of distinct integers, return all possible subsets.

Example
If S = [1,2,3], a solution is:

[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]

*/

import java.io.*;
import java.util.*;

public class Subsets{
	/*
     * @param nums: A set of numbers
     * @return: A list of lists
     Solution 1:  ex: [1,2,3];
     start(0) [] -> index(0) [1] -> index(1) [1, 2] -> index(2) [1,3], [1,2,3];
	 start(1) ->index(1) [2] -> index(2) [2,3];
	 start(2) ->index(2) [3];

	 Run time: O(n^2); b/c  start(1) -> n, start(2) -> n ... start(n) -> n;
	 	meaning: n + n-1 + n-2 + n-3 ... 1;  == (n(n+1))/2 == n^2 runtime;

	 Space: O(2n^2) = O(n^2);
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<List<Integer>> current = new ArrayList<List<Integer>>();
        //current.add(Arrays.asList(nums[0]));
        subsetsHelper(nums, 0, 0, result, current);
        result.add(new ArrayList<Integer>());  //add empty set
        return result;
    }

    public static void subsetsHelper(int[] nums, int start, int index, List<List<Integer>> result, List<List<Integer>> current){
    	//System.out.println( start + " " + index + " " + nums.length);
    	if(start >= nums.length){
    		return;
    	}
    	//loop through all already
    	if(index >= nums.length){
    		result.addAll(current);
    		current = new ArrayList<List<Integer>>();
    		subsetsHelper(nums, start+1, start+1, result, current);
    	}else{
    		if(current.size() == 0){ //[]
    			current.add(new ArrayList<Integer>() ); //[[]]
    			current.get(0).add(nums[start]); //[[1]]
    		}
			List<List<Integer>> tempcur = new ArrayList<List<Integer>>();
    		for(List<Integer> list : current){
    			if(index == start)  //already been added
    				continue;
    			//[1], [1,2] => [1,3], [1,2,3]
	    		ArrayList<Integer> temp = new ArrayList<Integer>(list);
	    		temp.add(nums[index]);
	    		tempcur.add(temp);
	    		//System.out.println( Arrays.toString(tempcur.toArray()) );
	    	}
	    	current.addAll(tempcur);
    		
	    	subsetsHelper(nums, start, index+1, result, current);
    	}

    	
    }

    /*
	Solution 2: iteration only;
	Runtime: T(n * 2(n)) = O(n^2);
	Space: O(n Lg n); b/c  current is emptied before iteration;
    */
	public static List<List<Integer>> subsets2(int[] nums) {
		if(nums == null)
			return null;
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		for(int i=0; i< nums.length; i++){  //T(n)
			List<List<Integer>> current = new ArrayList<List<Integer>>();

			//get sets that are already in result; ex: [1] [1,2] [2]
			for (List<Integer> list : result) { //T(n)
				current.add(new ArrayList<Integer>(list));
			}
	 		
			//add nums[i] to existing sets; ex: [1,3], [1,2,3], [2,3]
			for (List<Integer> list : current) { //T(n)
				list.add(nums[i]);
			}

	 
			//add nums[i] only as a set; ex: cur +[3] = [1,3], [1,2,3], [2,3], [3]
			ArrayList<Integer> single = new ArrayList<Integer>();
			single.add(nums[i]);
			current.add(single);
	 		
			result.addAll(current);
		}

		//add empty set
		result.add(new ArrayList<Integer>());

		return result;
 
	}


    public static void main(String [] args){
		int [] arr = { 1, 2, 3};

		System.out.println( Arrays.toString(subsets2(arr).toArray()) );

	}
}