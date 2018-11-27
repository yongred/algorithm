/**
322. Coin Change
You are given coins of different denominations and a total amount of money amount. Write a function to compute the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.

Example 1:

Input: coins = [1, 2, 5], amount = 11
Output: 3 
Explanation: 11 = 5 + 5 + 1
Example 2:

Input: coins = [2], amount = 3
Output: -1
Note:
You may assume that you have an infinite number of each kind of coin.
*/

/**
Solution: DP dottom-up, 1d array.
coins: {3, 5};  amount= 8;
0  1  2  3  4  5  6  7  8
0 -1 -1 1  -1 1  2  -1 2
How to Arrive:
* This looks very similar to Perfect Square problem.
* If we know the (amount - coinVal) amount's Min # coins, we just need to add 1 for cur coin to get Min coins for this amount.
* Ex: coins {1,2} amount=4; 4-1 = 3 -> 2coins + 1 = 3coins; 4-2 = 2 -> 1coins + 1 = 2coins; So, the min coins for 4 is 2 coins.
* we need to choose each of the coins, and see which one leads to the min # of coins for the amount.
* Base case: amount=0 then 0 coin needed.
* So, we need to have a DP[amount + 1] to save all min # coins for vals <= amount.
* We loop through 1->n to fill out each val's min #, by testing each choice of coins.
```
for (int i = 1; i <= amount; i++)
		for (int coin : coins)
```
* When coin is > curAmount, then this coin is no use, check next coin.
* Also, if (curAmount - coinVal) == -1 then this coin cannot add up to this amount.
```
if (coin > i) {
	continue;
}
if (dp[i - coin] == Integer.MAX_VALUE) {
	continue;
}
``` 
* Otherwise, just add 1 coin to prev (curAmount - coinVal)'s min #;
```
int coinNum = 1 + dp[i - coin];
```
* maintain a Min for which coin leads to min # coins. If this coinMin < prevMin, then update.
```
minCoin = Math.min(minCoin, coinNum);
```
* Store the min in dp[curAmount];
* Repeat the process until dp[n];
* Time: O(n * c); amount * coins.
* Space: O(n);


Solution: Memorization top-down
How to arrive:
* Same idea as DP.
* loop through the coins to find the Min # by choosing each coin.
* if coin > amount, then skip it. 
* To find the Min for choosing cur coin, we can find the prev (amount - curCoin)'s min #, and add 1 (for curCoin);
* If (amount - curCoin) is -1 (no coins add up to val), then skip it. It cannot add up to curVal.
* Memorize the minCost among all choices. memo[amount]; 
* return either -1 or minCost.
* Time: O(n * c);
	* Analysis: Not cached is c^n; Cached, goes down first time memorized all sub amounts. After that we don't need to go down recurse call again. Just loop the coins. So loop the coins n times = c * n;
	* ex: amount = 6; coins={1,2,3}; B/c first recurse for c {1}; we already have all sub amounts cached. So other times, just loop the coins and retrieve from cached.
	6, 4, 3
	5, 3, 2
	4, 2, 1
	3, 1,
	2, 1
	1
* Space: O(n);

*/

class CoinChange {

	/**
   * Solution: DP Bottom-up
   * Time: O(n * c);
   * Space: O(n);
   */
  public int coinChange(int[] coins, int amount) {
    if (amount == 0) {
      return 0;
    }
    int[] dp = new int[amount + 1];
    for (int i = 1; i <= amount; i++) {
      int minCoin = Integer.MAX_VALUE;
      for (int coin : coins) {
        // if coin > curAmount cannot add up to it.
        // coins may not be in order, so don't break
        if (coin > i) {
          continue;
        }
        // if no coin can add up to i-coin Val, then this coin cannot add up to curVal.
        if (dp[i - coin] == Integer.MAX_VALUE) {
          continue;
        }
        // Min coins for i-coin val + 1 (cur coin) = min # of using this coin for curVAl.
        int coinNum = 1 + dp[i - coin];
        // Find the coin causing the min # of coins for curVal.
        minCoin = Math.min(minCoin, coinNum);
      }
      dp[i] = minCoin;
    }
    
    return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
  }

  /**
   * Solution: Memorization Top-down
   * Time: O(n * c);
   * Space: O(n);
   */
  public int coinChange(int[] coins, int amount) {
    int[] memo = new int[amount + 1];
    // -1 used for no coin add up to amount, So use -2 for not memorized.
    Arrays.fill(memo, 1, amount + 1, -2);
    return helper(coins, amount, memo);
  }
  
  public int helper(int[] coins, int amount, int[] memo) {
    if (amount == 0) {
      return 0;
    }
    // -2 is not memorized. Check if memorized.
    if (memo[amount] != -2) {
      return memo[amount];
    }
    int minCost = Integer.MAX_VALUE;
    for (int coin : coins) {
      if (coin > amount) {
        continue;
      }
      int prevMin = helper(coins, amount - coin, memo);
      if (prevMin == -1) {
        continue;
      }
      int curMin = 1 + prevMin;
      minCost = Math.min(minCost, curMin);
    }
    memo[amount] = (minCost == Integer.MAX_VALUE) ? -1 : minCost;
    return memo[amount];
  }
}