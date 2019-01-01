/**
154. Find Minimum in Rotated Sorted Array II
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

The array may contain duplicates.

Example 1:

Input: [1,3,5]
Output: 1
Example 2:

Input: [2,2,2,0,1]
Output: 0
*/
/**
Solution: same as Find Minimum in Rotated Sorted Array, with condition check.
How to arrive:
* Same as the first problem, find the Inflection Point. Compare midVal with StartVal.
* (mid-1 > mid) or (mid > mid+1) Found IP.
* When midVal == startVal, check mid == endVal. If mid != endVal, means Inflect Point on rightSide. Go check Right.
* If both midVal == startVal == endVal; Then we have to check both side.
* Check leftSide first, if left found IP, return that.
* If left Not found Ip, check rightSide. Both might be all repeat, then the whole arr is duplicate val, return that.
* Time: O(n); all duplicate worst case.
* Space: O(1) for iterative, O(lgN) for recursive.

Solution: simpler thinking, using End to check.
Ex: [1,2,3,4,5] normal, smallest on leftside.
[4,5,1,2,3] IP on right side inclusive mid (1->3);
[5,1,2,3,4] IP on leftside, inclusive mid. (5->2);
**Key**: The only case on the right is IP on right, midVal > endVal. So if we check that, we know smallest must be on left.
* **Base case:** Loop until = 1 num left, return nums[start];
* When there is duplicates:
Ex: [2,0,2,2,2] midVal == endVal, we can simply do end--; until either not duplicate or only 1 num left. 
* When not dupl found, repeat the process. In this case: [2,0]; 2 > 0; start = 1 end= 1; [0] returned.
* if [2,2,2]; all dupl, it loop till [2] return it.
```
while (start < end) {
      int mid = start + (end - start) / 2;
      // IP on right.
      // mid exclusive, since > end, not smallest
      if (nums[mid] > nums[end]) {
        start = mid + 1;
      } else if (nums[mid] < nums[end]) {
        // right normal, left could be normal or IP, 
        // EITHER way Left have the SMALLEST val inclusive mid. 
        // Ex: [(1,2),3] or [(3,1),2,3] or [(4,0,1),2,3]
        // inclusive mid b/c midVal < endVal, still could be smallest.
        end = mid;
      } else {
        // startVal == endVal.
        end--;
      }
    }
    // loop out when only 1 left. Which is the smallest.
    return nums[start];
```
*/

class FindMinRotatedSortedArrayII {

	/**
	 * Solution: iterative, simplified checking end.
	 * time: O(n)
	 * Space: O(1)
	 */
	public int findMin(int[] nums) {
    int start = 0;
    int end = nums.length - 1;
    while (start < end) {
      int mid = start + (end - start) / 2;
      // IP on right.
      // mid exclusive, since > end, not smallest
      if (nums[mid] > nums[end]) {
        start = mid + 1;
      } else if (nums[mid] < nums[end]) {
        // right normal, left could be normal or IP, 
        // EITHER way Left have the SMALLEST val inclusive mid. 
        // Ex: [(1,2),3] or [(3,1),2,3] or [(4,0,1),2,3]
        // inclusive mid b/c midVal < endVal, still could be smallest.
        end = mid;
      } else {
        // startVal == endVal.
        end--;
      }
    }
    // loop out when only 1 left. Which is the smallest.
    return nums[start];
  }

	/**
	 * Solution: iterative
	 * Time: O(n)
	 * Space: O(1)
	 */
	public int findMin(int[] nums) {
    if (nums.length == 1) {
      return nums[0];
    }
    int start = 0;
    int end = nums.length - 1;
    // no IP, normal arr
    if (nums[start] < nums[end]) {
      return nums[start];
    }
    
    while (start <= end) {
      // prevent [2,2] case where mid-1 outOfBounce.
      if (end - start == 1) {
        return Math.min(nums[start], nums[end]);
      }
      int mid = start + (end - start) / 2;
      // check if next is IP
      if (nums[mid] > nums[mid + 1]) {
        return nums[mid + 1];
      }
      // check if mid is IP
      if (nums[mid - 1] > nums[mid]) {
        return nums[mid];
      }
      // left normal
      if(nums[mid] > nums[start]) {
        // IP on right
        start = mid + 1;
      } else if (nums[mid] < nums[start]) {
        // IP on left.
        end = mid - 1;
      } else {
        // midVal == startVal
        if (nums[mid] != nums[end]) {
          // right is not dupl
          start = mid + 1;
        } else {
          // both have dupl
          // check left
          int left = mid - 1;
          while (left > start && nums[left] == nums[mid]) {
            left--;
          }
          if (nums[left] != nums[mid]) {
            // find non dupl, go left <-
            end = left;
            continue;
          }
          // all dupl in left. Check right
          int right = mid + 1;
          while (right < end && nums[right] == nums[mid]) {
            right++;
          }
          if (nums[right] != nums[mid]) {
            // find non dupl, go right.
            start = right;
          } else {
            // all duplicates in arr, return dupl val.
            return nums[mid];
          }
        }
      }
    }
    return -1;
  }

	/**
	 * Solution: recursive, find Inflect Point
	 * Time: O(n); worst all Duplicates.
	 * Space: O(lgN); only half at same time;
	 */
  public int findMin(int[] nums) {
    return helper(nums, 0, nums.length - 1);
  }
  
  public int helper(int[] nums, int start, int end) {
    if (start >= end) {
      return nums[start];
    }
    // prevent case [2,2], which could lead to mid-1 outofBounce.
    if (end - start == 1) {
      return Math.min(nums[start], nums[end]);
    }
    // if arr is normal, no Inflect Point.
    if (nums[start] < nums[end]) {
      return nums[start];
    }
    int mid = start + (end - start) / 2;
    // cur > next; IP is next.
    if (nums[mid] > nums[mid + 1]) {
      return nums[mid + 1];
    }
    // prev > cur; cur is IP.
    if (nums[mid - 1] > nums[mid]) {
      return nums[mid];
    }
    // left normal
    if (nums[mid] > nums[start]) {
      // IP on right, 
      // mid exclusive > start, not smallest.
      return helper(nums, mid + 1, end);
    } else if (nums[mid] < nums[start]) {
      // IP on left
      // mid exclusive, b/c we check its left and right, not IP.
      return helper(nums, start, mid - 1);
    } else {
      // midVal == startVal, left or/and right side is all duplicates.
      if (nums[mid] != nums[end]) {
        // if end != mid, right side is not duplicates.
        return helper(nums, mid + 1, end); 
      } else {
        // both midVal = startVal = endVal; need to find non-allDupl side.
        int leftVal = helper(nums, start, mid - 1);
        if (leftVal != nums[mid]) {
          // found non-dupl on left.
          return leftVal;
        } else {
          // left is dupl, find rightSide for smallest. (could be dupl val)
          return helper(nums, mid + 1, end);
        }
      }
    }
    
  }
  
}