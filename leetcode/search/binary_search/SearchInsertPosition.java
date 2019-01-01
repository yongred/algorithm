/**
35. Search Insert Position
Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You may assume no duplicates in the array.

Example 1:

Input: [1,3,5,6], 5
Output: 2
Example 2:

Input: [1,3,5,6], 2
Output: 1
Example 3:

Input: [1,3,5,6], 7
Output: 4
Example 4:

Input: [1,3,5,6], 0
Output: 0
*/
/**
Solution: Binary Search, handle not found consitions.
How to Arrive:
* If target is in arr, It will be returned in binary search.
```
while (start <= end) {
	int mid = start + (end - start) / 2;
	if (nums[mid] == target) {
		return mid;
	} else if (target < nums[mid]) {
		end = mid - 1;
	} else {
		start = mid + 1;
	}
}
```
* If not returned, means not in arr. 
	* Ex: out of bounce, [5,6] t=7 or t=4; 
	* in case t=7, we need index of (6) 1+1 = index 2; index after end. t > curMid;
		* Since t > curMid; means *start = mid+1 = 2 && end =1 * ;  We can return start; Or end+1;
	* in case t=4, we need same as index of (5) 0; t < curMid;
		* Since t < cuMid; *start = 0, end = -1* ; we can return start or end+1;
* Time: O(lgN)
* Space: O(1);
*/

class SearchInsertPosition {

  public int searchInsert(int[] nums, int target) {
    // no elms, insert at 0;
    if (nums.length == 0) {
      return 0;
    }
    int start = 0;
    int end = nums.length - 1;
    while (start <= end) {
      // mid is left last.
      int mid = start + (end - start) / 2;
      if (nums[mid] == target) {
        return mid;
      } else if (target < nums[mid]) {
        end = mid - 1;
      } else {
        // target > curNum
        start = mid + 1;
      }
    }
    // start out of bounce, [5,6] t=7 or t=4; 
    // t=7 start=2 mid(1)+1; t=4 start=0 = lastStart(0), end= -1;
    return start;
  }
}