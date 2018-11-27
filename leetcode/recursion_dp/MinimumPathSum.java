
/**
Solution: DP regular. 2d array. bottom-up.
Ex: [[1,3,1],[1,5,1],[4,2,1]]
[1, 4, 5],
[2, 7, 6],
[6, 8, 7]
Ans = 7;
How to arrive:
* We can only go right or down, that means only curGrid's top and left grid can reach here.
* That means, If we know the MinPath on Top and Left of cur grid, we can choose the Min between them and add curVal to it. **curGridMin = MIN(TopMin, LeftMin)**;
* This will form a DP 2d array to save prev grid's Min.
* Init the DP's 1st row and col, b/c leftmost can only reach from top, just append topVal to curGrid. Same with 1st row/top row, only depend on left grid.
* Fill the DP by comparing top grid val and left grid val.
* return dp[m - 1][n - 1]; for 0 based index.
* Time: O(n);
* Space: O(n^2);

Solution: DP optimize space O(n);
* Notice we only needs the prev Left and top Min path val.
* So, if we save the prev Col, we know the left vals. And if we init grid from top to bottom we will have the top val available to us.
* Ex: here we know 1st row is just 1+cur. And now we have left and top for the next grid. Min(2, 1+val) + cur.
1, 1+val
2, Min(2, 1+val) + curVal
6
* So, just maintain 1D array DP is enough.
* Initialize the 1st col like before.
* **key**: add a condition when row=0; When calc grids col by col. row=0; curMin = curVal + leftMin.
```
if (row == 0) {
	// prevCol row0 + curCol row0;
	dp[row] += grid[row][col];
	continue;
}
```
* Time: O(n);
* Space: O(n);

Solution: DP, optimized space O(1);
How to arrive:
* Notice **we can use the input param grid[][] as our DP**
* Do the same for DP to grid instead.
* Time: O(n);
* Space: O(1);

*/

class MinimumPathSum {

	/**
	 * Solution: DP, with O(1) Space; Using input param grid as DP.
	 * Time: O(n)
	 * Space: O(1);
	 */
	public int minPathSum(int[][] grid) {
    if (grid.length == 0) {
      return 0;
    }
    // m row, n col.
    int m = grid.length;
    int n = grid[0].length;
    // 1st row's every col vals.
    for (int c = 1; c < n; c++) {
      grid[0][c] += grid[0][c - 1];
    }
    // 1st col's every row vals.
    for (int r = 1; r < m; r++) {
      grid[r][0] += grid[r - 1][0];
    }
    // use init 1st col and row vals to calc min path.
    for (int col = 1; col < n; col++) {
      for (int row = 1; row < m; row++) {
        int minPath = Math.min(grid[row - 1][col], grid[row][col - 1]);
        grid[row][col] += minPath;
      }
    }
    return grid[m - 1][n - 1];
  }

	/**
	 * Solution: DP, with O(n) Space;
	 * Time: O(n)
	 * Space: O(n);
	 */
	public int minPathSum(int[][] grid) {
    if (grid.length == 0) {
      return 0;
    }
    // m row, n col.
    int m = grid.length;
    int n = grid[0].length;
    int[] dp = new int[m];
    // init
    dp[0] = grid[0][0];
    // 1st col's every row vals.
    for (int r = 1; r < m; r++) {
      dp[r] = dp[r - 1] + grid[r][0];
    }
    // use init 1st row vals to calc min path.
    for (int col = 1; col < n; col++) {
      for (int row = 0; row < m; row++) {
        if (row == 0) {
          // prevCol row0 + curCol row0;
          dp[row] += grid[row][col];
          continue;
        }
        // min between left dp[row] or top dp[row-1]
        int minPath = Math.min(dp[row], dp[row - 1]);
        dp[row] = grid[row][col] + minPath;
      }
    }
    return dp[m - 1];
  }

	/**
	 * Solution: regular DP solution, 2d array.
	 * Time: O(n)
	 * Space: O(n^2);
	 */
  public int minPathSum(int[][] grid) {
    if (grid.length == 0) {
      return 0;
    }
    // m row, n col.
    int m = grid.length;
    int n = grid[0].length;
    int[][] dp = new int[m][n];
    // init
    dp[0][0] = grid[0][0];
    // 1st row's every col vals.
    for (int c = 1; c < n; c++) {
      dp[0][c] = dp[0][c - 1] + grid[0][c];
    }
    // 1st col's every row vals.
    for (int r = 1; r < m; r++) {
      dp[r][0] = dp[r - 1][0] + grid[r][0];
    }
    // use init 1st col and row vals to calc min path.
    for (int row = 1; row < m; row++) {
      for (int col = 1; col < n; col++) {
        int minPath = Math.min(dp[row - 1][col], dp[row][col - 1]);
        dp[row][col] = grid[row][col] + minPath;
      }
    }
    return dp[m - 1][n - 1];
  }
}