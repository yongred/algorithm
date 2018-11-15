/**
8.3 Magic Index: A magic index in an array A [e ... n -1] is defined to be an index such that A[i] =i. 
Given a sorted array of distinct integers, write a method to find a magic index, if one exists, in
array A.
FOLLOW UP
What if the values are not distinct?
Hints: # 170, #204, #240, #286, #340
*/

import java.util.*;

public class MagicIndex{

	public int findMI(int [] arr){
		return findMI(arr, 0, arr.length-1);
	}

	public int findMI(int [] arr, int fst, int end){
		int mid = (int)Math.floor((fst + end) / 2);
		if(mid == arr[mid])
			return mid;
		else if(mid < arr[mid])
			return findMI(arr, fst, mid);
		else
			return findMI(arr, mid+1, end);
	}

	public static void main(String [] args){
		MagicIndex obj = new MagicIndex();
		int [] arr = {-3, -2, -1, 0, 1, 3, 5, 7, 13, 18};
		System.out.println(obj.findMI(arr));
	}
}