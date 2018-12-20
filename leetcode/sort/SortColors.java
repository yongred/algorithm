/**
75. Sort Colors
Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white and blue.

Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

Note: You are not suppose to use the library's sort function for this problem.

Example:

Input: [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
Follow up:

A rather straight forward solution is a two-pass algorithm using counting sort.
First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.
Could you come up with a one-pass algorithm using only constant space?
*/

/**
Solution 1: Using Counting sort. (Although Follow up want 1 pass, constant space). This is 2 pass.
How to arrive:
* We know the range, 0->2; So counting sort is the perfect fit.
* We counts 0,1,2 appearances in Counts array.
```
int[] counts = new int[max - min + 1];
// count the nums appearances
for (int num : nums) {
	counts[num]++;
}
```
* Then, we put nums into the original array in smallest to largest order.
* Loop from min to max:
	* if a num appears/count >= 1:
		* while counts of num is > 0:
			* nums[index] = n; (put the num into curPos);
			* index++;
			* Counts[n]--; (curNum has been put into arr);
* Time: O(n);
* Space: O(1); 3 extras space in Counts arr.

---------------------
Solution 2: 3-way Partition. 1 pass.
3 way partition: 
Invariant:
000xxxxxxxxx222
0... lo...mid...hi...N
[0,0,0,1,1,2,2]
       l
           m
         h
m=2 swap(m,h) h--; m=0 swap(l,m) l++ m++; m=0 swap(l,m) l++ m++;
m=1 m++; m=0 swap(l,m) l++ m++; m=1 m++; m=2 swap(m,h) h--; m=2 swap(m,h) h--;
h < m; DONE;

How to arrive:
* There are only 3 choices: 0,1,2; 
* Therefore if we encounter 0s, we know we can put them all on left. If we encounter 2s we can put them all on right.
* We can have 1pointer (mid) going through the array nums, 1 pointer (lo) keep the 0s on the left of it, and 1pointer (hi) keep the 2s on its right.
* Now when midPointer find 0s, swap with loPointer; When find 2s swap with hiPointer.
* When we put all 0s and 2s to the correct positions, then 1s are all in middle.
* To do that we need 3 pointers: lo, mid, hi;
Invariant: maintain this, don't change.
000xxxxxxxxx2222
0... lo...mid...hi...  N
* Algorithm:
* start lo and mid in 1st pos, index0; And hi in last pos index n-1;
* While mid <= hi:
	* if midVal = 0:
		* swap loVal with midVal,
		* increment both lo and mid;
		* **increment mid b/c lo pos # has already been checked, therefore lo will not have any 2s.** 
		* Ex: [2(lo)..0(mid)..1] won't happen, b/c mid has been to that pos before, therefore that 2 should have been swapped.
		* And mid should be >= lo;
	* if midVal = 1:
		* mid++; skip 1s. (when swapping with 0s and 2s, it will be swapped to right place)
	* if midVal = 2:
		* swap midVal with hiVal;
		* hiVal--;
		* **We don't increment mid in this case** b/c rightSide nums has not been checked yet. So we have to check it. Could be 0s, which need to be swapped with lo.
		* Everything on the left of mid is checked.
* Time: O(n);
* Space: O(1);
*/

class SortColors {

	/**
	 * Solution 2: 3-way Partition. 1 pass.
	 * Time: O(n)
	 * Space: O(1)
	 */
	public void sortColors(int[] nums) {
    int lo = 0;
    int mid = 0;
    int hi = nums.length - 1;
    // while mid <= hi, last elm still may need swap Ex: [2,0,0,2,2]
    while (mid <= hi) {
      if (nums[mid] == 0) {
        // swap with lo
        swap(nums, lo, mid);
        lo++;
        // increment mid b/c lo pos # has already been checked, therefore lo will not have any 2s.
        // And mid should be >= lo;
        mid++;
      } else if (nums[mid] == 2) {
        // swap with hi
        swap(nums, mid, hi);
        hi--;
      } else {
        // midVal == 1
        mid++;
      }
    }
  }
  
  public void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
  }

	/**
	 * Solution 1: counting sort. 2 pass solution.
	 * time: O(n)
	 * Space: O(1)
	 */
  public void sortColors(int[] nums) {
    countingSort(nums, 0, 2);
  }
  
  public void countingSort(int[] nums, int min, int max) {
    // form a count arr, Ex: [0,1,2,0,0,1] = [3,2,1]; 3 0s, 2 1s, 1 2s.
    int[] counts = new int[max - min + 1];
    // count the nums appearances
    for (int num : nums) {
      counts[num]++;
    }
    // nums arr index for putting in numbers from smallest to largest.
    int index = 0;
    // min to max, >= 1 appeared nums put into arr from smallest to largest order.
    for (int n = min; n <= max; n++) {
      // if curNum appears 1 or more times, curNum is the smallest uninserted num.
      if (counts[n] > 0) {
        // put all curNum into the front positions.
        while (counts[n] > 0) {
          // put in the curNum, and go to next pos.
          nums[index] = n;
          index++;
          // putted the curNum in, decrement.
          counts[n]--;
        }
      }
    }
  }
  
}