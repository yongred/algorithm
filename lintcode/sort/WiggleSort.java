/**
508. Wiggle Sort
Given an unsorted array nums, reorder it in-place such that

nums[0] <= nums[1] >= nums[2] <= nums[3]....
Example
Given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1, 6, 2, 5, 3, 4].

Notice
Please complete the problem in-place.
*/

/**
Solution 1: Sort and swap adj 2 by 2; starting with index1;
Ex: [3,5,2,1,6,4]
Sorted: [1,2,3,4,5,6] => [1,swap(2,3),swap(4,5),6] => [1,3,2,5,4,6];
* Time: O(nlgn);
* Space: O(1);

Solution 2: Make sure All Odd index Vals >= adj #s.
Ex: [5,2,3,4,6,1]
[5,2,3,4,6,1] => index1: (5,2,3) 5 is largest => (2,5,3) [2,5,3,4,6,1]
[2,5,3,4,6,1] => index3: (3,4,6) 6 is largest => (3,6,4) [2,5,3,6,4,1]
[2,5,3,6,4,1] => index5: (4,1,nil) 4 is largest => (1,4,nil) [2,5,3,6,1,4]
 ANS = [2,5,3,6,1,4];
* Time: O(n);
* Space: O(1);
*/

public class WiggleSort {

	/**
	 * Solution 2: Make sure All Odd index Vals >= adj #s.
	 * Time: O(n)
	 * Space: O(1)
	 */
  public void wiggleSort(int[] nums) {
    // loop odd #s
    for (int i = 1; i < nums.length; i += 2) {
      // Find the MAX between 3 nums;
      int maxIndex = 1;
      maxIndex = (nums[i - 1] > nums[i]) ? i - 1 : i;
      if (i + 1 < nums.length) {
        maxIndex = (nums[maxIndex] > nums[i + 1]) ? maxIndex : i + 1;
      }
      // make it even <= odd >= even
      if (maxIndex != i) {
        swap(nums, i, maxIndex);
      }
    }
  }

  /*
   * @param nums: A list of integers
   * @return: nothing
   * Solution 1: Sort, then swap adj 2 by 2, starting index1;
   * Time: O(nlgn)
   * Space: O(1)
   */
  public void wiggleSort(int[] nums) {
    Arrays.sort(nums);
    // swap 2 by 2. Start with 2nd num for /\/\;
    for (int i = 1; i + 1 < nums.length; i += 2) {
      swap(nums, i, i + 1);
    }
  }
  
  public void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }
}