/**
515. Paint House
There are a row of n houses, each house can be painted with one of the three colors: red, blue or green. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x 3 cost matrix. For example, costs[0][0] is the cost of painting house 0 with color red; costs[1][2] is the cost of painting house 1 with color green, and so on... Find the minimum cost to paint all houses.

Example
Given costs = [[14,2,11],[11,14,5],[14,3,10]] return 10

house 0 is blue, house 1 is green, house 2 is blue, 2 + 5 + 3 = 10

Notice
All costs are positive integers.
*/

/**
Solution: DP, optimized Space.
		   R		B   G
H0 [14, 2, 11], 
H1 [13, 25, 7], 
H2 [21, 10, 23]
Ans = 10;
How to Arrive:
* We have n houses, each house has 3 choices. This fits DP 2d array for saved vals.
* We know the min cost is either R B or G, So for H0 we just choose the min among 3 colors.
* Now for H1, Min is among cur color costs + H0 prev color costs. But H1 color cannot be same as H0
* That means H1:R cannot add to H0:R, it left with H1:R + H0:B or H1:R + H0:G; Same with other 2 colors.
* **Key**: Now that give us 6 different choices: (H1:R + H0:B, H1:R + H0:G);  Or (H1:B + H0:R, H1:B + H0:G);  Or  (H1:G + H0:R, H1:G + H0:B);
* Find the Min between those 6 choices, we get H1 Min. **We want to Maintain R B G mins, for next calc**. 
* So save the Min vals for H0 & H1 RBG min costs. DP[1][R], DP[1][B], DP[1][G];
* Now, using the prev 3 RBG mins we can do the same with H2.
* Repeat until we find the last row RBG, return the Min among the 3.
* Time: O(n); T(3n);
* Space: O(1); Notice: we can actually use input param Array costs[][] as our DP, so we can save space. Otherwise O(n) for S(3n);


Solution: Memorization top-down. Stack overflow in large test.
How to arrive:
* We need a list of 3 colors costs. So we need a function return list of minCost for every houseLevel.
* Base Case: house 0 just return Cost[0] vals in a list.
```
List<Integer> rowList = new ArrayList<>();
if (houseLevel <= 0) {
	for (int i = 0; i < costs[0].length; i++) {
		rowList.add(costs[0][i]);
	}
	memo.add(rowList);
	return memo.get(0);
}
```
* if memorized, then return memo.get(houseLevel);
* Recursive cases: we get the prevRow from recursive call to HouseLevel -1. 
```
List<Integer> prevRow = helper(costs, houseLevel - 1, memo);
```
* Then loop through 3 colors, find prev 2 colors, get the smaller one add to curCost for this color.
```
for (int color = 0; color < 3; color++) {
		// c=0 -> 1, c=1 -> 2, c=2 -> 0;
		int prevColor1 = (color == 0) ? 1 : (color == 1 ? 2 : 0);
		// c=0 -> 2, c=1 -> 0, c=2 -> 1; 
		// pc1 & pc2: c=0 {1,2}, c=1 {2,0}, c=2 {0,1}
		int prevColor2 = (color == 0) ? 2 : (color == 1 ? 0 : 1);
		// choose the smaller prevColor cost.
		int prevSmaller = Math.min(prevRow.get(prevColor1),
				prevRow.get(prevColor2));
		int curCost = costs[houseLevel][color] + prevSmaller;
		rowList.add(curCost);
	}
```
* Memorize it in memo.
* return the last row.
* Time: O(n); T(3n)
* Space: O(n);

*/

public class PaintHouse {

	/**
   * @param costs: n x 3 cost matrix
   * @return: An integer, the minimum cost to paint all houses
   * Solution: DP, optimized space using input array.
   * Time: O(n); T(3n);
   * Auxiliary Space: O(1);
   */
  public int minCost(int[][] costs) {
    int n = costs.length;
    if (n == 0) {
      return 0;
    }

    for (int i = 1; i < n; i++) {
      for (int color = 0; color < 3; color++) {
        // c=0 -> 1, c=1 -> 2, c=2 -> 0;
        int prevColor1 = (color == 0) ? 1 : (color == 1 ? 2 : 0);
        // c=0 -> 2, c=1 -> 0, c=2 -> 1; 
        // pc1 & pc2: c=0 {1,2}, c=1 {2,0}, c=2 {0,1}
        int prevColor2 = (color == 0) ? 2 : (color == 1 ? 0 : 1);
        // choose the smaller prevColor cost.
        int prevSmaller = Math.min(costs[i - 1][prevColor1], costs[i - 1][prevColor2]); 
        // cur color min cost = this color cost for cur house + prev smaller color cost.
        costs[i][color] += prevSmaller;
      }
    }
    // return the min of 4 houses total, between 3 color choices.
    return Math.min(costs[n - 1][0], Math.min(costs[n - 1][1], costs[n - 1][2]));
  }

  /**
   * @param costs: n x 3 cost matrix
   * @return: An integer, the minimum cost to paint all houses
   * Solution: DP, bottom up
   * Time: O(n); T(3n);
   * Space: O(n); S(3n);
   */
  public int minCost2(int[][] costs) {
    int n = costs.length;
    if (n == 0) {
      return 0;
    }
    int[][] dp = new int[n + 1][3];
    
    for (int i = 1; i <= n; i++) {
      for (int color = 0; color < 3; color++) {
        // c=0 -> 1, c=1 -> 2, c=2 -> 0;
        int prevColor1 = (color == 0) ? 1 : (color == 1 ? 2 : 0);
        // c=0 -> 2, c=1 -> 0, c=2 -> 1; 
        // pc1 & pc2: c=0 {1,2}, c=1 {2,0}, c=2 {0,1}
        int prevColor2 = (color == 0) ? 2 : (color == 1 ? 0 : 1);
        // choose the smaller prevColor cost.
        int prevSmaller = Math.min(dp[i - 1][prevColor1], dp[i - 1][prevColor2]); 
        // cur color min cost = this color cost for cur house + prev smaller color cost.
        dp[i][color] = costs[i - 1][color] + prevSmaller;
      }
    }
    // return the min of 4 houses total, between 3 color choices.
    return Math.min(dp[n][0], Math.min(dp[n][1], dp[n][2]));
  }

  /**
   * @param costs: n x 3 cost matrix
   * @return: An integer, the minimum cost to paint all houses
   * Solution: Memorization recursive top-down.
   * Time: O(n); T(3n)
   * Space: O(n);
   */
  public int minCost3(int[][] costs) {
    if (costs.length == 0) {
      return 0;
    }
    List<List<Integer>> memo = new ArrayList<>();
    List<Integer> res = helper(costs, costs.length - 1, memo);
    return Math.min(res.get(0), Math.min(res.get(1), res.get(2)));
  }
  
  public List<Integer> helper(int[][] costs, int houseLevel,
      List<List<Integer>> memo) {
    List<Integer> rowList = new ArrayList<>();
    if (houseLevel <= 0) {
      for (int i = 0; i < costs[0].length; i++) {
        rowList.add(costs[0][i]);
      }
      memo.add(rowList);
      return memo.get(0);
    }
    // memorized
    if (memo.size() > houseLevel) {
      return memo.get(houseLevel);
    }
    List<Integer> prevRow = helper(costs, houseLevel - 1, memo);
    for (int color = 0; color < 3; color++) {
      // c=0 -> 1, c=1 -> 2, c=2 -> 0;
      int prevColor1 = (color == 0) ? 1 : (color == 1 ? 2 : 0);
      // c=0 -> 2, c=1 -> 0, c=2 -> 1; 
      // pc1 & pc2: c=0 {1,2}, c=1 {2,0}, c=2 {0,1}
      int prevColor2 = (color == 0) ? 2 : (color == 1 ? 0 : 1);
      // choose the smaller prevColor cost.
      int prevSmaller = Math.min(prevRow.get(prevColor1),
          prevRow.get(prevColor2));
      int curCost = costs[houseLevel][color] + prevSmaller;
      rowList.add(curCost);
    }
    memo.add(rowList);
    return memo.get(houseLevel);
  }
  
}