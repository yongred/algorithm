/**
268. Missing Number
Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.

Example 1:

Input: [3,0,1]
Output: 2
Example 2:

Input: [9,6,4,2,3,5,7,0,1]
Output: 8
Note:
Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
*/

/**
Solution: using XOR on index and vals.
How to Arrive:
* We know XOR can cancel out same elms. But nums elms don't have repeat elms.
* Nums are 0->n with 1 missing. Which means The Indexes are the same vals as nums elms, except for the 1 missing elm.
* Therefore we can XOR the indexes and the elms together, that way the XOR val left is the one missing.
* Loop from 0 -> n inclusive, since nums missed 1 elm.
	* XOR index.
	* If index < nums.length: (prevent out of bounce, since we loop til n);
		* XOR nums[i]
* Return XOR val result.
* Time: O(n)
* Space: O(1)

*/

class MissingNumber {

	/**
	 * Solution: using XOR on index and vals.
	 * Time: O(n)
	 * Space: O(1)
	 */
  public int missingNumber(int[] nums) {
    int xor = 0;
    for (int i = 0; i < nums.length + 1; i++) {
      xor ^= i;
      if (i < nums.length) {
        xor ^= nums[i];
      }
    }
    return xor;
  }
}