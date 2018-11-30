/**
81. Search in Rotated Sorted Array II
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., [0,0,1,2,2,5,6] might become [2,5,6,0,0,1,2]).

You are given a target value to search. If found in the array return true, otherwise return false.

Example 1:

Input: nums = [2,5,6,0,0,1,2], target = 0
Output: true
Example 2:

Input: nums = [2,5,6,0,0,1,2], target = 3
Output: false
Follow up:

This is a follow up problem to Search in Rotated Sorted Array, where nums may contain duplicates.
Would this affect the run-time complexity? How and why?
*/

/**
Solution: Add condition for duplicates.
How to Arrive:
* Extend from *Search in Rotated Sorted Array*, this needs to handle same nums.
* **Key**: Duplicates can be on either side, normal or breakpoint side.
	* Ex: [1,1,1,1,3,1]; In this case just compare startVal < midVal won't know which side to search.
* Handle same #s cases:
	* **nums[start] == nums[mid] means either left or right have all same elms**
	* [1,1,1,2,1] or [1,2,1,1,1]
	* Case: [1,1,1,2,3], In this case, mid=1 start=1; But end = 3; That means right side is not all duplicates; And we know nums[mid] != target, b/c we just check. So must be on Right side.
```
if (nums[mid] != nums[end]) {
	// if right is not all same.
	return helper(nums, target, mid + 1, end); 
}
```
	* Case: [1,2,1,1,1]; in this case, all start=mid=end=1; Now we have no choice but to check both sides; Check left(start, mid-1); if not found, check rightside right(mid+1, end);
* Time: O(n); Worst case all duplicates.
* Space: O(lgN); Only half is searched at a time, stack is only halved each time.
*/

class SearchRotatedSortedArrayII {

	/**
	 * Solution: iterative
	 * Time: O(n)
	 * Space: O(1)
	 */
  public boolean search(int[] nums, int target) {
    int start = 0;
    int end = nums.length - 1;
    while (start <= end) {
      int mid = (start + end) / 2;
      if (target == nums[mid]) {
        return true;
      }
      
      // if left side is normal.
      if (nums[start] < nums[mid]) {
        // within left range.
        if (target >= nums[start] && target < nums[mid]) {
          end = mid - 1;
        } else {
          // not in left range
          start = mid + 1;
        }
      } else if (nums[start] > nums[mid]) {
        // right side is normal
        if (target > nums[mid] && target <= nums[end]) {
          start = mid + 1;
        } else {
          end = mid - 1;
        }
      } else {
        // left or right halve is all repeats. [1,3,1,1,1,1];
        // if right side is not repeat
        if (nums[mid] != nums[end]) {
          start = mid + 1;
        } else {
          // need to find out which side is all repeats. And go to that side.
          int left = mid - 1;
          int right = mid + 1;
          while (left > start && right < end 
                 && nums[left] == nums[mid] && nums[right] == nums[mid]) {
            left--;
            right++;
          }
          // left is not all repeat
          if (left > start && nums[left] != nums[mid]) {
            end = left;
          } else if (right < end && nums[right] != nums[mid]) {
            // right is not all repeat
            start = right;
          } else {
            // both is repeat;
            break;
          }
        }
      }
    }
    
    return false;
  }

  /**
   * Solution: recursive
   * Time: O(n)
   * Space: O(lgN); Only half is searched at a time, stack is only halved each time.
   */
  public boolean search(int[] nums, int target) {
    return helper(nums, target, 0, nums.length - 1);
  }
  
  public boolean helper(int[] nums, int target, int start, int end) {
    if (start > end) {
      return false;
    }
    int mid = (start + end) / 2;
    if (target == nums[mid]) {
      return true;
    }
    // left normal
    if (nums[start] < nums[mid]) {
      // in left range.
      if (target >= nums[start] && target < nums[mid]) {
        return helper(nums, target, start, mid - 1);
      } else {
        // not in left range
        return helper(nums, target, mid + 1, end);
      }
    } else if (nums[start] > nums[mid]) {
      // right is normal
      // in right range
      if (target > nums[mid] && target <= nums[end]) {
        return helper(nums, target, mid + 1, end);
      } else {
        return helper(nums, target, start, mid - 1);
      }
    } else {
      // start = mid; duplicates, either left or right is all same.
      // check right
      if (nums[mid] != nums[end]) {
        // if right is not all same.
        return helper(nums, target, mid + 1, end); 
      } else {
        // both have duplicates, check both side
        boolean left = helper(nums, target, start, mid - 1);
        if (!left) {
          return helper(nums, target, mid + 1, end);
        } else {
          return left;
        }
      }
    }
    
  }

}