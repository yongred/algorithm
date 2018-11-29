
/**
Solution: DP, 2d Array, bottom-up.
w1 = "adceg" w2="abcfg"
	  "" a  b  c  f  g
"" [0, 1, 2, 3, 4, 5], 
a	 [1, 0, 1, 2, 3, 4], 
d  [2, 1, 1, 2, 3, 4], 
c  [3, 2, 2, 1, 2, 3], 
e  [4, 3, 3, 2, 2, 3], 
g  [5, 4, 4, 3, 3, 2]

How to Arrive:
* If we know the min ope for prev char substr. We just need to decide cur char is Insert, Delete, or Replace, Or remain the the same (no ope needed);
* To know the prev min ope, it is also either Insert, Delete or Replace, or Remain. So we just know the min out of the opes.
* That give us 2 cases: 
	* w1char == w2char, 0 + preMin; no ope needed; Remain.
	* w1char != w2char, 1 + preMin; Delete, Insert, or Replace.
* Base case:
	* we know w1="" & w2=""; 0 ope needed.
	* w1="" & w2="whatever"; we do Insert opes m (w2len) times;
	* w1="whatever" & w2=""; we do Delete opes n (w1len) times;
* Now we can construct the DP 2d Table, as above.
* DP[0][0] is "" & "" so 0; 
* DP[w1][0] is all DEL, it just +1 to top grid (prev w1) | dir.
* DP[0][w2] is all Insert, just +1 to left grid (prev w2) -> dir.
* Now we have 1st row and col, we can figure out the rest using them.
* DEL = upper + 1, | dir; B/c del char means w1 remove cur to become prev w1substr. Ex: ab - b => a;
* INSERT = left + 1, -> dir; B/c insert means cur w1 + char to become cur w2; so just w2 moved. Ex: a + (b) => ab;
* REPLACE = (r-1, d-1) upperLeft +1, \ dir; Transform cur w1char => w2char. Ex: a(d -> b) => ab;
* REMAIN = (r-1, d-1) upperLeft, \ dir; Same as prev w1 & w2;
* Conclude 
```
// when chars ==, no ope is needed, carryOver prev.
if (w1[r - 1] == w2[c - 1]) {
	dp[r][c] = dp[r - 1][c - 1];
} else {
	// when w1 != w2 char, find min of replace \, insert ->, delete |;
	int minOpe = Math.min(dp[r - 1][c - 1], Math.min(dp[r - 1][c], dp[r][c - 1]));
	// perform ope (+1) to the Min ope required.
	dp[r][c] = minOpe + 1;
}
```
* Time: O(nm);
* Space: O(nm);

Solution: Memorization, recursive top-down.
How to arrive:
* Use the same base case and recursive case as above.
* Base case:
	* we know w1="" & w2=""; 0 ope needed.
	* w1="" & w2="whatever"; we do Insert opes m (w2len) times;
	* w1="whatever" & w2=""; we do Delete opes n (w1len) times;
```
// "" == "", 0 ope.
    if (r <= 0 && c <= 0) {
      return 0;
    }
    // memorized val.
    if (memo[r][c] != -1) {
      return memo[r][c];
    }
    // 1st row, w1='', only from insert. ->
    if (r == 0) {
      memo[r][c] = helper(w1, w2, r, c - 1, memo) + 1;
      return memo[r][c];
    }
    // 1st col, w2='', only from delete. |
    if (c == 0) {
      memo[r][c] = helper(w1, w2, r - 1, c, memo) + 1;
      return memo[r][c];
    }
```
* Recursive case:
	* w1char == w2char, 0 + preMin; no ope needed; Remain.
	* w1char != w2char, 1 + preMin; Delete, Insert, or Replace.
```
// if w1char == w2char, copy prev w1 w2
    if (w1[r - 1] == w2[c - 1]) {
      memo[r][c] = helper(w1, w2, r - 1, c - 1, memo);
      return memo[r][c];
    }
    // when w1 != w2 char, find min of replace \, insert ->, delete |;
    int prevMin = Math.min(helper(w1, w2, r - 1, c - 1, memo), 
        Math.min(helper(w1, w2, r - 1, c, memo), helper(w1, w2, r, c - 1, memo)));
    memo[r][c] = prevMin + 1;
```
* Time: O(nm);
* Space: O(nm);

*/ 

class EditDistance {

	/**
	 * Solutioin: DP bottom-up, 2d array.
	 * Time: O(nm);
	 * Space: O(nm); 
	 */
  public int minDistance(String word1, String word2) {
    int n1 = word1.length();
    int n2 = word2.length();
    char[] w1 = word1.toCharArray();
    char[] w2 = word2.toCharArray();
    
    int[][] dp = new int[n1 + 1][n2 + 1];
    // 1st col init, w2 = '', remove opes.
    for (int r = 1; r <= n1; r++) {
      dp[r][0] = dp[r - 1][0] + 1;
    }
    // 1st row init, w1 = '', insert opes.
    for (int c = 1; c <= n2; c++) {
      dp[0][c] = dp[0][c - 1] + 1;
    }
    
    for (int r = 1; r <= n1; r++) {
      for (int c = 1; c <= n2; c++) {
        // when chars ==, no ope is needed, carryOver prev.
        if (w1[r - 1] == w2[c - 1]) {
          dp[r][c] = dp[r - 1][c - 1];
        } else {
          // when w1 != w2 char, find min of replace \, insert ->, delete |;
          int minOpe = Math.min(dp[r - 1][c - 1], Math.min(dp[r - 1][c], dp[r][c - 1]));
          // perform ope (+1) to the Min ope required.
          dp[r][c] = minOpe + 1;
        }
      }
    }
    return dp[n1][n2];
  }

  /**
   * SOlution: Memorization, recursive top-down
   * TIme: O(mn)
   * Space: O(mn)
   */
  public int minDistance(String word1, String word2) {
    char[] w1 = word1.toCharArray();
    char[] w2 = word2.toCharArray();
    int[][] memo = new int[w1.length + 1][w2.length + 1];
    for (int[] row : memo) {
      Arrays.fill(row, -1);
    }
    int res = helper(w1, w2, w1.length, w2.length, memo);
    return res;
  }
  
  public int helper(char[] w1, char[] w2, int r, int c, int[][] memo) {
    // "" == "", 0 ope.
    if (r <= 0 && c <= 0) {
      return 0;
    }
    // memorized val.
    if (memo[r][c] != -1) {
      return memo[r][c];
    }
    // 1st row, w1='', only from insert. ->
    if (r == 0) {
      memo[r][c] = helper(w1, w2, r, c - 1, memo) + 1;
      return memo[r][c];
    }
    // 1st col, w2='', only from delete. |
    if (c == 0) {
      memo[r][c] = helper(w1, w2, r - 1, c, memo) + 1;
      return memo[r][c];
    }
    // if w1char == w2char, copy prev w1 w2
    if (w1[r - 1] == w2[c - 1]) {
      memo[r][c] = helper(w1, w2, r - 1, c - 1, memo);
      return memo[r][c];
    }
    // when w1 != w2 char, find min of replace \, insert ->, delete |;
    int prevMin = Math.min(helper(w1, w2, r - 1, c - 1, memo), 
        Math.min(helper(w1, w2, r - 1, c, memo), helper(w1, w2, r, c - 1, memo)));
    memo[r][c] = prevMin + 1;
    return memo[r][c];
  }


}