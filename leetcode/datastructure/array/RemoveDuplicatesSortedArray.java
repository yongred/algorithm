/**
26. Remove Duplicates from Sorted Array
Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the new length.

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

Example 1:

Given nums = [1,1,2],

Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.

It doesn't matter what you leave beyond the returned length.
Example 2:

Given nums = [0,0,1,1,1,2,2,3,3,4],

Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.

It doesn't matter what values are set beyond the returned length.
Clarification:

Confused why the returned value is an integer but your answer is an array?

Note that the input array is passed in by reference, which means modification to the input array will be known to the caller as well.

Internally you can think of this:

// nums is passed in by reference. (i.e., without making a copy)
int len = removeDuplicates(nums);

// any modification to nums in your function would be known by the caller.
// using the length returned by your function, it prints the first len elements.
for (int i = 0; i < len; i++) {
    print(nums[i]);
}
*/

/**
Solution: Partition, assign prevPartedVal as comparison for unique num.
Ex:
Sorted
[0,0,1,1,1,2,2,3,3,4] len = 10
[0,1,2,3,4] len = 5
2 pointers
find unique num, put it in place.

[0,1,2,3,4]
prevVal: 0,1,2,3,4
partIndex: 1,2,3,4,5  ANS = 5;
i: 1 [1]=0 == prev(0) skip, 
[2]=1 update [partIndex(1)++]=1 prevVal=1,
[3]=1 == prevVal(1) skip, [4]=1 skip,
[5]=2 update [partIndex(2)++]=2 prevVal=2
[6]=2 == prevVal(2) skip
[7]=3 update [partIndex(3)++]=3 prevVal=3
[8]=3 == prevVal(3) skip
[9]=4 update [partIndex(4)++]=4 prevVal=4, partIndex = 5 ANS;

How to Arrive:
* The question is asking us to move appeared num vals to the left, skip same val duplicates. Return newLen;
* The array is Sorted, so duplicates are together. We can just skip over them to get the next unique num, and put it in place.
* So it is a Partition problem. However, the pivotVal changes each time a unique num found.
* We want the pivotVal to be the prev partitioned num. So we can use it to find unique num. Like the example above;
* **Key**: Start out the partitionIndex as 1; since index0 is already inplace. And we need pivotVal to be the prevPartedVal.
	* As for nums.len == 0 cases, take care it before anything, return 0;

* Time: O(n)
* Space: O(1)

---------------
Solution 2: Parition simpliflied
pivotVal is always the [pi-1] val; Just use that to compare.
Ex:
[1,1,2,3,3,4]
res: [1,2,3,4]
pi: 1,2,3,4
[1]=1; 1 == [pi1-1](1) skip
[2]=2; 2 > [pi1-1](1) assign 2 to pi, pi=2
[3]=3; 3 > [pi2-1](2) assign 3 to pi. pi=3
[4]=3; 3 == [pi3-1](3) skip
[5]=4; 4 > [pi3-1](3) assign 4 to pi. pi=4
ANS = 4;
*/

class RemoveDuplicatesSortedArray {

	/**
	 * Solution: Parition simpliflied
	 * Time: O(n)
	 * Space: O(1)
	 */
	public int removeDuplicates(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    int pi = 0;
    // pivotVal/prev finished num is always pi-1;
    for (int n : nums) {
      if (pi < 1 || n > nums[pi - 1]) {
        nums[pi] = n;
        pi++;
      }
    }
    return pi;
  }

	/**
	 * Solution: Partition, assign prevPartedVal as comparison for unique num.
	 * Time: O(n)
	 * Space: O(1)
	 */
  public int removeDuplicates(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    // first elm already in place.
    int partIndex = 1;
    // prevVal is the prev parted pos val. Use to compare for unique val.
    int prevVal = nums[0];
    // find unique #s, assign to partIndex pos.
    for (int i = partIndex; i < nums.length; i++) {
      // compare partVal to n[i]; Skip same num.
      if (nums[i] == prevVal) {
        continue;
      }
      // found unique n[i], assign to partIndex pos
      nums[partIndex] = nums[i];
      // update prevVal, as partIndex move to next pos.
      prevVal = nums[partIndex];
      partIndex++;
    }
    return partIndex;
  }
  
}