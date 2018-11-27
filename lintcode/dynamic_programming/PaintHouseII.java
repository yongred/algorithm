/**
516. Paint House II
There are a row of n houses, each house can be painted with one of the k colors. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by a n x k cost matrix. For example, costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2, and so on... Find the minimum cost to paint all houses.

Example
Given n = 3, k = 3, costs = [[14,2,11],[11,14,5],[14,3,10]] return 10

house 0 is color 2, house 1 is color 3, house 2 is color 2, 2 + 5 + 3 = 10

Challenge
Could you solve it in O(nk)?

Notice
All costs are positive integers.
*/

/**
Solution: DP, bottom up, 2d array.
Ex: for [[4,2,1,3],[10,5,5,1],[3,4,10,6]]
[4, 2, 1, 3],
[11, 6, 7, 2],
[5, 6, 12, 12]
Ans = 5;

How to Arrive:
* Same as the logic of Paint House, but if we do the same, for every color of the house check all prev colors, then we will have O(nkk);
* Ex: for 1->n; for 1->k; check prev 1->k; find min prevColor (!= curColorPos) + curColor.
* How do we optimize it?
	* Realize, we don't actually need all prev colors, we just need the prevMin. 
	* If the prevMin == curColorPos, then we are looking for the 2nd prevMin. 
	* Notice this only happens once every row. All others are using PrevMin, only 1 conflict with prevMin uses prev2ndMin.
	* Which means **We only needs prevMin and prev2ndMin **
* Now we can do for 1->n; for 1->k; check if(curColorPos == prevMin) ? prev2ndMin : prevMin; 
* This only takes Time: O(nk);
* After the last row iteration, return dp[n-1][MinIdx]; Ans is the DP last row's MinVal's index.
* Time: O(nk);
* Space: O(1); using input param costs[][] as DP, saved O(nk) space;
*/

public class PainHouseII {
	
  /**
   * @param costs: n x k cost matrix
   * @return: an integer, the minimum cost to paint all houses
   */
  public int minCostII(int[][] costs) {
    int n = costs.length;
    if (costs.length == 0) {
      return 0;
    }
    int k = costs[0].length;
    // maintain 2 min colors.
    int prevMinIdx = 0;
    int prevMinIdx2 = 1;
    for (int c = 1; c < k; c++) {
      if (costs[0][c] < costs[0][prevMinIdx]) {
        prevMinIdx2 = prevMinIdx;
        prevMinIdx = c;
      } else if (costs[0][c] < costs[0][prevMinIdx2]) {
        prevMinIdx2 = c;
      }
    }
    
    for (int i = 1; i < n; i++) {
      int minIdx = 0;
      int minIdx2 = 1;
      for (int c = 0; c < k; c++) {
        int prevMin = (c == prevMinIdx) ? costs[i - 1][prevMinIdx2]
            : costs[i - 1][prevMinIdx];
        costs[i][c] += prevMin; 
        // maintain 1st & 2nd mins
        if (costs[i][c] < costs[i][minIdx]) {
          minIdx2 = minIdx;
          minIdx = c;
        } else if (c > 0 && costs[i][c] < costs[i][minIdx2]) {
          minIdx2 = c;
        }
      }
      prevMinIdx = minIdx;
      prevMinIdx2 = minIdx2;
    }
    // ans is min at last row.
    return costs[n - 1][prevMinIdx];
  }
}