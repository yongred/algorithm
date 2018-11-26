/**
312. Burst Balloons
Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums. You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] * nums[right] coins. Here left and right are adjacent indices of i. After the burst, the left and right then becomes adjacent.

Find the maximum coins you can collect by bursting the balloons wisely.

Note:

You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
Example:

Input: [3,1,5,8]
Output: 167 
Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
             coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167

*/

/**
Solution: Recursive, Naive
How to arrive:
* We simulates the burst of bubble with an ArrayList. We burst, do left * cur * right, then remove from list (copy new list) recurse down to solve n-1 list with cur balloon bursted.
* And we compare and maintain the Max out of all choices of bursts.
int curVal = numList.get(i) * numList.get(i - 1) * numList.get(i + 1);
List<Integer> nextList = new ArrayList<Integer>(numList);
nextList.remove(i);
curVal += helper(nextList);
if (curVal > res) {
        res = curVal;
}
* Time: O(n!);
* Space: O(n);

Solution: Memorization. Think last burst -> first burst.
      b1  3   2   4    5  b1
b1 [-1, -1, -1, -1, -1, -1],
3  [-1, 6, 36, 99, 104, -1], 
2  [-1, -1, 24, 84, 99, -1], 
4  [-1, -1, -1, 40, 50, -1], 
5  [-1, -1, -1, -1, 20, -1], 
b1 [-1, -1, -1, -1, -1, -1]

How to arrive:
* If we think the problem bottom up, **last burst first**.
* Ex: [3,2,4,5]; We can burst either one LAST.
* if last burst is 2, [3] 2 [4,5]; 
* B/c 2 is the last to burst, 2 exist before [3] and [4,5] is bursted. 
* Left: (1 * 3 * 2) and Right: (2 * 4 * 5 + 2 * 5 * 1), or (4 * 5 * 1 + 2 * 4 * 1); 
* **Left and right is Bounded by 2, They are Independent from each other**.
* That means, the Left Max burst + cur burst + Right Max burst = Max burst for bursting curNum last.
* * **Key**: cur burst = leftBound * curVal * rightBound;
* It should multiply leftVal and rightVal outside of cur Range, b/c it is the last to burst, all nums in the Cur Range is Already Bursted.
* Now we do that to every possibility of bursting last 3 or 2 or 4 or 5;
* Get the maximum among them.
* Time: O(n^3); 
  * Generates every single continuous subarrs O(n^2), and traverse to burst each one to see the max O(n). n^2 * n = n^3;
* Space: O(n^2);

Solution: DP 2d array.
    b1  3  2  4  5  b1    
b1 [0, 0, 0, 0, 0, 0], 
3  [0, 6, 36, 99, 104, 0], 
2  [0, 0, 24, 84, 99, 0], 
4  [0, 0, 0, 40, 50, 0], 
5  [0, 0, 0, 0, 20, 0], 
b1 [0, 0, 0, 0, 0, 0]

How to Arrive:
* Memorization we think of last to burst, then recurse down to find earlier choices until 1st choice. Init 1st choice vals, use it for earlier recursions.
* So using same idea, for Dp we need to init 1st burst vals first (shortest range) 1 num. Then use shorter range vals to find longer range.
* Ex: [3,2], find [3] first is 1 * 3 * 2 = 6; [2] first = 3 * 2 * 1 = 6; Now we can use those vals to find [3,2]; 3 or 2 is choose last.
* Case: 2 Last = (3 bursted val) 6 + 1 * 2 * 1 = 8;
* Case: 3 Last = (2 bursted val) 6 + 1 * 3 * 1 = 9; 
* 9 > 8; So [3,2] = 9; 3 bursted last.
* So use this idea for DP, from shortest range (1 num) to longest (1st to n); So we goes col by col bases, from bottom row to top row; Start = end to start = 1;
```
for (int end = 1; end <= n; end++) {
    // from shortest range end->end to longest 1st->end;
    for (int start = end; start > 0; start--)
```
* Longest range is 1->n; Which is DP[1][n], this is the ans.
* And for each range, we need to choose which one is last to burst. We have start->end choices for cur range. 
* We calc the burstVal for each choice, and find the Max. And store that in DP.
```
for (int split = start; split <= end; split++) {
  int curBurst = boundedNums[split] * boundedNums[start - 1] * boundedNums[end + 1];
  int totalBurst = dp[start][split - 1] + curBurst + dp[split + 1][end];
  max = Math.max(max, totalBurst);
}
dp[start][end] = max;
```
* Repeat the process until we find DP[1][n];
* Time: O(n^3);
* Space: O(n^2);

*/

class BurstBalloons {

  /**
   * Solution: DP 2d Array.
   * Time: O(n^3)
   * Space: O(n^2)
   */
  public int maxCoins(int[] nums) {
    int n = nums.length;
    if (n == 0) {
      return 0;
    }
    // add boundary 1 to leftbound and rightbound.
    int[] boundedNums = new int[n + 2];
    boundedNums[0] = 1;
    boundedNums[n + 1] = 1;
    for (int i = 0; i < n; i++) {
      boundedNums[i + 1] = nums[i];
    }
    
    int[][] dp = new int[n + 2][n + 2];
    for (int end = 1; end <= n; end++) {
      // from shortest range end->end to longest 1st->end;
      for (int start = end; start > 0; start--) {
        int max = Integer.MIN_VALUE;
        // split is choice for last to burst. ex: [3,2,4,5] 3 last or 2 last ...
        for (int split = start; split <= end; split++) {
          int curBurst = boundedNums[split] * boundedNums[start - 1] * boundedNums[end + 1];
          int totalBurst = dp[start][split - 1] + curBurst + dp[split + 1][end];
          max = Math.max(max, totalBurst);
        }
        dp[start][end] = max;
      }
    }
    return dp[1][n];
  }

  /**
   * Solution: Memorization
   * Time: O(n^3)
   * Space: O(n^2)
   */
  public int maxCoins(int[] nums) {
    int n = nums.length;
    if (n == 0) {
      return 0;
    }
    // add boundary 1 to leftbound and rightbound.
    int[] boundedNums = new int[n + 2];
    boundedNums[0] = 1;
    boundedNums[n + 1] = 1;
    for (int i = 0; i < nums.length; i++) {
      boundedNums[i + 1] = nums[i];
    }
    // memo for range from start to end subarr + bounds. The Max bursts.
    int[][] memo = new int[n + 2][n + 2];
    // need to set vals to -1 since nums 0->100; positive, 0 is a possibility.
    for (int[] rows : memo) {
      Arrays.fill(rows, -1);
    }
    return calcMax(boundedNums, 1, n, memo);
  }
  
  public int calcMax(int[] boundedNums, int start, int end, int[][] memo) {
    if (start > end) {
      return 0;
    }
    if (memo[start][end] != -1) {
      return memo[start][end];
    }
    int max = Integer.MIN_VALUE;
    // Burst each one and get the largest.
    for (int i = start; i <= end; i++) {
      // cur burst val = leftBound * mid * rightBound. Remember it is the last burstVal.
      // start-1 = leftBound, end+1 = rightBound for cur range. B/c other vals in range are already bursted.
      // ex: cur range 1| [3,2] 5 |1; 5 is last one to burst, either we choose 2 or 3 the bound would be 1*cur*5;
      int curBurst = boundedNums[i] * boundedNums[start - 1] * boundedNums[end + 1];
      // prev max bursts from left
      int leftBurst = calcMax(boundedNums, start, i - 1, memo);
      // prev max bursts from right
      int rightBurst = calcMax(boundedNums, i + 1, end, memo);
      int totalBurst = curBurst + leftBurst + rightBurst;
      max = Math.max(max, totalBurst);
    }
    // memorized cur range max.
    memo[start][end] = max;
    return max;
  }

  /**
   * Solution: Naive, recursive
   * Time: O(n!)
   * Space: O(n)
   */
  public int maxCoins(int[] nums) {
    List<Integer> numList = new ArrayList<>();
    numList.add(1);
    for (int num : nums) {
      numList.add(num);
    }
    numList.add(1);
    return helper(numList);
  }
  
  public int helper(List<Integer> numList) {
    if (numList.size() <= 2) {
      return 0;
    }
    if (numList.size() <= 3) {
      return numList.get(1);
    }
    int res = Integer.MIN_VALUE;
    
    for (int i = 1; i < numList.size() - 1; i++) {
      int curVal = numList.get(i) * numList.get(i - 1) * numList.get(i + 1);
      List<Integer> nextList = new ArrayList<Integer>(numList);
      nextList.remove(i);
      curVal += helper(nextList);
      if (curVal > res) {
        res = curVal;
      }
    }
    return res;
  }
}