/**
Permutations II:
Given a list of numbers with duplicate number in it. Find all unique permutations.

*/

import java.io.*;
import java.util.*;

public class PermutationsII{
	 /*
     * @param :  A list of integers
     * @return: A list of unique permutations
     * Solution 1: just use hashset, automatically remove duplicate when add
     * Space: O(n^2); Time: (n * n!)
     */

 	public static List<List<Integer>> permuteUnique(int[] num) {
		List<List<Integer>> returnList = new ArrayList<List<Integer>>();
		returnList.add(new ArrayList<Integer>());
	 
		for (int i = 0; i < num.length; i++) {
			Set<ArrayList<Integer>> currentSet = new HashSet<ArrayList<Integer>>();
			for (List<Integer> l : returnList) {
				for (int j = 0; j < l.size() + 1; j++) {
					l.add(j, num[i]);
					ArrayList<Integer> T = new ArrayList<Integer>(l);
					l.remove(j);
					currentSet.add(T);
				}
			}
			returnList = new ArrayList<List<Integer>>(currentSet);
		}
	 
		return returnList;
	}

	/*
	Solution2:
	use swap: ex: [1,2,3,4] => 
		1,2,[3,4] => 1,2,[4,3] 
		1,[2,3,4] => 1,[3,2,4] => 1,[3,4,2] => 1,[4,3,2] ...
		2,[1,3,4] => ...
	just as the definition of permutation

	then we check for dulplicate:
	-check if current start position is same as the position we are switching, if so skip this permutation and its child permutations, it will all be the same.
	ex: 1,2,1 if you switch pos0 and pos2, it will still be the same permutation.
	
	*/

	public static List<List<Integer>> permuteUnique2(int[] num) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		permuteUniqueHelper(num, 0, result);
		return result;
	}
	 
	public static void permuteUniqueHelper(int[] num, int start, List<List<Integer>> result) {
	 	
		if (start >= num.length ) {
			//swaped all position of the current start index, add that list to result
			ArrayList<Integer> item = convertArrayToList(num);
			System.out.println( Arrays.toString(item.toArray()) );
			result.add(item);
		}
	 
		for (int j = start; j <= num.length-1; j++) {
			//if start pos and j is duplicated, then we don't need to do the same permutations again.
			if (!containsDuplicate(num, start, j)) {
				//change start index, and permute.
				swap(num, start, j);
				permuteUniqueHelper(num, start + 1, result);
				//switch back to previous position
				swap(num, start, j);  

			}
		}
	}
	 
	public static ArrayList<Integer> convertArrayToList(int[] num) {
		ArrayList<Integer> item = new ArrayList<Integer>();
		for (int h = 0; h < num.length; h++) {
			item.add(num[h]);
		}
		return item;
	}
	 
	public static boolean containsDuplicate(int[] arr, int start, int end) {
		for (int i = start; i <= end-1; i++) {
			if (arr[i] == arr[end]) {
				return true;
			}
		}
		return false;
	}
	 
	public static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}



    public static void main(String [] args){
		int [] arr = { 1,2,1};

		//System.out.println( Arrays.toString(permuteUnique(arr).toArray()) );

		System.out.println( Arrays.toString(permuteUnique2(arr).toArray()) );

	}
}