/**
Regular Experssion

Leetcode 相似问题:
• Leetcode #44 Wild Card Matching
• Leetcode #10 Regular Experssion Matching
https://www.mitbbs.com/article_t/JobHunting/33067863.html

• *: 0个或多个之前字符
• .: 任何字符
• +:1个或多个之前字符
另外一点不太清楚是这个加号的作用,如果是 aa 和 +a 这种算是match到了吗?就是加号前面是
空。
应该是 a+吧

https://www.glassdoor.com/Interview/1-find-all-the-combinations-of-a-string-in-lowercase-and-
uppercase-For-example-string-ab-and-gt-ab-Ab-aB-AB-QTN_582954.htm

Implement a simple regex parser which, given a string and a pattern, returns a boolean indicating whether the input matches the pattern.

By simple, we mean that the regex can only contain special character:

* (star) The star means what you'd expect, that there will be zero or more of previous character in that place in the pattern.
. (dot) The dot means any character for that position.
+ (plus). The plus means one or more of previous character in that place in the pattern.

*/

import java.util.*;
import java.io.*;

public class RegularExpression {
	
	/**
	 * Solution 1: DP bottom-up.
	 * Time: O(S * P)
	 * Space: O(S * P)
	 */
	public static boolean regexMatch(String s, String p) {
		char [] sch = s.toCharArray();
    char [] pch = p.toCharArray();
    boolean [][] dp = new boolean[pch.length + 1][sch.length + 1];
    // init P'' match S''
    dp[0][0] = true;
    // init P for S='';
    for (int i = 1; i <= pch.length; i++) {
      // if skip last char is true, ex: P= a* or .*b*, S='';
      // **.. is false
      if (pch[i - 1] == '*') {
      	// no need to check i >=2, b/c '*' or 'a**' or '*+' is not valid Regex.
      	// need a letter or '.' before * or +;
        dp[i][0] = dp[i - 2][0];
      }
    }

    // fill table using init vals
    for (int pi = 1; pi <= pch.length; pi++) {
      for (int si = 1; si <= sch.length; si++) {
        // case '.' or pChar == sChar, val = prev \ dir.
        if (pch[pi - 1] == '.' || pch[pi - 1] == sch[si - 1]) {
          dp[pi][si] = dp[pi - 1][si - 1];
        } else if (pch[pi - 1] == '*') {
          if (dp[pi - 2][si]) {
            // * can cancel last char. So carry down pi-2
            dp[pi][si] = dp[pi - 2][si];
          } else if (pch[pi - 2] == sch[si - 1] || pch[pi - 2] == '.') {
            // compare * prev char to S cur, and look at prev S, si-1;
            // ex: S=aa P=a*; a = a so aa = a*;
            dp[pi][si] = dp[pi][si - 1];
          }
        } else if (pch[pi - 1] == '+') {
        	if (dp[pi - 1][si]) {
        		// Ex: first match a+ = aaa;
        		dp[pi][si] = dp[pi - 1][si];
        	} else if (pch[pi - 2] == sch[si - 1] || pch[pi - 2] == '.') {
        		// prev pchar == cur schar or pchar = '.';
        		// Ex: a+ = aaa; .+ = aaa;
        		dp[pi][si] = dp[pi][si - 1];
        	}
        }
      }
    }
    return dp[pch.length][sch.length];
	}

	/**
   * Solution: DP, top-down
   * Time: O(S * P)
   * Space: O(S * P)
   */
  public static boolean regexMatch2(String s, String p) {
    // memo, 0 not assigned, 1 is true, -1 is false;
    int[][] memo = new int[p.length() + 1][s.length() + 1];
    return helper(s, p, s.length(), p.length(), memo);
  }
  
  public static boolean helper(String s, String p, int si, int pi, int[][] memo) {
    if (memo[pi][si] != 0) {
      return (memo[pi][si] == -1) ? false : true;
    }
    // P "" match S "";
    if (si == 0 && pi == 0) {
      memo[pi][si] = 1;
      return true;
    }
    boolean res = false;
    if (pi == 0) {
      memo[pi][si] = -1;
      return false;
    }
    if (si == 0) {
      if (p.charAt(pi - 1) == '*') {
        res = helper(s, p, si, pi - 2, memo);
      } else {
        res = false;
      }
      memo[pi][si] = res ? 1 : -1;
      return res;
    }
    // '.' case, chars == case
    if (s.charAt(si - 1) == p.charAt(pi - 1) || p.charAt(pi - 1) == '.') {
      // compare last chars.
      res = helper(s, p, si - 1, pi - 1, memo);
    } else if (p.charAt(pi - 1) == '*') {
      // check pat-2, for cancel out prev char.
      if (helper(s, p, si, pi - 2, memo)) {
        res = true;
      } else if (p.charAt(pi - 2) == s.charAt(si - 1) || p.charAt(pi - 2) == '.') {
        // if schar == prev pchar, char before *; a* = aa; or .* = aa;
        // just check prev s matches
        res = helper(s, p, si - 1, pi, memo);
      }
    } else if (p.charAt(pi - 1) == '+') {
    	// check prev pattern matches cur S.
    	if (helper(s, p, si, pi - 1, memo)) {
    		res = true;
    	} else if (p.charAt(pi - 2) == s.charAt(si - 1) || p.charAt(pi - 2) == '.') {
    		// if schar == prev pchar, char before +; a+ = aa; or .+ = aa;
        // just check prev s matches
        res = helper(s, p, si - 1, pi, memo);
    	}
    }
    memo[pi][si] = res ? 1 : -1;
    return res;
  }


	public static void main(String[] args) {
		String s = "aaaabbbccbcc";
		String p = ".a*b+cc.c+";
		if (regexMatch(s, p)) {
			System.out.println("Is match");
		} else {
			System.out.println("Not match");
		}

		if (regexMatch2(s, p)) {
			System.out.println("Is match2");
		} else {
			System.out.println("Not match2");
		}
	}

}