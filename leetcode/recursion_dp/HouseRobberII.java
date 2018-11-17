/**
213. House Robber II
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.

Example 1:

Input: [2,3,2]
Output: 3
Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
             because they are adjacent houses.
Example 2:

Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.
*/

/**
Solution: DP, split 0->n-1 & 1->n;
How to arrive:
* Looking at it carefully, it is just House Robber problem with 2 Maxes to compare;
* **Key**: split the problem into 2 arrays,  0->n-1 & 1->n;
	* Ex: [10,7,9,1,1,4] -> [10,7,9,1,1] and [7,9,1,1,4]; Now just get the Maxes from both and compare.
* **House Robber** linear solution is to have inclMax and exclMax pass down to next iteration.
* curInclMax = curVal + prevExclMax;
* curExclMax = Max(prevInclMax, prevExclMax);
	* **Key**: curExclMax == PrevMax; PrevMax is either prevVal included or excluded.
* We do this to [10,7,9,1,1] and [7,9,1,1,4] then compare their results, get the Max.
* Time: O(n);
* Space: O(1);
*/

import java.util.*;
import java.io.*;

class HouseRobberII {

  public int rob(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    if (nums.length == 1) {
      return nums[0];
    }
    // Maxes for 0 -> n-1;
    int firstInclMax = 0;
    int firstExclMax = 0;
    // Maxes for 1 -> n;
    int lastInclMax = 0;
    int lastExclMax = 0;
    
    for (int i = 0; i < nums.length - 1; i++) {
      // including first val.
      int tempExcl = firstExclMax;
      firstExclMax = Math.max(firstInclMax, firstExclMax);
      firstInclMax = nums[i] + tempExcl;
      // including last val.
      tempExcl = lastExclMax;
      lastExclMax = Math.max(lastInclMax, lastExclMax);
      lastInclMax = nums[i + 1] + tempExcl;
    }
    int firstMax = Math.max(firstInclMax, firstExclMax);
    int lastMax = Math.max(lastInclMax, lastExclMax);
    
    return Math.max(firstMax, lastMax);
  }
}