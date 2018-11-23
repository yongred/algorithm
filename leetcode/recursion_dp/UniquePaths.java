/**
62. Unique Paths
A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).

How many possible unique paths are there?


Above is a 7 x 3 grid. How many possible unique paths are there?

Note: m and n will be at most 100.

Example 1:

Input: m = 3, n = 2
Output: 3
Explanation:
From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
1. Right -> Right -> Down
2. Right -> Down -> Right
3. Down -> Right -> Right
Example 2:

Input: m = 7, n = 3
Output: 28
*/

/**
Solution: Recursive Cached.
How to arrive:
* From looking at this problem, we can see 2 opions, right or down. Until r = m, and d = n;
* It's easy to recognize the recursive pattern, going down or right at each grid, when r < m, or d < n;
* Base Case: when reach till the target, r=m && d=n; we find 1 path.
* So as the total paths is going right paths + going down paths, until r=m, d=n, return 1;
* That will give us a solution of Time: O(2^(m+n));
* To optimize it, we can use a 2d array as cache.
* There are overlapping grids that we travelled more than once. If we already know from that grid how many paths leads to target, then we don't have to walk again, just add that num to cur.
* The cache[r][d] memorize the path nums for traversed grids, and if encounter that grid again, return the val.
* Time: O(mn);
* Space: O(mn);

Solution 2: DP with 2d array.
m=3, n=3;
[1, 1, 1]
[1, 2, 3]
[1, 3, 6]
How to Arrive:
* Think about if we know the paths to prev grid, can we use it to calc cur gird's paths?
* We just need to add the ways from prev gird to cur gird, to prev total paths.
* There are 2 ways to get to cur gird, either from left or top.
* So, if there are 2 ways to get to top grid from beginning, and 1 way to get to left gird, then to get to cur grid we just need to add top grid paths to left gird paths = 3;
* It means you can reach cur grid from top using 2 paths, and from left 1 path.
* Initialization of the 2d dp[r][d]:
	* Notice 1st col and row is always 1, b/c there is only 1 path to get to the top row, that is by travelling right. And 1st col by travelling down.
	* So 1st col and row init to 1s.
* Now we have the init vals, just loop col by col or row by row, and add top + left = cur.
* Time: O(mn);
* Space: O(mn);

Solution: DP 1d array.
How to Arrive:
* Looking at the ans and graph above, we notice the only infos we need is top gird and left grid.
* We know the 1st one is 1. And array vals are 0s w/o init.
* We are traversing col by col or row by row. Let's do col by col.
* 1st is 1, can we calc r=0, d=1, the grid below 1st?
	* top =1, left should be 0, which is same as cur grid, not initialized array.
	* So 1 + cur grid = 1;
* Now, if we store that in 1d array, consist vals of 1st col.
* And we can do the same with next row d=2. And finish init 1st col.
* Now let's look at 2nd col, we know top is going to be 1. Now That is actually the same val as prev col top. We have that val in 1d array.
* now d=1, 2nd row, for 2nd col. We know top is 1, do we know left? Yes, in 1d array, storing the 1st col vals. So dp[d-1] top=1 + dp[d] prev left =1 = 2 for cur. Now dp[d] becomes 2nd col, row 2 val.
* Now, we can do the same with next row in 2nd col, using top=2 dp[1], and left=1 dp[2];
* Repeat col by col, until dp[n-1];
* Time: O(mn);
* Space: O(n);
*/

class UniquePaths {

	/**
	 * Solution: DP
	 * Time: O(mn)
	 * Space: O(mn)
	 */
	public int uniquePathsDP(int m, int n) {
    if (m <= 0 || n <= 0) {
      return 0;
    }
    int [][] dp = new int[m][n];
    // there is only 1 path to get to any top rows' grid. Only by ->
    for (int i = 0; i < m; i++) {
      dp[i][0] = 1;
    }
    // there is only 1 path to get to any leftmost col gird, only by down.
    for (int j = 0; j < n; j++) {
      dp[0][j] = 1;
    }
    
    for (int r = 1; r < m; r++) {
      for (int d = 1; d < n; d++) {
        // paths from top + paths from left = ways to get to cur pos.
        dp[r][d] = dp[r - 1][d] + dp[r][d - 1];
      }
    }
    
    return dp[m - 1][n - 1];
  }

  /**
	 * Solution: DP optimized space
	 * Time: O(mn)
	 * Space: O(n)
	 */
  public int uniquePathsDPOptimized(int m, int n) {
    if (m <= 0 || n <= 0) {
      return 0;
    }
    int [] dp = new int[n];
    dp[0] = 1;
    for (int r = 0; r < m; r++) {
      for (int d = 1; d < n; d++) {
        // dp[d] is left, dp[d-1] is top; b/c we go col by col, top pos is updated.
        // ex: 1st col, dp[0]=1, dp[1]=0; 0+1=1; Left is already init from last iter
        dp[d] = dp[d] + dp[d - 1];
      }
    }
    
    return dp[n - 1];
  }

	/**
	 * Solution: Recursive, Brute Force.
	 * Time: O(2^(m+n))
	 * Space: O(m+n)
	 */
  public int uniquePathsRecursive(int m, int n) {
    if (m <= 0 || n <= 0) {
      return 0;
    }
    return helper(m, n, 1, 1);
  }
  
  public int helper(int m, int n, int r, int d) {
    if (r >= m && d >= n) {
      return 1;
    }
    
    int paths = 0;
    if (r < m) {
      paths += helper(m, n, r+1, d);
    }
    if (d < n) {
      paths += helper(m, n, r, d+1);
    }
    
    return paths;
  }

  /**
   * Solution: DP Memorization
   * Time: O(mn))
	 * Space: O(mn)
   */
  public int uniquePathsMemorize(int m, int n) {
    if (m <= 0 || n <= 0) {
      return 0;
    }
    int [][] cache = new int[m + 1][n + 1];
    return helper(m, n, 1, 1, cache);
  }
  
  public int helper(int m, int n, int r, int d, int [][] cache) {
    if (r >= m && d >= n) {
      return 1;
    }
    
    if (cache[r][d] > 0) {
      return cache[r][d];
    }
    if (r < m) {
      cache[r][d] += helper(m, n, r+1, d, cache);
    }
    if (d < n) {
      cache[r][d] += helper(m, n, r, d+1, cache);
    }
    
    return cache[r][d];
  }
}