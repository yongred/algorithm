/**
10. Regular Expression Matching
Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.

'.' Matches any single character.
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).

Note:

s could be empty and contains only lowercase letters a-z.
p could be empty and contains only lowercase letters a-z, and characters like . or *.
Example 1:

Input:
s = "aa"
p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input:
s = "aa"
p = "a*"
Output: true
Explanation: '*' means zero or more of the precedeng element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
Example 3:

Input:
s = "ab"
p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
Example 4:

Input:
s = "aab"
p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore it matches "aab".
Example 5:

Input:
s = "mississippi"
p = "mis*is*p*."
Output: false
*/

/**
Solution: DP 2d array table.
How to Arrive:
* Similar to 44 Wildcard Matching.
* Can we use DP? Ask this question, If we know the prev Ans, can we use it to get cur Ans?
  * Ex: S='abbbc', P='ab * c'; If we know abbb = ab * ; Then we just need to check c == c;
  * Meaning we just need to find possibilities of each cases, 'a-z', '.', and '* ';
* '.' and 'a-z' matching are straight forward, when '.' or 'a-z' == 'a-z' True. Just look at [pi-1][si-1] pos, \ dir carry over the val.
* The main problem is '* '. What are the cases for '* '?
  * Case 1: 0 or cancel out the prev char; Ex: ab* == a; 
    * That means cur Patt can count the same as P-2 pos Patt. So we just check 2 pos back.
    * Notice for '* ', it doesn't require \ dir [pi-1][si-1] to be true, b/c it has the ability to cancel last char.
  * Case 2: 1+ more repeating the prev char. Ex: ab* == abbb;
    * Condition to meet: if P-1 (prev P) is == S cur char.  b == b in this case OR P-1 is '.' which matches any char;
    * Now we have to check if the prev S is a match;
      *  Ex: ab* == a, then ab* == ab, then ab* == abb; 
      *  But, ab* !=  c, then ab* != cb, so ab* != cbb;  
    * Meaning the beginning part before the 'b* ' patt has to match first for b afterwards to match.
* Time: O(PS);
* Space: O(PS);
*/
import java.util.*;
import java.io.*;

class RegularExpressionMatching {

  public boolean isMatch(String s, String p) {
    char [] sch = s.toCharArray();
    char [] pch = p.toCharArray();
    boolean [][] dp = new boolean[pch.length + 1][sch.length + 1];
    // init P'' match S''
    dp[0][0] = true;
    // init P for S='';
    for (int i = 1; i <= pch.length; i++) {
      if (pch[i - 1] == '*') {
        // if skip last char is true, ex: P= a* or .*b*, S='';
        // **.. is false
        if (i > 1 && pch[i - 2] != '*' && dp[i - 2][0]) {
          dp[i][0] = true;
        }
      }
    }
    
    // fill table using init vals
    for (int pi = 1; pi <= pch.length; pi++) {
      for (int si = 1; si <= sch.length; si++) {
        // case '.' or pChar == sChar, val = prev \ dir.
        if (pch[pi - 1] == '.' || pch[pi - 1] == sch[si - 1]) {
          dp[pi][si] = dp[pi - 1][si - 1];
        } else if (pch[pi - 1] == '*') {
          if (pi > 1 && dp[pi - 2][si]) {
            // * can cancel last char. So carry down pi-2
            dp[pi][si] = true;
          } else if (pi > 1 && (pch[pi - 2] == sch[si - 1] || pch[pi - 2] == '.')) {
            // compare * prev char to S cur, and look at prev S, si-1;
            // ex: S=aa P=a*; a = a so aa = a*;
            dp[pi][si] = dp[pi][si - 1];
          }
        }
      }
    }
    return dp[pch.length][sch.length];
  }
  
}