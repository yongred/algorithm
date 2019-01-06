

/**
GeeksforGeeks
Count number of substrings
Given a string of lowercase alphabets, count all possible substrings (not necessarily distinct) that have exactly k distinct characters.

Input:

The first line of input contains a single integer T denoting the number of test cases. Then T test cases follow. Each test case consist of two lines. The first line of each test case consists of a string S.
The second line of each test case contains integer K.

Output:

Corresponding to each test case, in a new line, print the count all possible substrings that has exactly k distinct characters.

Constraints:

1 ≤ T ≤ 100
1 ≤ string length ≤ 1000
1 ≤ K ≤ 26

Example:

Input
1
aba
2

Output
3

*/

/*package whatever //do not write package name here */

import java.util.*;
import java.lang.*;
import java.io.*;

class CountNumberOfKSubstring {

	/**
	 * Solution: Nested loop generates all substr, Use hash[26] to count
	 * Time: O(n^2)
	 * Space: O(1)
	 */
	public static int countKSubstr(String str, int k) {
		int res = 0;
		int unique = 0;
		// use 2 for loop, generates all substrs,
		// use hash to store char count from startIndex.
		int[] winChars = new int[26];
		// from 0 -> n-k; [0,1,2,3,4] k=3 5-3=2(last)
		for (int i = 0; i <= str.length() - k; i++) {
			for (int j = i; j < str.length(); j++) {
				// count the char.
				winChars[str.charAt(j) - 'a']++;
				// count is 1 means unique.
				if (winChars[str.charAt(j) - 'a'] == 1) {
					unique++;
				}
				if (unique == k) {
					res++;
				}
				// System.out.println(str.substring(i, j + 1));
			}
			// reset
			Arrays.fill(winChars, 0);
			unique = 0;
		}
		return res;
	}

	public static void main (String[] args) {
		Scanner input = new Scanner(System.in);
		int cases = input.nextInt();
		while (cases > 0) {
			String str = input.next();
			int k = input.nextInt();
			System.out.println(countKSubstr(str, k));
			cases--;
		}
		input.close();
	}
}