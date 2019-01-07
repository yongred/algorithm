/**
Maximum Minimum Path in Matrix

From leftTop pos to rightBottom pos, only able to move right or down.
Find the minElms in all paths, Then find the maxElm in minElms.
Ex:
{8,4,3,5}
{6,5,9,8}

paths: 
8,4,3,5,8  min=3
8,4,3,9,8  min=3
8,4,5,9,8  min=4
8,6,5,9,8  min=5

Max(3,3,4,5) = 5;
*/

/**
Solution: DP: MaxMini algorithm
Ex:
{8,4,3,5}
{6,5,9,8}
init 1st row and col, only 1 path so just the min of the path.
[8,4,3,3]
[6]
then check up or left path's min, choose the Larger one. Then compare to curGrid val to see if gridVal is the new min
[8,4,3,3]
[6,5,5,5]
Ex: both 8,4,5 and 8,6,5 goes to 5 grid, the maxMini is 6 in (8,6) path. 
Now b/c 8,6,5 goes to 5, 5< 6, the new min for (8,6,5) is 5; so maxMini for (8,6,5) is 5.
So maxMini for (8,4,5) and (8,6,5) is 5;
* Key: We choose the path with the Larger min, then update the min of the choosen path by compare curGridVal.
* Time: O(n * m); linear.
* Space: O(n * m);
*/

public class MaximumMinimumPath {
	
	/**
	 * Solution: DP: MaxMini algorithm
	 * Time: O(n * m); linear.
	 * Space: O(n * m);
	 */
	public static int maxMinPath(int[][] mat) {
		if (mat == null || mat.length == 0) {
			return 0;
		}
		int rows = mat.length;
		int cols = mat[0].length;
		// DP for maxMini of paths to each grid.
		int[][] dp = new int[rows][cols];
		// 1st grid, only have the val itself as the path.
		dp[0][0] = mat[0][0];
		// init 1st rows, cols. Only 1 path, so just the min of that path.
		for (int r = 1; r < rows; r++) {
			// minGrid is either last min, or cur gridVal.
			dp[r][0] = Math.min(dp[r - 1][0], mat[r][0]);
		}
		for (int c = 1; c < cols; c++) {
			dp[0][c] = Math.min(dp[0][c - 1], mat[0][c]);
		}
		// now use top and left grid to find maxMini of curGrid.
		for (int r = 1; r < rows; r++) {
			for (int c = 1; c < cols; c++) {
				// choose the path with the Larger maxMini. From up or left path.
				// Then compare to curGridVal, see if curGridVal is the newMin of the choosen path
				dp[r][c] = Math.min(mat[r][c], Math.max(dp[r - 1][c], dp[r][c - 1]));
			}
		}
		return dp[rows - 1][cols - 1];
	}

	public static void main(String[] args) {
		int[][] mat = {
			{8,4,3,1},
			{6,1,9,8}
		};
		int res = maxMinPath(mat);
		System.out.println(res);
	}

}