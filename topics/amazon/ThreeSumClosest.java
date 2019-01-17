/**
16. 3Sum Closest
Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.

Example:

Given array nums = [-1, 2, 1, -4], and target = 1.

The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
*/

/**
Solution: Sort, 2 pointers close the gap like binarySearch.
Ex:
[2,3,1,6,4] tar=14;
sort [1,2,3,4,6]
1: 1+10 = 11;
tar of 2sum = 14-1=13
2+6=8 < 13, 2->3; 
3+6=9 < 13, 3->4; 
4+6=10 < 13; 4->6; 6=6 start=end, return 10.

2: 2+10 = 12;
14-2=12
3+6=9 < 12, 3->4; 
4+6=10 < 12; 4->6; 6=6 start=end, return 10.

3: 3+10=13;
14-3=11;
4+6=10 < 11; 4->6; 6=6 start=end, return 10.
[3,4,6] is last 3 nums Done.  
Min(14-11, 14-12, 14-13); ANS = 13;

How to Arrive:
* As modified version of 3sum, we can use the same approach, with new conditions.
* New Condition:
	* We need to keep a record of closestSum b/c we may not find exactly sum = target.
  Ex: [2,5,8] 2sum tar=9; 2+8=10, 2+5=7; 10 is the Ans;
	* Find the diff of abs(tar-sum) and compare to prev record.
	* Use a boolean to init the first sum of 3 to closestSum.

* Time: O(nlgn);
* Space: O(1);
*/

class ThreeSumClosest {

	/**
	 * Solution: Sort, 2 pointers close the gap like binarySearch.
	 * Time: O(nlgn);
	 * Space: O(1);
	 */
  public int threeSumClosest(int[] nums, int target) {
    // sort, 2 pointers.
    Arrays.sort(nums);
    int closestSum = 0;
    boolean first = true;
    // loop and find first num, then add to closestTwoSum.
    for (int i = 0; i < nums.length - 2; i++) {
      int num1 = nums[i];
      // find 2 sum closest to tar-n1; start with next num in arr.
      int twoSum = twoSumClosest(nums, target - num1, i + 1);
      // find the diff
      int threeSum = twoSum + num1;
      // check if first 3 combos.
      if (first) {
        closestSum = threeSum;
        first = false;
      }
      int diff1 = Math.abs(target - closestSum);
      int diff2 = Math.abs(target - threeSum);
      // find the smaller diff.
      closestSum = (diff2 < diff1) ? threeSum : closestSum;
      if (closestSum == target) {
        break;
      }
    }
    return closestSum;
  }
  
  public int twoSumClosest(int[] nums, int target, int start) {
    // nums sorted. 2 pointers
    int end = nums.length - 1;
    // find 2 num sum closest to target.
    int closestSum = 0;
    boolean first = true;
    // calc sums for 2 nums. And close up the gap.
    while (start < end) {
      int sum = nums[start] + nums[end];
      // keep a record of closestSum.
      if (first) {
        closestSum = sum;
        first = false;
      } else {
        int diff1 = Math.abs(target - closestSum);
        int diff2 = Math.abs(target - sum);
        closestSum = (diff2 < diff1) ? sum : closestSum;
      }
      // close up the pointers to check for smaller diff.
      if (sum == target) {
        break;
      } else if (sum > target) {
        // target smaller, on left.
        end--;
      } else {
        // sum < target is bigger, on right
        start++;
      }
    }
    return closestSum;
  }
  
}