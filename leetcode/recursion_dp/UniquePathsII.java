/**
63. Unique Paths II
A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

Now consider if some obstacles are added to the grids. How many unique paths would there be?



An obstacle and empty space is marked as 1 and 0 respectively in the grid.

Note: m and n will be at most 100.

Example 1:

Input:
[
  [0,0,0],
  [0,1,0],
  [0,0,0]
]
Output: 2
Explanation:
There is one obstacle in the middle of the 3x3 grid above.
There are two ways to reach the bottom-right corner:
1. Right -> Right -> Down -> Down
2. Down -> Down -> Right -> Right
*/

/**
Solution: DP
How to arrive:
* Same logic as Unique Paths, just when we encounter block, we set that grid to 0, meaning cannot reach that grid.
* **special cases**: 
	* if [0,0] starting point is blocked, then there is no path, so return 0.
	* If [m-1, n-1] target is blocked, then there is no path, so 0 as well.
	* For the 1st row and col, it has only 1 path, if one grid is blocked, then rest of the row or col is blocked as well. Init them into 0s.
* Now we have the base cases, just calc by left + top if not blocked; if cur grid is blocked set to 0;
* return last grid DP.
* Time: O(mn);
* Space: O(mn);

Solution: DP optimized space, using obstacleGrid as DP.
How to arrive:
* Notice obstacleGrid is also a 2d array. Can we use it as DP?
* In order to use it as DP, we need to init its 1st row and col, and handle its blocked (1) cases.
* We have to find all the blocks in 1st row and col and set them to 0s for DP, and any not blocked grid before the block happen to 1 like usual.
* Then, we just iterate the same way, if it's block (1) set to 0s, if not adding left to top. 
* Don't worry about changing the 1s blocks into 0, b/c we only iter through each grid once to set it's value, So 1s blocks becomes 0 will only be used as DP later. Don't have to check if it's block again.
* Time: O(mn);
* Space: O(1);

*/

public class UniquePathsII {

	/**
	 * Solution: DP 2d array.
	 * Time: O(mn)
	 * Space: O(mn)
	 */
	public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    if (obstacleGrid == null || obstacleGrid.length == 0) {
      return 0;
    }
    int m = obstacleGrid.length;
    int n = obstacleGrid[0].length;
    // dp col by col, height of grids
    int [][] dp = new int[m][n];
    // if encounter block, it blocks rest of the path in this row.
    for (int i = 0; i < m; i++) {
      if (obstacleGrid[i][0] == 1) {
        break;
      }
      dp[i][0] = 1;      
    }
    // if encounter block, it blocks rest of the path in this col.
    for (int j = 0; j < n; j++) {
      if (obstacleGrid[0][j] == 1) {
        break;
      }
      dp[0][j] = 1;
    }
    for (int r = 1; r < m; r++) {
      for (int d = 1; d < n; d++) {
        if (obstacleGrid[r][d] != 1) {
          dp[r][d] = dp[r - 1][d] + dp[r][d - 1];
        } else {
          dp[r][d] = 0;
        }
      }
    }
    return dp[m - 1][n - 1];
  }

  /**
	 * Solution: Optimized Space by using obstacleGrid as DP.
	 * Time: O(mn)
	 * Space: O(1)
	 */
  public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    if (obstacleGrid == null || obstacleGrid.length == 0) {
      return 0;
    }
    // If the starting cell has an obstacle, then simply return as there would be
    // no paths to the destination.
    if (obstacleGrid[0][0] == 1) {
      return 0;
    }
    
    int m = obstacleGrid.length;
    int n = obstacleGrid[0].length;
    boolean blocked = false;
    // Number of ways of reaching the starting cell = 1.
    obstacleGrid[0][0] = 1;
    // if encounter block, it blocks rest of the path in this row. Set 0s.
    for (int i = 1; i < m; i++) {
      // check for block
      if (obstacleGrid[i][0] == 1) {
        // turn block val 1 into 0 for DP purposes.
        obstacleGrid[i][0] = 0;
        blocked = true;
      } else if (!blocked) {
        // not blocked init 1 for DP.
        obstacleGrid[i][0] = 1;
      }
    }
    
    blocked = false;
    // if encounter block, it blocks rest of the path in this col.
    for (int j = 1; j < n; j++) {
      if (obstacleGrid[0][j] == 1) {
        obstacleGrid[0][j] = 0;
        blocked = true;
      } else if (!blocked) {
        obstacleGrid[0][j] = 1;
      } 
    }

    for (int r = 1; r < m; r++) {
      for (int d = 1; d < n; d++) {
        if (obstacleGrid[r][d] != 1) {
          obstacleGrid[r][d] = obstacleGrid[r - 1][d] + obstacleGrid[r][d - 1];
        } else {
          // make obstacles 1 become 0 in path ways to use as DP;
          // don't worry about future encounter, since we only traverse this grid once.
          obstacleGrid[r][d] = 0;
        }
      }
    }
    return obstacleGrid[m - 1][n - 1];
  }

}