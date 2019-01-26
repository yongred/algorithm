/**
Find Case Combinations of a String

Leetcode 相似问题
• Leetcode #46 Permutation and Leetcode #47 Permutation II.
• Leetcode #78 Subsets and Leetcode #90 Subsets II.
Method that gives all permutations of a string. Involving examination of subsets.

https://www.glassdoor.com/Interview/1-find-all-the-combinations-of-a-string-in-lowercase-and-
uppercase-For-example-string-ab-and-gt-ab-Ab-aB-AB-QTN_582954.htm

Find all the combinations of a string in lowercase and uppercase. 
For example, string "ab" >>> "ab", "Ab", "aB", "AB". 
So, you will have 2^n (n = number of chars in the string) output strings. 
The goal is for you to test each of these strings and see if it matchs a hidden string.
*/

/**
abc:
abc, aBc, abC, aBC;
Abc, ABc, AbC, ABC;

Questions: 
is it all unique chars? 
is input all lowercase?
does order of outputs matters?
*/

import java.io.*;
import java.util.*;

public class FindCaseCombinationsString {

	/**
	 * Solution 1: Recursive, DFS/Backtracking;
	 * Time: O(2^n) 2 cases for each char.
	 * Space: O(n) recursive until start=len;
	 */
	public static void caseCombos(String str) {
		helper(str.toCharArray(), 0);
	}

	public static void helper(char[] arr, int start) {
		if (start >= arr.length) {
			System.out.println(new String(arr));
			return;
		}
		// uppercase.
		arr[start] = Character.toUpperCase(arr[start]);
		helper(arr, start + 1);
		// lowercase;
		arr[start] = Character.toLowerCase(arr[start]);
		helper(arr, start + 1);
	}

	/**
	 * Solution 2: Iterative, Using bit to determine if curLetter is upper or lower.
	 * Time: (2^n)
	 * Space: O(n);
	 */
	public static void caseCombosIter(String str) {
		if (str == null || str.length() == 0) {
			return;
		}
		// loop str as charArr
		char[] arr = str.toCharArray();
		int n = arr.length;
		// 2^n total strs outputs.
		for (int i = 0; i < (1 << n); i++) {
			// each pos has half upper, half lower; 
			// in the order of 2^0 lower, 2^0 upper -> 2^1, 2^2, 2^3 lower and upper;
			// Ex; abc, aBc, abC, aBC;  
			//     Abc, ABc, AbC, ABC; aAaAaAaA, bbBBbbBB, ccccCCCC
			char[] curArr = new char[n];
			for (int j = 0; j < n; j++) {
				// 2^0, (0L,1U); 2^1 (0L,1L,2U,3U); 2^2 (0L,1L,2L,3L, 4U,5U,6U,7U);
				// idx0 char Lo or Up is determine by 0th bit, idx1 char is determined by 1st bit.
				// idx2 char Lo or Up is determine by 2nd bit. 
				// Conclude: to determine if n comboStr's ith char is Lo or Up, check if n's ith bit is 1 or 0;
				curArr[j] = isBitSet(i, j) ? Character.toUpperCase(arr[j]) : Character.toLowerCase(arr[j]);
			}
			System.out.println(new String(curArr));
		}
	}

	static boolean isBitSet(int n, int ith) {
		// determine if ith bit is 0 or not; 0 for lowercase, not0 for upper;
    return (n & 1 << ith) != 0;
  }

	public static void main(String[] args) {
		String str = "abcd";
		caseCombos(str);
		System.out.println("iter-----------------");
		caseCombosIter(str);
	}

}