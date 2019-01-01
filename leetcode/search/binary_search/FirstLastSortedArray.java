/**
34. Find First and Last Position of Element in Sorted Array
Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.

Your algorithm's runtime complexity must be in the order of O(log n).

If the target is not found in the array, return [-1, -1].

Example 1:

Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
Example 2:

Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]
*/

/**
Solution: Binary Search for first, then for last.
How to arrive:
* Repeated elms, 1st and last pos of target. 
* Problem: search [7,8,8,8,9] t=8 array like this, you must search both sides, unable to eliminates either side. When search both 1st 8 and last 8.
* To be able to eliminate one side, we can search first target first. Then last target.
* For 1st target, do regular binary search, except when midVal == target, we need to find the leftmost target. So, we cut off rightsides, with inclusive mid on left.
* Ex: [7,8,8,8,9] mid=2; cut off right = [7,8,8]; then [7,8], [8] return last one left. (startIndex); **use leftLast mid** [8,9] mid=0;
```
int mid = start + (end - start) / 2; int end = nums.length - 1;
```
* For last target, do regular binary search, except when midVal == target, we find rightmost target. So cut off leftside, with inclusive mid on right. **Use right1st mid** [8,9] m=1;
```
int mid = start + (end - start + 1) / 2; int end = nums.length - 1;
```
* Ex:  [7,8,8,8,9] mid=2; cutoff left, [8,8,9], then [8,9], [8] return last one left. (end index);
* Remember to check if last num left == target, if not return -1;
* return  [firstIndex, lastIndex];
* Time: O(lgN);
* Space: O(1);
*/

class FirstLastSortedArray {

	/**
	 * Solution: binary Search 2 times.
	 * Time: O(lgN);
	 * Space: O(1);
	 */
  public int[] searchRange(int[] nums, int target) {
    if (nums.length == 0) {
      return new int[] {-1, -1};
    }
    int[] res = new int[2];
    res[0] = findFirst(nums, target);
    res[1] = findLast(nums, target);
    return res;
  }
  
  public int findFirst(int[] nums, int target) {
    int start = 0;
    // len-1 for leftLast elm, last elm left will be leftmost target.
    int end = nums.length - 1;
    while (start < end) {
      // mid is leftLast elm. ex: [8,8] t=8, mid=0, 8=8 <-, ret 0[8];
      int mid = start + (end - start) / 2;
      if (nums[mid] == target) {
        // cut off right, inclusive mid. Find leftmost target.
        end = mid;
      } else if (nums[mid] > target) {
        // midVal too big, cut off right, exclude mid.
        end = mid - 1;
      } else {
        // midVal < target, on the right. exclude mid.
        start = mid + 1;
      }
    }
    // last one left is either the leftmost target, or not target -1.
    return (nums[start] == target) ? start : -1;
  }
  
  public int findLast(int[] nums, int target) {
    int start = 0;
    // len for right1st mid, so last elm left is rightmost target.
    int end = nums.length - 1;
    while (start < end) {
      // mid is right1st elm. ex: [8,8] t=8, mid=1, 8=8 ->, ret 1[8];
      int mid = start + (end - start + 1) / 2;
      if (nums[mid] == target) {
        // cut off left, inclusive mid, find rightmost target.
        start = mid;
      } else if (nums[mid] < target) {
        // target on the right, cut off left, exclude mid.
        start = mid + 1;
      } else {
        // midVal > target, target on left, exclude mid.
        end = mid - 1;
      }
    }
    // last one left, either is last target or -1.
    // ex: [2,2] end=1, start= mid(1)+1 = 2; choose end.
    return (nums[end] == target) ? end : -1;
  }
}