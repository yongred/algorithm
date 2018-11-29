/**
97. Interleaving String
Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.

Example 1:

Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
Example 2:

Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false
*/

/**
Solution: DP 2d array. Bottom-up
s1 "aabcc"
s2 "dbbca"
s3 "aadbbcbcac"
		""		 "d"		"b"		 "b"		"c"		 "c"
""[true, false, false, false, false, false], 
a [true, false, false, false, false, false], 
a [true, true, true, true, true, false], 
b [false, true, true, false, true, false], 
c [false, false, true, true, true, true], 
c [false, false, false, true, false, true]

How to arrive:
* Question: use s1 and s2 chars in ASC order to fill s3. So s1 and s2 chars has to be in order in s3.
* Each s3 char is either from s1 or s2, so 2 choices at each pos.
* And choosed s1 + s2 chars == s3 len; Ex: s1="ab" s2="cc" s3="accb"; 2+2=4;
* **Key**: if we know the last pos substr is true. Then all we need to do is see which s1 or s2 char match s3.
* See the table above, **| dir for choosing s1 char, -> dir for choosing s2 char**.
* Go down to get a new char from s1, go right to get new char from s2.
* Left pos is true means: s1 cur pos char was last choosed and matched s3 pos. Now at this pos you can check s2 char == s3 char, if match then this path matches. 
* Top pos is true means: s2 cur pos char was last choosed and matched s3 pos. For this pos you can check s1 char == s3 char.
* Both true means: you can compare it with either one. s1 or s2.
* Base case: 
	* s1"" and s2"" to s3 "", is true, b/c choose either empty == empty.
	* 1st col is just using s1 chars to match s3, only check top is true and if s1 char match.
	* 1st row is just using s2 chars to match s3, only check left is true and if s2 char match.
* Now we have our base case, and init vals. We can fill the table until last grid.
```
// check left path, left char (s1) last used
	if (dp[row][col - 1]) {
		// since s1 char in this pos is used, we need to use s2 top char.
		s2match = (c2[col - 1] == c3[row + col - 1]);
	}
	boolean s1match = false;
	// check top path, s2 char last used.
	if (dp[row - 1][col]) {
		// s2 char in this pos is used (T), we nned to use s1 char.
		s1match = (c1[row - 1] == c3[row + col - 1]);
	}
	// either s1 or s2 char can continue the path.
	dp[row][col] = (s1match || s2match);
```
* Time: O(s1 * s2);
* Space: O(s1 * s2);

*/

class InterleavingString {

  public boolean isInterleave(String s1, String s2, String s3) {
    int n1 = s1.length();
    int n2 = s2.length();
    int n3 = s3.length();
    if (n1 + n2 != n3) {
      return false;
    }

    char[] c1 = s1.toCharArray();
    char[] c2 = s2.toCharArray();
    char[] c3 = s3.toCharArray();
    boolean[][] dp = new boolean[n1 + 1][n2 + 1];
    // "" or "" to "" is true.
    dp[0][0] = true;
    // init 1st col, only using s1 chars to make s3.
    for (int i = 1; i <= n1; i++) {
      dp[i][0] = dp[i - 1][0] && (c1[i - 1] == c3[i - 1]);
    }
    // init 1st row, only using s2 chars to make s3.
    for (int i = 1; i <= n2; i++) {
      dp[0][i] = dp[0][i - 1] && (c2[i - 1] == c3[i - 1]);
    }
    // | dir: use s1 char, -> dir: use s2 char.
    for (int row = 1; row <= n1; row++) {
      for (int col = 1; col <= n2; col++) {
        boolean s2match = false;
        // check left path, left char (s1) last used
        if (dp[row][col - 1]) {
        	// going left to right, meaning choosing a s2 char.
          s2match = (c2[col - 1] == c3[row + col - 1]);
        }
        boolean s1match = false;
        // check top path, s2 char last used.
        if (dp[row - 1][col]) {
        	// going down from top, meaning choosing a s1 char.
          s1match = (c1[row - 1] == c3[row + col - 1]);
        }
        // either s1 or s2 char can continue the path.
        dp[row][col] = (s1match || s2match);
      }
    }
    return dp[n1][n2];
  }
}