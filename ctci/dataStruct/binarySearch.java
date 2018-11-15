package dataStruct;

import java.util.Arrays;
import java.util.Scanner;

/**
 * binary search requires data to be sorted first, it compares the target to
 * the middle value of the data, depends on whether it is larger or smaller than the 
 * middle, get rids of the other part. with eliminating data half each time
 * the O(logN)
 * @author YongLiu
 *
 */
public class binarySearch {
	public void binarySch(int value, int lowIndex, int hiIndex, int[] arr){
		int mid = (hiIndex + lowIndex) /2;
		if(hiIndex <lowIndex)
			System.out.println("not found");
		else if(arr[mid] == value)
			System.out.println("found "+ value + " at index " +mid);
		else if(value > arr[mid])
			binarySch(value, mid +1, hiIndex, arr);
		else if(value < arr[mid])
			binarySch(value, lowIndex, mid-1, arr);
	}
	
	public static void main(String [] args){
		Scanner input = new Scanner(System.in);
		QuickSort qs = new QuickSort();
		binarySearch bs = new binarySearch();
		int [] myInts = {10,30,80,0,40,70,50,60,20};
		System.out.println("before Sort");
		for(int i: myInts){
			System.out.print(i + " ");
		}
		System.out.println();
		qs.quickSort(myInts, 0, myInts.length-1);
		System.out.println("After Sort");
		for(int i: myInts){
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println("guess a number");
		int guess = input.nextInt();
		bs.binarySch(guess, 0, myInts.length-1, myInts);
	}
}
