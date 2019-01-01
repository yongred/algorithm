
/**
Solution: compare start with mid, see where the rotate happens.
How to arrive:
* We need to modify binary search. Need to know the break point of rotate.
* **key**: There is only 1 break point, meaning either on left or right of mid.
* If nums[start] < nums[mid], then we know left side is normal, break point on the right. Else we know break point on the left.
* The side w/o the break point, is in normal order, we can do binary search on that side.
* If target falls in the normal side range, then binary search in that side.
* If not, we know target is on the breakpoint side. So, we repeat the process on break point side, which we check split and check which side has breakpoint.
* **Special case**: only 2 nums left, nums[start] == nums[mid]; This could cause problem when checking range for right side. (target > nums[mid] && target <= nums[end])
	* Ex: [3,1] t=1; breakpoint is 3, target < nums[mid]; cause it to search left.
	* So we handle the 2 nums case before that.
* Time: O(lgN);
* Spce: O(1);
*/

class SearchRotatedSortedArray {

	/**
	 * Solution: iterative, compare start with end.
	 * Time: O(lgN);
	 * Space: O(1);
	 */
  public int search(int[] nums, int target) {
    if (nums.length == 0) {
      return -1;
    }
    int start = 0;
    int end = nums.length - 1;
    while (start <= end) {
      int mid = (start + end) / 2;
      if (nums[mid] == target) {
        return mid;
      }
      
      // start < mid means leftSide is in order. break on right side.
      if (nums[start] <= nums[mid]) {
        // if target is within the left range, check left.
        if (target < nums[mid] && target >= nums[start]) {
          end = mid - 1;
        } else {
          // target is on the right.
          start = mid + 1;
        }
      } else {
        // break on left side start > mid; Right side is in order
        // if target falls in right side range, check right.
        if (target > nums[mid] && target <= nums[end]) {
          start = mid + 1;
        } else {
          // target is on the left.
          end = mid - 1;
        }
      }
    }
    
    return -1;
  }

  /**
   * Solution: recursive
   * Time: O(lgN)
   * Space: O(lgN)
   */
  public int search(int[] nums, int target) {
    return helper(nums, target, 0, nums.length - 1);
  }
  
  public int helper(int[] nums, int target, int start, int end) {
    if (start > end) {
      return -1;
    }
    if (start == end) {
      return nums[start] == target ? start : -1;
    }
    int mid = (start + end) / 2;
    if (target == nums[mid]) {
      return mid;
    }
    // if left is normal side
    if (nums[start] <= nums[mid]) {
      // if target is in left range.
      if (target >= nums[start] && target < nums[mid]) {
        return helper(nums, target, start, mid - 1);
      } else {
        // not in left range, target in breakpoint right side.
        return helper(nums, target, mid + 1, end);
      }
    } else {
      // right side is normal
      // target is in right range.
      if (target > nums[mid] && target <= nums[end]) {
        return helper(nums, target, mid + 1, end);
      } else {
        // not in right range, target in breakpoint left side.
        return helper(nums, target, start, mid - 1);
      }
    }
    
  }
}