/**
Find Median in Large Integer File of Integers

Find the median from a large file of integers. You can not access the numbers by index, can only access it sequentially (0->n). And the numbers cannot fit in memory.
*/

/**
median = mid of odd; [1,2,(3),4,5]
median = (mid1 + mid2) / 2; of even. [1,2,(3,4),5,6]
file is not sorted.

Ex: [4,6,0,2,8]; Med= 4;
findKth(3): guess= (-2^31 + 2^31)/2 = 0; largest= -2^31p, (0);
	count=1, 0< 3, go right, (0+1 > 0) leftB= 1 (3, [1->2^32]);
findKth(3, [1->2^32]): guess= (1 + 2^32) / 2= 2^16; largest= 1-,4-,6-, (8);
  count=5; 5> 3; go left, rightB= 8 (3, [1->8]);
findKth(3, [1->8]): guess= (1 + 8)/2 = 4; largest= 1-, (4);
	count=3; 3=3; return 4; DONE;

Ex: [0,0,-2,0];
findKth(2): guess= (-2^31 + 2^31)/2 = 0; largest= -2^31p, (0);
	count=4, 4 > 2, go left, rightB= 0; (2, [-2^31->0])
findKth(2, [-2^31->0]): guess= (-2^31 + 0)/2 = -2^15; largest= -2^31;
	count=0; 0 < 2, go right, (-2^15 > -2^31 +1) leftB= -2^15 (2, [-2^15-> 0])
findKth(2, [-2^15-> 0]): guess= (-2^15 + 0)/2 = -2^7; largest= -2^15;
	count=0; 0< 2, go right, (-2^7 > -2^15+1) leftB= -2^7; （2，[-2^7-> 0])
findKth(2，[-2^7-> 0]): guess= (-2^7 + 0)/2 = -2^3; largest= -2^7;
	count=0; 0< 2, go right, (-2^3 > -2^7+1) leftB= -2^3; （2，[-2^3-> 0])
findKth(2，[-2^3-> 0]): guess= (-8 + 0)/2 = -4; largest= -8;
	count=0; 0< 2, go right, (-4 > -8) leftB= -4; （2，[-4 -> 0])
findKth(2，[-4 -> 0]): guess= (-4 + 0)/2 = -2; largest= -4;
	count=0; 0< 2, go right, (-2 > -4) leftB= -4; （2，[-2 -> 0])
findKth(2，[-4 -> 0]): guess= (-2 + 0)/2 = -1; largest= -2;
	count=1; 1< 2, go right, (-1 > -2) leftB= -1; （2，[-1 -> 0])
findKth(2, [-1 -> 0]): guess= (-1 + 0)/2 = 0; largest= -1, (0);
	count=3; 3> 2, go left, rightB= 0; (2, 0->0);
findKth(2, 0->0): 0=0 return 0;

Solution: Binary Search between int.MIN and int.MAX; Count the smaller and larger #s in nums;
How to Arrive:
* Question explaination:
	* We do not have access to nums directly through indexes. (assume we cannot know the vals of nums);
	* Find the median even though we don't know any val of the nums.
	* We can only access nums sequentially, loop from 1->n;
* So, we have to Guess the number until we guess the right num for the median.
* What we know is that nums are Integers, So int.MIN and int.MAX is our Boundaries.
* We also know: 
	* median = mid of odd, [1,2,(3),4,5]; 
	* median = (mid1 + mid2) / 2; of even. [1,2,(3,4),5,6]
* The nums are Not Sorted. So we have to find a way to eliminates the Impossible nums.
* We need the len of nums, in order to get median (kth) num.
* So first we count nums and find the len; (we can only access nums sequentially);
* Knowing the len, we can check whether nums are even or odd.
* Odd: we are finding the (len/2 + 1)th num in nums as Median.
* Even: we find ((len/2)th num + (len/2 + 1)th num) / 2 = Median;
* So we will implement a findKth() function. We initially pass in int.MIN and int.MAX as our boundaries.
	* Base: when leftbound = rightbound, only 1 num left, return leftbound;
	* Recursive:
		* We guess the mid num of the leftbound and rightbound; leftbound + (rightbound - leftbound) / 2;
		* Now we will count how many numbers in nums are <= guess; And keep a maxNum <= guess (largestSmaller);
		* if smallerOrEqualCount == k: means maxNum in nums <= guess is the Kth num we are looking for.
		* if smallerOrEqualCount < k: means Guess is too small, tar is on the rightside. 
		* So, we eliminates leftside. leftBound = guess+1; B/c we know tar > guess, and we know guess >= largestSmaller. So tar > largestSmaller (maxNum that <= guess); findKth(k, [guess+1 -> rightbound]);
		* if smallerOrEqualCount > k: means Guess is <= tar; leftside (MIN -> largestSmaller) is still possible, eliminates rightside; findKth(k, [leftbound -> largestSmaller]);
		* Notice: we only narrow down our guesses, but did not resize the nums array, So K is always the same.
		
* Time: O(32 * nums) = O(n); Integers are 2^32 numbers, we elminates half each time. 32 times scanning/counting the nums Array.
* Space: O(1);
*/

import java.util.*;
import java.io.*;

public class FindMedianLargeIntegerFileOfIntegers {
	
	/**
	 * Solution: Binary Search between int.MIN and int.MAX; Count the smaller and larger #s in nums;
	 * Time: O(32 * nums) = O(n); Integers are 2^32 numbers, we elminates half each time. 32 times scanning/counting the nums Array.
	 * Space: O(1);
	 */
	public static double findMedian(int[] nums) {
		// nums are in a file, don't have access to len, don't have indexes.
		// but we need the len to find median.
		int len = 0;
		// sequential, 1 by 1, count len
		for (int num : nums) {
			len++;
		}
		// median of odd is mid, len=5, 5/2 = 2 + 1 = 3; [1,2,(3),4,5]
		if (len % 2 != 0) {
			return (double) findKth(nums, len / 2 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
		} else {
			// median of even is (mid1 + mid2) / 2; len=6, 6/2= 3, 6/2+1= 4; [1,2,(3,4),5,6]
			return (double) (findKth(nums, len / 2, Integer.MIN_VALUE, Integer.MAX_VALUE)
					+ findKth(nums, len / 2 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE)) / 2;
		}

	}

	public static long findKth(int[] nums, int k, long leftbound, long rightbound) {
		// find kth
		if (leftbound >= rightbound) {
			return leftbound;
		}
		// guess the num in mid.
		long guess = leftbound + (rightbound - leftbound) / 2;
		// count the nums <= guess.
		int smallerCount = 0;
		// find the largest num <= guess.
		long largestSmaller = leftbound;

		for (int num : nums) {
			if (num <= guess) {
				smallerCount++;
				largestSmaller = Math.max(largestSmaller, num);
			}
		}
		// binary search
		if (smallerCount == k) {
			return largestSmaller;
		} else if (smallerCount < k) {
			// guess should be larger, go right
			// eliminates leftside. guess+1; we know tar > guess, and we know guess >= largestSmaller.
			return findKth(nums, k, guess + 1, rightbound);
		} else {
			// count > k, guess should be smaller, go left
			return findKth(nums, k, leftbound, largestSmaller);
		}
	}


	public static void main(String[] args) {
		int[][] cases = new int[][] {{0,0,-2,-1}, {4,6,0,2,8}, {-23, 9, -32, 4, 5, -40}, {1,2,3,4,5,6,7},
			{6,5,4,3,2,1}, {99}, {-3,2}, {-2}};
		for (int[] arr : cases) {
			System.out.println("median of: " + Arrays.toString(arr));
			System.out.println("median: " + findMedian(arr));
		}
	}
}