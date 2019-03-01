/**
189. Rotate Array

Given an array, rotate the array to the right by k steps, where k is non-negative.

Example 1:

Input: [1,2,3,4,5,6,7] and k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
Example 2:

Input: [-1,-100,3,99] and k = 2
Output: [3,99,-1,-100]
Explanation: 
rotate 1 steps to the right: [99,-1,-100,3]
rotate 2 steps to the right: [3,99,-1,-100]
Note:

Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
Could you do it in-place with O(1) extra space?

*/

/*
Solution 1:  index mapping, using a copyed array to preserve original data.
* rotation number > n == k % n; Let's call it newK.
* Indexes of the array moved forward newK times in the rotation, and if get pass n size, we rotate back to beginning.
* So we have the formula: newIndex = (index + newK) % n;
* Use a copyed array to store all the original data, map the newIndex val = index val;
* nums[(i + rotate) % n] = copy[i]; 
* 
* Time: O(n)
* Space: O(n)

Solution 2: Rotate layer by layer, without extra space.
Ex:
[1,2,3,4,5,6] k=2;
[(5),2,(1),4,(3),6]
[5,(6),1,(2),3,(4)]

* Keep a count of how many is rotated. Loop until count == n;
* Keep curInd, and curVal; user curVal to assign to nextInd;
* Use a Do while loop, until (curInd == start) again. circled back.
* nextInd = (curInd + k) % n;
* **Key** : Have a tempVal for nums[nextInd] so we won't lose that number, use it for next iteration.
* Time: O(n)
* Space: O(1)

Solution 3: Reverse all, Reverse 0->k-1; Reverse k-> n-1;

reverse all first; [1,2,3,4,5] -> [5,4,3,2,1]; k=3;
reverse first k items [(3,4,5),2,1]
reverse the rest k->n items [3,4,5,(1,2)]
* Time: O(n)
* Space: O(1)

*/

class RotateArray {
	
	/**
	 * Solution 1: index mapping, using a copyed array to preserve original data.
	 * Time: O(n)
	 * Space: O(n)
	 */
  public void rotate(int[] nums, int k) {
    int n = nums.length;
    int rotate = k % n;
    int[] copy = Arrays.copyOf(nums, n);
    for (int i = 0; i < n; i++) {
      nums[(i + rotate) % n] = copy[i]; 
    }
  }

  /**
   * Solution 2: index mapping, in a rotating circle, no need for copyed array.
   * Time: O(n)
   * Space: O(1)
   */
  public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    int count = 0;
    for (int start = 0; count < n; start++) {
      int curInd = start;
      int curVal = nums[curInd];
      // the next time curInd == start.
      do {
        // nextPos is the curVal
        int nextInd = (curInd + k) % n;
        int temp = nums[nextInd];
        nums[nextInd] = curVal;
        // next pos increment.
        curInd = nextInd;
        curVal = temp;
        count++;
      } while (curInd != start);
    }
  }

  /**
   * Solution 3: reversing the array. reverse order: all, 0->k-1, k->n-1;
   * time; O(n)
   * Space: O(1)
   */
  public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    // reverse all first; [1,2,3,4,5] -> [5,4,3,2,1]; k=3;
    reverse(nums, 0, n - 1);
    // reverse first k items
    reverse(nums, 0, k - 1);
    // reverse the rest k->n items
    reverse(nums, k, n - 1);
  }
  
  public void reverse(int[] nums, int start, int finish) {
    while (start < finish) {
      int temp = nums[start];
      nums[start] = nums[finish];
      nums[finish] = temp;
      start++;
      finish--;
    }
  }

}