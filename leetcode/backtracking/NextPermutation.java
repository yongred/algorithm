

/**
Solution: Find Inverse point, from right to left, where leftNum < rightNum.
Find inverse point:
1,4,3,2 => from right to left, 3>2, 4>3, 1<4 Found. i0 is inversePoint.
Find smallest num > IP(1), 2 is the num. Swap them, => 2,4,3,1; 
Now reverse right subarr (4,3,1) after IP i0, => 2,1,3,4; ANS.

How to Arrive:
* The largest permutation is one with all DESC orders. Ex: 4,3,2,1;
* If one of them is in ASC order Ex: 4,2,3,1; That means we have larger # on the right of 2;
* Then the next larger num for that digit is the smallest num > 2 of the right. Which is 3;
* We swap them, [4,3,2,1]; Now the right side (2,1) is in DESC order, which means they are the largest. We want the smallest right side. 
* So we reverse rightSide. 2,1 -> 1,2; now we have [4,3,1,2] Which is the next larger Perm.
* If we have multiple Inverse Points: [4,5,2,3,1]; 4->5 & 2->3, two Inverse points.
* We are interested in smallest digit, which is on the right side. In this case 2->3 Inverse Point is the one we want.
* Algorithm:
* Find the smallest Inverse point (rightmost);
* Loop from right, find the 1st Inverse Point index.
* If there is a Inverse Point (nums not largest):
	* Find the smallest num > IP num, on the right of InversePoint.
	* Swap Ip with smallestNum found.
	* Reverse the right subarr of (IP index + 1 to end); *We want right to be smallest ASC*
* If no Inverse Point found:
	* Question wants the smallest perm, ASC.
	* Reverse the whole nums.
* Time: O(n);
* Space: O(1);
*/

class NextPermutation {

	/**
	Solution: Find Inverse point, from right to left, where leftNum < rightNum.
	* Time: O(n);
	* Space: O(1);
	*/
  public void nextPermutation(int[] nums) {
    // if only 1 num or less, no need to do anything.
    if (nums == null || nums.length <= 1) {
      return;
    }
    // inverse index, start with 2nd lastNum.
    int inverseIndex = nums.length - 2;
    // search for ip. Found / dir where leftNum < rightNum.
    while (inverseIndex >= 0 && nums[inverseIndex] >= nums[inverseIndex + 1]) {
      inverseIndex--;
    }
    // if found inversePoint, now find smallest # > IP# on right subarr.
    if (inverseIndex >= 0) {
      int minLargerIndex = inverseIndex + 1;
      for (int i = minLargerIndex; i < nums.length; i++) {
        // num > IP#, <= curMin. if == curMin, we want the rightmost smallest.
        if (nums[i] <= nums[minLargerIndex] && nums[i] > nums[inverseIndex]) {
          minLargerIndex = i;
        }
      }
      // swap Ip with smallest # > ip.
      int temp = nums[inverseIndex];
      nums[inverseIndex] = nums[minLargerIndex];
      nums[minLargerIndex] = temp;
      // reverse the right side to become smallest possible right subarr.
      // Ex: 2,(4,3,1) -> 2,1,3,4;
      reverse(nums, inverseIndex + 1, nums.length - 1);
    } else {
      // if no inversePoint, index= -1, Reverse all elms to return the smallest.
      reverse(nums, 0, nums.length - 1);
    }
  }
  
  public void reverse(int[] nums, int start, int end) {
    while (start < end) {
      int temp = nums[start];
      nums[start] = nums[end];
      nums[end] = temp;
      start++;
      end--;
    }
  }
}