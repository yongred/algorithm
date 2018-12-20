/**
283. Move Zeroes
Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.

Example:

Input: [0,1,0,3,12]
Output: [1,3,12,0,0]
Note:

You must do this in-place without making a copy of the array.
Minimize the total number of operations.
*/

/**
left: all non-0s
right: 0s;
[0,1,0,3,12]

*/

/**
Solution 1: naive, Like bubble sort. Reverse loop find 0s, swap them till rightmost nonZero pos. 
* swap with all nonZero pos on right until, it is in next 0 pos.
Ex: [7,8,(0),9,10,0,0] => [7,8,9,10,(0),0,0]
* Time: O(n^2)
* Space: O(1)

----------------

Solution 2: Partition,  2 pointers, find non-zeroes swap with cur left slot.
Ex:
[1,3,12,0,0]
        l
            i
 
i=0, i++; i=1 swap (1,0) l++ i++; i=0, i++; 
i=3, swap(3,0) l++ i++; i=12 swap(12,0) l++ i++;
i=0 i++; DONE;

How to arrive:
* If we move the non-zeroes in order to their correct positions, then the 0s is also in pos.
* This just Partition.
* **Keep a partitionIndex for all the non-zeroes put in places**;
* Loop from left -> right, to find non-zeroes and put them in place 1 by 1.
* left-> right preserve the orders.
* When n[i] != 0; we swap with n[partIndex], and partIndex++ move to place next non-zero pos.
* **Alternatly**: b/c we know after all non-zeroes in correct places, all right rest positions are 0s. So just assign the rest to 0s.
* Time: O(n);
* Space: O(n);

*/

class MoveZeroes {

	/**
	 * Soluton 2: partition non-zeros to left.
	 * time: O(n)
	 * space: O(1)
	 */
	public void moveZeroes(int[] nums) {
    if (nums == null || nums.length == 0) {
      return;
    }
    // use 2 pointers
    // left p of all non-zeros
    int left = 0;
    // loop and find & move non-0s to left. Partition.
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        // place in cur left pos for non-zero
        swap(nums, i, left);
        left++;
      }
    }
  }

  /**
   * Solution 3: partition non-zeros to left w/o swap, just assign rest right to 0s.
   * time: O(n)
   * space: O(1)
   */
  public void moveZeroes(int[] nums) {
    if (nums == null || nums.length == 0) {
      return;
    }
    // use 2 pointers
    // left p of all non-zeros
    int left = 0;
    // loop and find non-0s to left. Partition.
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        // place non-zero to cur correct pos.
        nums[left] = nums[i];
        left++;
      }
    }
    // rest pos are all 0s.
    for (int j = left; j < nums.length; j++) {
      nums[j] = 0;
    }
  }

	/**
	 * Solution: naive, Like bubble sort. Reverse loop find 0s, swap them till rightmost nonZero pos. 
	 * Time: O(n^2)
	 * Space: O(1)
	 */
  public void moveZeroes(int[] nums) {
  	// reverse loop, find 0s
    for (int i = nums.length - 1; i >= 0; i--) {
      if (nums[i] == 0) {
      	// swap with all nonZero pos on right until, it is in next 0 pos.
      	// Ex: [7,8,(0),9,10,0,0] => [7,8,9,10,(0),0,0]
        int j = i;
        while (j + 1 < nums.length && nums[j + 1] != 0) {
          swap(nums, j, j + 1);
          j++;
        }
      }
    }
  }
  
  public void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }
}