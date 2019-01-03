/**
27. Remove Element
Given an array nums and a value val, remove all instances of that value in-place and return the new length.

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

The order of elements can be changed. It doesn't matter what you leave beyond the new length.

Example 1:

Given nums = [3,2,2,3], val = 3,

Your function should return length = 2, with the first two elements of nums being 2.

It doesn't matter what you leave beyond the returned length.
Example 2:

Given nums = [0,1,2,2,3,0,4,2], val = 2,

Your function should return length = 5, with the first five elements of nums containing 0, 1, 3, 0, and 4.

Note that the order of those five elements can be arbitrary.

It doesn't matter what values are set beyond the returned length.
Clarification:

Confused why the returned value is an integer but your answer is an array?

Note that the input array is passed in by reference, which means modification to the input array will be known to the caller as well.

Internally you can think of this:

// nums is passed in by reference. (i.e., without making a copy)
int len = removeElement(nums, val);

// any modification to nums in your function would be known by the caller.
// using the length returned by your function, it prints the first len elements.
for (int i = 0; i < len; i++) {
    print(nums[i]);
}
*/

/**
Solution: Partition every != val to left, the partitionIndex is the count.
Ex: 
[0,1,2,2,3,0,4,2]; len = 8
[0,1,3,0,4,2,2,2]; len = 5
Or
[0,1,2,2,3,0,4,2] len = 8;
[0,1,3,0,4, (0,4,2)don't matter] len = 5;

How to Arrive:
* Understand that the question is asking us to move all elms not = val to the left, then return the new len.
* Which is the same as Partition. And the Partition Index is the same as newLen.
* Now we can do a traditional partition using swap, but that will be slow.
* We can simply assign != val #s all to the left, b/c we don't care the vals after the newlen.
* Time: O(n)
* Space: O(1)
*/

class RemoveElement {

	/**
	 * Solution Simplified: no swap, just assign.
	 * Time: O(n)
	 * Space: O(1)
	 */
	public int removeElement(int[] nums, int val) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    // assign all != val #s to left.
    int partIndex = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != val) {
        // simply assign to partIndex position, no need to swap.
        nums[partIndex] = nums[i];
        partIndex++;
      }
    }
    return partIndex;
  }

	/**
	 * Solution: Partition every != val to left, the partitionIndex is the count.
	 * Time: O(n)
	 * Space: O(1)
	 */
  public int removeElement(int[] nums, int val) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    int newLen = partition(nums, val);
    return newLen;
  }
  
  public int partition(int[] nums, int val) {
    // != val, to the left, = val right.
    int partIndex = 0;
    for (int i = partIndex; i < nums.length; i++) {
      // compare curNum to val
      if (nums[i] != val) {
        // put behind partIndex. Swap
        int temp = nums[partIndex];
        nums[partIndex] = nums[i];
        nums[i] = temp;
        partIndex++;
      }
    }
    return partIndex;
  }
  
}