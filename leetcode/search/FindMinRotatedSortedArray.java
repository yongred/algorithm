/**
153. Find Minimum in Rotated Sorted Array
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

You may assume no duplicate exists in the array.

Example 1:

Input: [3,4,5,1,2] 
Output: 1
Example 2:

Input: [4,5,6,7,0,1,2]
Output: 0
*/

/**
Solution: Find the Inflection point.
Ex: [3,4,5,6,1,2]  mid=2, 3 < 5; inflectPoint -> right side. 
[1,2,3]
How to arrive:
* The smallest of the rotated array, is the inflection point.
* Inflection point: Either prev > cur, cur = IP; or cur > next, next = IP;
* **Base case:** (mid-1 > mid) or (mid > mid+1);
* start < mid; then left is normal, IP on right side.
* start > mid; then IP on left side.
* Special case: if arr is normal, ASC, then check from the beginning, nums[0] < nums[n]; If yes, return nums[0];
* only 1 num: return nums[0];
* Time: O(lgN);
* Space: O(1);
*/

class FindMinRotatedSortedArray {

  /**
   * Solution: recursive;
   * Time: O(lgN);
   * Space: O(lgN)
   */
  public int findMin(int[] nums) {
    return helper(nums, 0, nums.length - 1);
  }
  
  public int helper(int[] nums, int start, int end) {
    if (start >= end) {
      return nums[start];
    }
    // left last mid
    int mid = start + (end - start) / 2;
    // start -> mid left is normal
    if (nums[start] <= nums[mid]) {
      // check right
      if (nums[mid] <= nums[end]) {
        // right is normal, both side normal, go left
        // mid might be smallest, ex: [1,2]; mid=0; <-. Inclusive mid.
        return helper(nums, start, mid);
      } else {
        // midVal > endVal, 
        // break -> right side. Go right side.
        // mid Exclusive, since mid > end, not smallest.
        return helper(nums, mid + 1, end);
      }
    } else {
      // left is breaked. startVal > midVal, right is normal. Go left.
      // mid might be the smallest, so Inclusive.
      return helper(nums, start, mid);
    }
  }

  /**
   * Solution: iterative, Find Inflection Point.
   * Time: O(LgN)
   * Space; O(1)
   */
  public int findMin(int[] nums) {
    if (nums.length == 1) {
      return nums[0];
    }
    int n = nums.length;
    // check if arr is not rotated.
    if (nums[0] < nums[n - 1]) {
      return nums[0];
    }
    int start = 0;
    int end = n - 1;
    // when there is 2 or 3 nums left, there will be return if there is IP
    // it won't goes to 1 num left.
    while (start <= end) {
      int mid = start + (end - start) / 2;
      // mid > next; next is IP. 
      // mid won't be last index, mid is leftLast.
      if (nums[mid] > nums[mid + 1]) {
        return nums[mid + 1];
      }
      // prev > mid; found Inflect Point
      // index 0 == IP case has been covered by prev condition. So mid-1 won't outOfBounce.
      if (nums[mid - 1] > nums[mid]) {
        return nums[mid];
      }
      // left normal, IP on right.
      if (nums[0] <= nums[mid]) {
        start = mid + 1;
      } else {
        // IP on left
        end = mid - 1;
      }
    }
    return -1;
  }

}