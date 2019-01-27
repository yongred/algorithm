/**
119. Edit Distance
Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)

You have the following 3 operations permitted on a word:

Insert a character
Delete a character
Replace a character
Example
Given word1 = "mart" and word2 = "karma", return 3.
*/

/*
<- + 1 insert, | + 1 del, \ + 1 = replace;
\ = same char;

   "" k a r m a
"" 0  1 2 3 4 5
m  1  1 2 3 3 4
a  2  2 1 2 3 3
r  3  3 2 1 2 3
t  4  4 3 2 3 3
*/

public class EditDistance {

  /**
   * @param word1: A string
   * @param word2: A string
   * @return: The minimum number of steps.
   */
  public int minDistance(String word1, String word2) {
    int n1 = word1.length();
    int n2 = word2.length();
    int[][] dp = new int[n1 + 1][n2 + 1];
    // base case "" -> "" 0 step
    // init 1st col and row to i
    for (int i = 1; i <= n1; i++) {
      // 1st col, del i times, from word1 -> "";
      dp[i][0] = i;
    }
    for (int i = 1; i <= n2; i++) {
      // 1st row, insert i times, from "" -> word2;
      dp[0][i] = i;
    }
    // dp calc
    for (int i = 1; i <= n1; i++) {
      for (int j = 1; j <= n2; j++) {
        // check char ==; same as \
        if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          // 1+ min steps from \ | <- direction, replace, del, insert;
          dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], 
              Math.min(dp[i - 1][j], dp[i][j - 1]));
        }
      } 
    }
    return dp[n1][n2];
  }
  
  
  
}