/**
375. Guess Number Higher or Lower II
We are playing the Guess Game. The game is as follows:

I pick a number from 1 to n. You have to guess which number I picked.

Every time you guess wrong, I'll tell you whether the number I picked is higher or lower.

However, when you guess a particular number x, and you guess wrong, you pay $x. You win the game when you guess the number I picked.

Example:

n = 10, I pick 8.

First round:  You guess 5, I tell you that it's higher. You pay $5.
Second round: You guess 7, I tell you that it's higher. You pay $7.
Third round:  You guess 9, I tell you that it's lower. You pay $9.

Game over. 8 is the number I picked.

You end up paying $5 + $7 + $9 = $21.
Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.
*/

/**
Question: Guess from 1->n nums, Tells you your guess is > or <, What is the best way to choose that in worst case (guessed wrong until you are certain the ans) you still have the minimum loss.
**In other words: Mini cost among max losses**
Case 1: 1 num; n=1, cost is 0; We always choose correct if only 1 choice.
Case 2: 2 nums; {3,4} cost is smallest num, 3 in this case. B/c they till you > or < than the target. Choose 3, knowing 4 is next, pay $3; Choose 4 will pay $4; So 3 < 4;
Case 3: 3 nums; {3,4,5} cost is the mid num; since they told you > or <. Guess the mid, knowing we can choose the next one Correctly. 4 in this case.
*Notice we can choose start 3 or 4 or 5; 3[4,5] cost 7; [3]4[5] cost 4; [3,4]5 cost 8; So 4 is the minimum cost to guarantee a win.*
Case 4: More than 3 nums; {2,3,4,5} Same as 3 choices, we can start with any of the 4 nums. 
choose 2 first: 2[3,4,5] = 2 + 4 = 6; 
choose 3 first: [2] 3 [4,5], this is tricky, [2]=0 or [4,5]=4 which side do you pick from? We want the worst case, which is guessing wrong and opponent will choose larger cost num. So in this case 4 > 0; so 3 + 4 = 7;
choose 4 first: [2,3]4 [5], [2,3]=2 or [5]=0; opponent will choose the larger, so 2, 4+2=6;
choose 5 first: [2,3,4] 5, 5+3=8;
So we have 6,7,6,8; We find the mini cost 6;

Solution: Memorize recursive, top-down
How to arrive:
* From the analysis above, we need a range to choose from, starting with 1->n;
* We can choose any 1->n nums first, so a loop. Call it x.
* Now we can choose from **either left of x** or **right side of x**;
* We want the larger one between left and right. Then add it to cur x, since choosed x first.
* We get x + Max(func(start, i-1), func(i+1, end));
* The result is 1 possible mini cost. We need to find all possibilities, start with another x.
* **Base case** is when there is only 1 num left, we know the cost is 0;
* Otherwise we recurse down to smaller problem for left and right. And memorize the results.
* Time: O(n^3);
	* B/c we loop 1->n; for each num we recurse down i+1 and loop from 1->n again; 
	* And then for each of those 1->n nums we recurse down to another 1->n; Until n times. So for not memorized it's O(n^n);
	* What happen is choose start num from 1->n, for each choice we need to split and loop left and right which is n total. That is n^2; now n^2 will do the same for each, n-1; n^3; And on and on until down to 1 left;
	* We memorized, so no repeated recurse. We just traverse each different combos once. Meaning we generate all continuous subarrs, and we choose every # in that subarr. So, O(n^2) to get all continuous subarrs, O(n) to choose every possibility. 
* Space: O(n^2);

Solution: DP, bottom-up
How to arrive:
* Like memo solution, we need a 2d DP to store start and end miniCosts, dp[start][end]; So an dp[n+1][n+1]; 
* Now we need to **know the shorter range in order to get to the longest range 1->n**;
* Ex: {1,2,3,4} 1[2,3,4]; find the [2,3,4] first.
* So we need to find from the shortest range and progress to longest 1->n;
* We know the 1 num is 0, that is already the vals in dp. So we start with 2 num.
* So, for (int end = 2; end <= n; end++); Smallest range to larger.
* And, for (int start = end - 1; start > 0; start--); Smallest range from start->end to larger.
* Now we have the range, we need to choose every num in that range, get the worse case for choosing each num as 1st choice.
* for (int split = start + 1; split < end; split++); And, curMax = split + Math.max(dp[start][split - 1], dp[split + 1][end]);
* We go left and right to find the worse/largest cost between them. And add the cur choice cost to it.
* Now, find the miniCost among all the starting choices' worse costs.
* miniCost = Math.min(miniCost, curMax);
* Notice: we are spliting dp[start][split - 1] and dp[split + 1][end]. To prevent case of loop out of bounce, end+1 or start-1; the loop is for atleast 3 nums.
* We can check for 2 nums cases after the loop, dp[start][end] = (start + 1 == end) ? start : miniCost;
* Time: O(n^3); for every range (continuous subarrs) n^2, we traverse starting choices n;
* Space: O(n^2);

*/

class GuessHigherOrLowerII {

  public int getMoneyAmount(int n) {
    int[][] memo = new int[n + 1][n + 1];
    return helper(1, n, memo);
  }
  
  public int helper(int start, int end, int[][] memo) {
    if (start >= end) {
      return 0;
    }
    // see already calc.
    if (memo[start][end] > 0) {
      return memo[start][end];
    }
    int res = Integer.MAX_VALUE;
    // choose/start num between start->end, leads to minimum among the max costs.
    for (int i = start; i <= end; i++) {
      // choose i, find miniMax on left and on right, choose larger one for worst case scenario
      int curMax = i + Math.max(helper(start, i - 1, memo), helper(i + 1, end, memo));
      res = Math.min(res, curMax);
    }
    memo[start][end] = res;
    return res;
  }

  /**
   * Solution: DP bottom-up
   * Time: O(n^3);
   * Space: O(n^2);
   */
  public int getMoneyAmount(int n) {
    // start by end. row by col
    int[][] dp = new int[n + 1][n + 1];
    // goes col by col, ans is in row1, last col.
    // start at 2 b/c 1 num is 0, already init.
    for (int end = 2; end <= n; end++) {
      // start with 1 less than end, then 2 less, 3 less, moving up the row.
      for (int start = end - 1; start > 0; start--) {
        // minicost from start->end;
        int miniCost = Integer.MAX_VALUE;
        // choose a num from start->end; Split is choice to start with.
        // loop for atleast 3 nums from start->end. prevent case of loop out of bounce end+1 or start-1.
        for (int split = start + 1; split < end; split++) {
          // compare left and right, get the larger/worse case scenario.
          int curMax = split + Math.max(dp[start][split - 1], dp[split + 1][end]);
          // find the minimum cost among the worse costs of all starting choices.
          miniCost = Math.min(miniCost, curMax);
        }
        // 2 nums, just choose the smaller one, start.
        dp[start][end] = (start + 1 == end) ? start : miniCost;
      }
    }
    // ans is start at 1 -> end at n;
    return dp[1][n];
  }
}