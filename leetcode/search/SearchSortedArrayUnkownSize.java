/**
702. Search in a Sorted Array of Unknown Size
Given an integer array sorted in ascending order, write a function to search target in nums. If target exists, then return its index, otherwise return -1. However, the array size is unknown to you. You may only access the array using an ArrayReader interface, where ArrayReader.get(k) returns the element of the array at index k (0-indexed).

You may assume all integers in the array are less than 10000, and if you access the array out of bounds, ArrayReader.get will return 2147483647.

Example 1:

Input: array = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in nums and its index is 4
Example 2:

Input: array = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: 2 does not exist in nums so return -1
Note:

You may assume that all elements in the array are unique.
The value of each element in the array will be in the range [-9999, 9999].
*/

/**
Solution: use end * 2 to find len, LgN time.
How to Arrive:
* Array is sorted, so it should be binary search.
* Missing len, can't find mid.
* need to find len first. But instead of linear approach, we can do end *= 2; Takes LgN time.
	* Ex: 1,2,4,8,16,32...n; 2^k = n; O(lgN);
```
(reader.get(end) != Integer.MAX_VALUE) end *= 2;
```
* Notice we don't need to go all the way end, we just need to go larger than Target. We can shorten the search.
```
(reader.get(end) != Integer.MAX_VALUE && reader.get(end) < target)
```
* We can shorten it again, since we know last end (curEnd/2) is < target. So we can use it as our StartIndex. 
* Search Start -> end using BinarySearch.
* Notice: if list.get(index) is OutOfBounce, we should go right. 
* Since OutOfBounce returns MAX_VALUE, we don't have to change anything b/c target < list.get(mid) is already going right.
*
*
* Time: O(lgN)
* Space: O(1); using Iterative.
*/

import java.util.*;
import java.io.*;

public class SearchSortedArrayUnkownSize {

	public class ArrayReader {
		private int[] nums;
		public ArrayReader (int[] nums) {
			this.nums = nums;
		}

		public int get(int index) {
			try {
				return this.nums[index];
			} catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
	}

	// cannot use size or length function.
	public int search(ArrayReader reader, int target) {
		int start = 0;
		int end = 1;
		// use exponential to use as len, lgN time.
		// 2^k = n; 1,2,4,8,16,32... n;
		while (reader.get(end) != Integer.MAX_VALUE && reader.get(end) < target) {
			start = end;
			end *= 2;
		}
		return binarySearchIter(reader, target, start, end);
	}

	public int binarySearch(ArrayReader reader, int target, int start, int end) {
		if (start > end) {
			return -1;
		}
		int mid = (start + end) / 2;
		if (reader.get(mid) == target) {
			return mid;
		} else if (target < reader.get(mid)) {
			// out of bounce indexs will return MAX, so just target < get(mid) is fine.
			return binarySearch(reader, target, start, mid - 1);
		} else {
			return binarySearch(reader, target, mid + 1, end);
		}
	}

	public int binarySearchIter(ArrayReader reader, int target, int start, int end) {
		while (start <= end) {
			int mid = (start + end) / 2;
			if (reader.get(mid) == target) {
				return mid;
			} else if (target < reader.get(mid)) {
				// out of bounce indexs will return MAX, so just target < get(mid) is fine.
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}

		return -1;
	}

	public static void main(String[] args) {
		SearchSortedArrayUnkownSize obj = new SearchSortedArrayUnkownSize();
		int[] nums = {-1, 0, 3, 5, 9, 12};
		ArrayReader reader = obj.new ArrayReader(nums);
		int target = 12;
		int res = obj.search(reader, target);
		System.out.println("ans: index " + res);
	}
}