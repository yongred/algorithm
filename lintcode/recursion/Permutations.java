
/**
Permutations:
Given a list of numbers, return all possible permutations.

Example
For nums = [1,2,3], the permutations are:

[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]
*/

import java.io.*;
import java.util.*;

public class Permutations{
	
	/*
   	* @param nums: A list of integers.
   	* @return: A list of permutations.
   	* putting the next number in every possible position of the cur ones.
   	* ex: [1] => [2,1], [1,2] => [3,2,1] [2,3,1] [2,1,3] [3,1,2] [1,3,2] [1,2,3]
   	* Space: O(n^2); Time: (n * n!) b/c 7*6*5*4*3*2*1 each time we pick a position we goes to the next with one fewer position
  	 */
	public static List<List<Integer>> permute(int[] nums) {
	    List<List<Integer>> result = new ArrayList<List<Integer>>();
	    result.add(new ArrayList<Integer>());

	    //loop through all nums
	    for(int i=0; i< nums.length; i++){
	    	//current list to become result
	    	List<List<Integer>> current = new ArrayList<List<Integer>>();
	    	//loop through [1]; [2,1] [1,2]; [...]
	    	for(List<Integer> list : result){
	    		//possible positions is 1 more than the size of current list. ex: [2,1] => [*,2,*,1,*]; 3 possible positions
	    		for(int j=0; j< list.size()+1; j++){
	    			
	    			//add to j position; ex: [1].add(0,2) => [2,1]; [1].add(1,2) => [1,2] 
	    			list.add(j, nums[i]);
	    			//assign list to temp by value, so list reference wont be affected when change in current.
	    			ArrayList<Integer> temp = new ArrayList<Integer>(list);
	    			current.add(temp);
	    			//make sure to remove so it wont affect the loop
	    			list.remove(j);
	    		}
	    	}
	    	//result assign to newly permuted vals list
	    	result = new ArrayList<List<Integer>>(current);
	 	}

	 	return result;
  	}


  	public static void main(String [] args){
		int [] arr = { 5,4,2, 1, 3};

		System.out.println( Arrays.toString(permute(arr).toArray()) );

	}



}