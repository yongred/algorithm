/**
Merge Two Sorted Arrays: Merge two given sorted integer array A and B into a new sorted integer array.
*/

import java.util.*;
import java.io.*;

public class MergeTwoSortedArrays{
	
	/*
	 * O(A+B) time, O(A+B) space.
   * @param A: sorted integer array A
   * @param B: sorted integer array B
   * @return: A new sorted integer array
   */
  public static int[] mergeSortedArray(int[] A, int[] B) {
    int [] res = new int[A.length + B.length];
    int a_ind = 0;
    int b_ind = 0;

    for(int i=0; i< res.length; i++){
    	if(a_ind < A.length && b_ind < B.length){
    		res[i] = (A[a_ind] <= B[b_ind]) ? A[a_ind++] : B[b_ind++];
    	}else if(a_ind < A.length){
    		res[i] = A[a_ind++];
    	}else if(b_ind < B.length){
    		res[i] = B[b_ind++];
    	}
    }

    return res;
  }

  public static int [] randomArray(int size, int max){
		int [] arr = new int[size];
		Random rand = new Random();
		for(int i= 0; i< size; i++){
			arr[i] = rand.nextInt(max);
		}
		return arr;
	}

  public static void main(String [] args){
		Scanner reader = new Scanner(System.in);

		System.out.println("size1: ");
		int size1 = reader.nextInt();
		System.out.println("maxval1: ");
		int maxval1 = reader.nextInt();
		int [] arr1 = randomArray(size1, maxval1);
		Arrays.sort(arr1);

		System.out.println("size2: ");
		int size2 = reader.nextInt();
		System.out.println("maxval2: ");
		int maxval2 = reader.nextInt();
		int [] arr2 = randomArray(size2, maxval2);
		Arrays.sort(arr2);

		int [] res = mergeSortedArray(arr1, arr2);
		System.out.println(Arrays.toString(res));

	}

}