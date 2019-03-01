/**
41. First Missing Positive
Given an unsorted integer array, find the smallest missing positive integer.

Example 1:

Input: [1,2,0]
Output: 3
Example 2:

Input: [3,4,-1,1]
Output: 2
Example 3:

Input: [7,8,9,11,12]
Output: 1
Note:

Your algorithm should run in O(n) time and uses constant extra space.
*/

/*
Solution: partition, put # to its right place, index+1 place.
Ex1:
[3,4,-1,1]
[1,-1,3,4] index1 != index1+1 (missing), meaning index1+1=2, 2 is missing.
Ex2:
[7,8,2,1] => [1,2,7,8]; index2+1 != 7; 3 is missing.
Ex3:
[4,2,3,1] => [1,2,3,4]; no missing in between, so n(4)+1=5 is missing.

* Like 3 way partition:
* Positive #s <= n, goes to positions of the array. -#s and #s > n go to other places in array.
* From 0 -> n-1 index places, find and place all positive #s <= n, and place them in num-1 index place.
* To do that, we loop from 0-> n-1:
	* while [curNum-1] != curNum; we swap curNum to [curNum-1] place.
	* Use a while loop to swap, b/c if the swapped num is positive and <= n, then we will have to swap it to correct place as well;
* Time: O(n);
* Space: O(1)

*/

class FirstMissingPositive {

	/**
	 * Solution: partition, put # to its right place, index+1 place.
	 * Time: O(n);
	 * Space: O(1)
	 */
  public int firstMissingPositive(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 1;
    }
    int n = nums.length;
    // put all positive #s <= n in their right place. index num-1 place.
    for (int i = 0; i < n; i++) {
      // ignore -#, already inplace # (A[num-1] == num), and > n #.
      // while the # could be swap right place, keep swap/place them in place.
      while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
        swap(nums, i, nums[i] - 1);
      }
    }
    // loop from index0-> n-1; check if A[index] == index+1;
    for (int i = 0; i < n; i++) {
      if (nums[i] != i + 1) {
        return i + 1;
      }
    }
    // if all array's #s are in order [1...n]; then n+1 is the 1st missing positive.
    return n + 1;
  }
  
  public void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }
}