/**
70. Climbing Stairs
You are climbing a stair case. It takes n steps to reach to the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Note: Given n will be a positive integer.

Example 1:

Input: 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
Example 2:

Input: 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
*/

/**
Solution: Recursive. Brute Force.
How to Arrive:
* Pattern: from n you can take 1 step n-1 left, or 2 steps n-2 left.
* like a tree: n - 1 => n-2, n-3;  n-2 => n-3, n-4; And continues.
* So, we can figure out n-1 ways + n-2 ways == n ways.
* Ex: n=3; take -1 have 2 left; take -2 have 1 left. Then 1 (1,1), 1(2) Or 2(1); 3 total.
* *For Leetcode, n=0 is 1 way*
* Time: O(2^n);
* Space: O(n);

Solution2: memorization. Top down
            4
          /   \
        3      2
      /   \    / 
    2     1  1   
How to Arrive:
* Now we know that recursion have repeated processes.
* Looking at the recursive tree above, to optimize that we can **get rid of the repeats by caching them in a array**.
* How to do that?
  * We pass an array along with each recursion. Store the ans in array, arr[n]; And check if arr[n] is already cached in arr, if so we don't need to calc again, just pass that val;
* Time: O(n);
* Space: O(n);

Solution 3: DP Bottom up;
How to arrive:
* If we know the n-1 and n-2 ways, we can find n ways, by adding them.
* So, that means we just need 2 variables to store the prev 2 positions.
* We know n=0 and n=1 both returns 1; So we store them in 2 variables. Let say prev and cur.
* Now to find n=2; just prev + cur; them update prev and cur to n=1, n=2; Pass to next iteration.
* When reaches to n; We get the answer.
* Time: O(n);
* Space: O(1);
*/

class ClimbingStairs {

  /**
   * Solution: DP bottom up
   * Time: O(n);
   * Space: O(1);
   */
  public int climbStairs(int n) {
    // init n=0 or n=1 ret 1;
    int prev = 1;
    int cur = 1;
    for (int i = 2; i <= n; i++) {
      int temp = cur;
      // ways(n) = ways(n-1) + ways(n-2);
      cur = prev + cur;
      prev = temp;
    }
    
    return cur;
  }

  /**
   * Solution: Recursive Brute force.
   * Time: O(2^n);
   * Space: O(n);
   */
  public int climbStairs1(int n) {
    if (n < 0) {
      return 0;
    }
    if (n == 1 || n == 0) {
      return 1;
    }
    int ways = climbStairs1(n - 1) + climbStairs1(n - 2);
    
    return ways;
  }

  /**
   * Solution: Memorization Top down
   * Time: O(n);
   * Space: O(n);
   */
  public int climbStairs2(int n) {
    int [] cache = new int[n + 1];
    // find ans w/ a cache from 0-n;
    return helper(n, cache);
  }
  
  public int helper(int n, int[] cache) {
    if (n < 0) {
      return 0;
    }
    // leetcode n=0 ret 1; and lintcode n=0 ret 0; 
    if (n == 1 || n == 0) {
      return 1;
    }
    // retrieve cached ans.
    if (cache[n] != 0) {
      return cache[n];
    }
    // 1 step or 2 step; n-1 + n-2; Store in cache
    cache[n] = helper(n - 1, cache) + helper(n - 2, cache);
    return cache[n];
  }
}