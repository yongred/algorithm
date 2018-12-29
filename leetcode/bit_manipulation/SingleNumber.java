/**
136. Single Number
Given a non-empty array of integers, every element appears twice except for one. Find that single one.

Note:

Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

Example 1:

Input: [2,2,1]
Output: 1
Example 2:

Input: [4,1,2,1,2]
Output: 4
*/

/**
Solution: Using XOR to cancel out same nums.
Ex: 12132;
1^1=0, 2^2=0, 3 leftover;
return 3;

How to Arrive:
* XOR will get the binary diff between 2 nums/chars. 
* If (x ^ x) = 0; No Diff;
* If (x ^ 0) = x; Diff between 0 and x is x.
* So if num (binary vals) are the same, then XOR will cancel it out, and the leftover is the unique Num;
* Time: O(n)
* Space: O(1)
*/

class SingleNumber {

  public int singleNumber(int[] nums) {
    int leftover = 0;
    for (int n : nums) {
      // use XOR to cancel out same vals. 
      // Ex: 4,1,2,1,2; 1^1=0, 2^2=0, 4 left;
      leftover ^= n;
    }
    return leftover;
  }
}