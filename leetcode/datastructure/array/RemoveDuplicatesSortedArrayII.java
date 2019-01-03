/**
80. Remove Duplicates from Sorted Array II
Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most twice and return the new length.

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

Example 1:

Given nums = [1,1,1,2,2,3],

Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 respectively.

It doesn't matter what you leave beyond the returned length.
Example 2:

Given nums = [0,0,1,1,1,1,2,3,3],

Your function should return length = 7, with the first seven elements of nums being modified to 0, 0, 1, 1, 2, 3 and 3 respectively.

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
Solution 1: traditional partition. Count appearance
Ex:
[0,0,1,1,1,1,2,3,3]
res: [0,0,1,1,2,3,3]
pivotVal: val,cnt [0,2], [1,2], [2,1], [3,2]
partIndex: 2,3,4,5,6,7
i: 2 [2]=1 1 != pv(0) update [pi(2)++]=1, pv=[v1,c1];
3 [3]=1 1==pv(1), cnt=1 < 2, update [pi(3)++]=1, pv=[v1,c2];
4 [4]=1 1==pv(1) cnt=2 skip
5 [5]=1 1==pv(1) cnt=2 skip
6 [6]=2 2!=pv(1) update [pi(4)++]=2, pv=[v2, c1]
7 [7]=3 3!=pv(2) update [pi(5)++]=3, pv=[v3, c1]
8 [8]=3 3==pv(3) cnt=1, update [pi(6)++] pv=[v3, c2]
ANS pi = 7;

How to Arrive:
* Use a partitionVal with count, update the partitionVal when unique num apears.
* if count for pv > 2, skip
* Basically keep a count and the curVal. [v2,c1] compare to n[i] and assign to PI.
* Time: O(n)
* Space: O(1)

-----------------

Solution 2: Partition Simplified by compare to pi-2 pos;
Ex:
[1,1,1,2,3,3,3]
res: [1,1,2,3,3]
pi: 0,1,2,3,4
pi0 < 2, res[pi0]=1, pi=1
pi1 < 2, res[pi1]=1, pi=2
[2] = 1, 1 == res[pi2-2](1) skip
[3]=2, 2 > res[pi2-2](1) update res[pi2]=2, pi=3
[4]=3, 3 > res[pi3-2](1) update res[pi3]=3, pi=4
[5]=3, 3 > res[pi4-2](2) update res[pi4]=3, pi=5
[6]=3, 3 = res[pi5-2](3) skip
DONE, pi = 5 ANS;

How to Arrive:
* Notice pivotVal is always [pi-2] val;
* We can simplify the partition, by compare the newNum to pi-2 pos, pi is at pos for new elm, so pi-2 pos is the last finished (cnt=2) elm;
Ex: partitioned elms [1,1,2, pi]; n=2; pi-2 is (1) which is the last finished elm. 
So [1,1,2,2,pi]; Next is n=2 again, pi-2 is (2) which is the finished last elm.
* Time: O(n)
* Space: O(1)
*/

class RemoveDuplicatesSortedArrayII {

	/**
	 * Solution 2: partition Simplified compare to pi-2 pos
	 * Time: O(n)
	 * Space: O(1)
	 */
	public int removeDuplicates(int[] nums) {
    int pi = 0;
    // check n is unique from pi-2 pos. Ex: 1,1,(pi)1, (n)2; [pi]=2;
    for (int n : nums) {
      // keep pi-2 diff from pi.
      if (pi < 2 || n > nums[pi - 2]) {
        nums[pi] = n;
        pi++;
      }
    }
    return pi;
  }

	/**
	 * Solution 1: traditional partition. Count appearance
	 * Time: O(n)
	 * Space: O(1)
	 */
  public int removeDuplicates(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    // partition index, first index0 is in place.
    int partIndex = 1;
    int pivotVal = nums[0];
    // count elm appearance.
    int count = 1;
    // find 1st elm, cnt 1 or 2.
    if (partIndex < nums.length && partIndex < 2 
        && nums[partIndex] == nums[partIndex - 1]) {
      count++;
      partIndex++;
    }
    // now partIndex is at pos for 2nd elm.
    // insert n[i] cnt <= 2;
    for (int i = partIndex; i < nums.length; i++) {
      // compare pv to n[i]
      if (nums[i] == pivotVal) {
        // count == 2, skip
        if (count < 2) {
          // less than 2, move to pi.
          nums[partIndex] = nums[i];
          partIndex++;
          count++;
        }
      } else {
        // not = pv; unique num, 
        nums[partIndex] = nums[i];
        partIndex++;
        // change pv to unique num, count is 1.
        pivotVal = nums[i];
        // reset count, 
        count = 1;
      }
    }
    return partIndex;
  }
  
}