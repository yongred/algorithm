/**
Collatz Conjecture
If a number is odd, the next transform is 3*n+1
If a number is even, the next transform is n/2
The number is finally transformed into 1.
The step is how many transforms needed for a number turned into 1.
Given an integer n, output the max steps of transform number in [1, n] into 1.
*/

/**
n = 12;
12/2 = 6; 6/2 = 3; 3 * 3 +1 = 10; 10/2 = 5; 5 * 3 + 1 = 16; 
16/2 = 8; 8/2 = 4; 4/2 = 2; 2/2=1; 9 steps;

Solution 1: Recursive + memorization; top-down
* Time: it is currently unknown if collatz even halts for all n; the claim that it does is known as Collatz conjecture. Therefore, no known method will work on this recurrence.
* Space: O(n)

Solution 2: bottom-up, DP. Count opes >= i, when cur < i, dp[i] = dp[cur] + count;
Ex:
13 → 40 → 20 → (10)already cached → 5 → 16 → 8 → 4 → 2 → 1
In the third iteration we hit the number 10, which is smaller than 13, and thus I have already calculated the sequence length for the number 10, so I can just add the length of the sequence I have already went through to get the total result.

13 takes 3 opes to get to 10; (10) count = 6; 3 + 6 = 9;

* Time: unknown if collatz even halts for all n; the claim that it does is known as Collatz conjecture. Therefore, no known method will work on this recurrence.
* Space: O(n)
*/

import java.io.*;
import java.util.*;

public class CollatzConjecture {

	/**
	 * Solution 1: Recursive + memorization; top-down
	 * Time: it is currently unknown if collatz even halts for all n; the claim that it does is known as Collatz conjecture. Therefore, no known method will work on this recurrence.
	 * Space: O(n)
	 */
	public static int maxSteps(int n) {
		HashMap<Integer, Integer> memo = new HashMap<>();
		int max = Integer.MIN_VALUE;
		for (int i = 1; i <= n; i++) {
			max = Math.max(max, steps(i, memo));
		}
		return max;
	}

	public static int steps(int n, HashMap<Integer, Integer> memo) {
		if (n == 1) {
			return 0;
		}
		if (memo.containsKey(n)) {
			return memo.get(n);
		}
		int steps = 0;
		if (n % 2 != 0) {
			steps = 1 + steps(3 * n + 1, memo);
		} else {
			steps = 1 + steps(n / 2, memo);
		}
		memo.put(n, steps);
		return steps;
	}

	/**
	 * Solution 2: bottom-up, DP. Count opes >= i, when cur < i, dp[i] = dp[cur] + count;
	 * Time: unknown if collatz even halts for all n; the claim that it does is known as Collatz conjecture. Therefore, no known method will work on this recurrence.
	 * Space: O(n)
	 */
	public static int maxSteps2(int n) {
		if (n <= 1) {
			return 0;
		}
		int max = 0;
		int[] cache = new int[n + 1];
		// Arrays.fill(cache, -1);
		// n=1, requires 0 step.
		cache[1] = 0;
		// loop from 2 to n;
		for (int i = 2; i <= n; i++) {
			// get the steps for i.
			int cur = i;
			int count = 0;
			// nums < i are calculated in cache. count # of opes before become < i;
			while (cur >= i) {
				if (cur % 2 != 0) {
					cur = cur * 3 + 1;
				} else {
					cur = cur / 2;
				}
				count++;
			}
			// cur is now < i; count is # of opes it takes to become cur. 
			// Add them with have the total opes of i;
			cache[i] = count + cache[cur];
			// check max
			max = Math.max(max, cache[i]);
		}

		return max;
	}



	public static void main(String[] args) {
		int n = 12;
		System.out.println("max steps " + maxSteps(n));
		System.out.println("max steps 2: " + maxSteps2(n));
	}


}