
/**
Solution: / goes ->, \ goes <-; Binary Search.
 [-INF, 1,2,3,4,1, -INF]; 3 < 4; peak on the right. Same as [-INF, 1,2,3,4, -INF] since 4 > -INF.
 return index 3, val 4;
 [-INF, 6,5,4,3,7,1, -INF]; 4 > 3; peak on left, index 0, val 6;
How to arrive:
* The question told us there is adjacent vals are not same. And n[-1] and n[len] == -INF.
* Which means #s are either / increasing or \ decreasing. And first or last num could be the peak. [6,5,4] 1st index 0 is peak, [4,5,6] last index 2 is peak. [5,4,6] both 0 or 2 index are peaks.
* We can do a binary search on that condition.
* If Mid is increasing, mid < next, means a peak is on the right side. 
* Ex: [1,4,5,6,7,1] 5 < 6; goes right and find 7; [6,7,1] subarr have peak.
* If Mid is decreasing, mid > next, means a peak is on the left side.
* Ex: [1,5,4,3,2] 4 > 3; goes left and find [1,5,4] (mid is inclusive, b/c mid > next);
* **Base case:** when there is only 1 num left return.
* Time: O(lgN);
* Space: O(1) for iterative, O(lgN) for recursive.
*/

class FindPeakElement {

	/**
	 * Solution: Iterative BinarySearch.
	 * Time: O(LgN)
	 * Space: O(1)
	 */
	public int findPeakElement(int[] nums) {
    int start = 0;
    int end = nums.length - 1;
    while (start < end) {
      int mid = start + (end - start) / 2;
      // increasing / order, peak on right
      if (nums[mid] < nums[mid + 1]) {
        // exclusive mid, [mid] < [mid+1];
        start = mid + 1;
      } else {
        // decreasing \ order, peak on left.
        // inclusive mid, midVal > nextVal, still possible as peak.
        end = mid;
      }
    }
    // when only 1 num left.
    return start;
  }

	/**
	 * Solution: recursive, find / or \
	 * Time: O(lgN)
	 * Space: O(lgN)
	 */
	public int findPeakElement(int[] nums) {
		return helper(nums, 0, nums.length - 1);
	}

	public int helper(int[] nums, int start, int end) {
		if (start >= end) {
			return start;
		}
		int mid = start + (end - start) / 2;
		// ASC order, goes right for peak.
		if (nums[mid] < nums[mid + 1]) {
			// exclusive mid, midVal < nextVal.
			return helper(nums, mid + 1, end);
		} else {
			// mid > next, no == for adj vals.
			// peak on left, inclusive mid, since mid > next, still possible as peak.
			return helper(nums, start, mid);
		}
	}
}