
/**
Solution: recursive brute force;
How to Arrive:
* Notice there are 3 options. [1,2,3,4] it can be (1,3), (2,4) or (1,4);
* Let say cur is 4, the Max is either including 4 or excluding 4 use adj (3) max.
* so 4 can be included in 2 cases, 1 or 2, which is prev -2pos, or -3pos.
* not need to add be more then -3pos, b/c ex: [1,2,3,4,5]; 1+3 is already in 3rd pos. 5 simple needs the 3rd pos max, not the 1st.
* So it becomes curMax = curVal + Max(-2posMax, -3posMax);
* Then compare to curMax ><= adjMax, return > one.
* Time: O(3^n); from n->0 do T(n-1)+T(n-2)+T(n-3);
* Space: O(n);

Solution 2: DP, 2d array.
Ex: table
[
	 0, 1
0 [0, 0],
10 [0, 10],
7 [10, 7],
9 [10, 19],
1 [19, 11],
1 [19, 20],
4 [20, 23],
1 [23, 21]
]
How to Arrive:
* DP question, if we know the prevMax can we get curMax?
* Ex: 1,2,3,4; [1,2,3] max = 1+3=4; curVal =4; We need 1 more val, which is 2 to add to 4;
* So, we need 2 vals to compare. max includes curVal and max exclude curVal.
* **Key**: each iteration will carry over 2 Things: **inclMax, exclMax**; Then next iter will use those 2 numbers. 
* **if we know the prev inclMax and exclMax** we can use it to calc **curInclMax** and **curExclMax**;
* curInclMax = curVal + prevExclMax;
* curExclMax = Max(prevInclMax, prevExclMax);
	* **Key**: curExclMax == PrevMax; PrevMax is either prevVal included or excluded.
* Ex: 1,2,3,4; [1,2,3] max = 1+3=4; 4 is **prevInclMax**, we need **prevExclMax** also.
	* prevExclMax is 2, 4+2 = 6; 6 > 4; return 6;
* Now we can construct a table of nums and 2 options, 0 excl & 1 incl.
* As the table above shown, each num have 2 options, 0 excl & 1 incl.
* It carries over the Max of excl cur, and Max of incl cur.
* At last we just need to find the Max between incl lastVal or excl lastVal. dp[n][1], dp[n][0];
* Time: O(n);
* Space: O(n);
*/
import java.util.*;
import java.io.*;

class HouseRobber {

	/**
	 * Solution: Dynamic Programming
	 * Time: O(n);
	 * Space: O(n);
	 */
	public int rob(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    // 2 options for each num, Max of include or exclude cur
    int [][] dp = new int[nums.length + 1][2];
    
    for (int i = 1; i <= nums.length; i++) {
      // calc max adj, exclude curVal; 
      // prevMax is either prev excluded or included.
      dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
      // calc max include curVal, curIncl = curVal + prevExcl.
      dp[i][1] = nums[i - 1] + dp[i - 1][0];
    }
    // max is either include or exclude last num.
    return Math.max(dp[nums.length][1], dp[nums.length][0]);
  }

  /**
   * Solution: optimized space DP
   * Time: O(n)
   * Space: O(1)
   */
  public int rob1(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    // 2 options for each num, Max of include or exclude cur
    int prevIncl = 0;
    int prevExcl = 0;
    int inclMax = 0;
    int exclMax = 0;
    
    for (int i = 0; i < nums.length; i++) {
      // calc max adj, exclude curVal; 
      // exclMax is either prev excluded or included.
      // ex: 9,1,2,4 => exclMax: 0,9,9,11; didn't include 1;
      exclMax = Math.max(prevExcl, prevIncl);
      // calc max include curVal, curIncl = curVal + prevExcl.
      inclMax = nums[i] + prevExcl;
      prevExcl = exclMax;
      prevIncl = inclMax;
    }
    // max is either include or exclude last num.
    return Math.max(inclMax, exclMax);
  }
  
  /**
   * Solution recursive:
   * Time: O(3^n);
   * Space: O(n);
   */ 
  public int rob2(int[] nums) {
    if (nums == null || nums.length <= 0) {
      return 0;
    }
    return helper(nums, 0);
  }
  
  public int helper(int[] nums, int index) {
    if (index >= nums.length) {
      return 0;
    }
    int cur = nums[index] + Math.max(helper(nums, index + 2), helper(nums, index + 3));
    int adj = helper(nums, index + 1);
    
    return Math.max(cur, adj);
  }
  
}