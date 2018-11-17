/**
44. Wildcard Matching
Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*'.

'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).
The matching should cover the entire input string (not partial).

Note:

s could be empty and contains only lowercase letters a-z.
p could be empty and contains only lowercase letters a-z, and characters like ? or *.

Example 1:

Input:
s = "aa"
p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input:
s = "aa"
p = "*"
Output: true
Explanation: '*' matches any sequence.
Example 3:

Input:
s = "cb"
p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
Example 4:

Input:
s = "adceb"
p = "*a*b"
Output: true
Explanation: The first '*' matches the empty sequence, while the second '*' matches the substring "dce".
Example 5:

Input:
s = "acdcb"
p = "a*c?b"
Output: false
*/

/**
Solution: Dynamic Programming, 2d array table.
Ex: String s = "abefgchb"; p = "*ab*c?b";
		''		a				b			e				f 		 g			c      h      b	
''[true, false, false, false, false, false, false, false, false],
* [true, true,  true, true, true, true, true, true, true], 
a [false, true, false, false, false, false, false, false, false], 
b [false, false, true, false, false, false, false, false, false], 
* [false, false, true, true, true, true, true, true, true], 
c [false, false, false, false, false, false, true, false, false], 
? [false, false, false, false, false, false, false, true, false], 
b [false, false, false, false, false, false, false, false, true]

How to Arrive:
* To use dynamic programming we need to ask the Question
	*  **If we know the prev smaller problem's ans, can we use it to get cur?**
		*  Ex: s = "adceb" p="ad* b", if we know 'adce' matches 'ad*' Can we get ans for 'adceb' and p='ad* b'?
		*  Answer: yes, we just need to compare last char. So we can use Dynamic Programming.
* **key**: Now to Memorize the prev ans, we can use a table. dp[p][s];
* dp[p][s] will contain all the boolean vals of all combo possibilities. So when we get to the last elm of the 2d array, it is the ans we looking for.
* What are the initial (base case) will need to set up for later comparison?
	* **Key**: consider when p='' and s=''; It is True; match.
	* **Key**: consider p='* ' or '*** ...'; having * s at beginning, then s='' will match. True;
	* All other case for s='' or p='' are FALSE;
* How do we consider True or False? Now that we can use the init to carry over. 
	* case p='a-z', if p == s true, then consider prev sequence matches. \ dir. If p != s false.
	* case p='?', true to any s chars, so just consider the prev sequence. \ dir.
	* case p='* ', 2 possibilities, Can match Empty '' or 1+ more chars.
		* 1. Empty '', means we skip this, so if prev Pat (upper) w/o * is True, then it is true.
			* Ex: s= 'ab' p='ab* '; ab matches ab. So this will match as well.
			* check prev pat val, dp[p-1][s] upper | direction. 
		* 2. 1+ more chars, means if the beginning string matches, then chars afterward will all matches.
			* Ex: s= 'abcde' p='ab* '; ab == ab; cbe matches * ; anything after ab is a match.
			* check prev s val, dp[p][s-1] <- direction.
* Time: O(p * s);
* Space: O(p * s);

Solution 2: Using 2 pointers.
How to Arrive:
* 'a-z' and '?' are easy to manage, just pass over when == or ?;
* '* ' is the tricky one we need to think about how to handle.
	* '* ' matches anything, So the key is whether the chars after '* ' matches.
	* Ex: there can be s: 'abcdechecd', p: ab * cd; Now there are 3 choices of 'c';
	* We need to think about check all possibilities for the char after '* ';
	* for P: 'ab * cd'; we look for 'c' in S: 'abcdechecd'. We check for 1st 'c' if leads to ans, then sec, then all 'c's afterward. 'cdechecd' != 'cd', 'checd' != 'cd', 'cd' == 'cd' found.
	* So when we found '* ', we need a pointer on char after '* ' in P to compare to S chars. In this case 'c', and comparing 'cd' to S.
	* And also a pointer to traverse 'c' in S. If not workout, come back and go to the next 'c'; Until found an ans or loop out of length.
* We loop until S is finished traverse, now if it is match, P would also finished traverse, unless there are '* 's at the end of P.
* So, we also consider the posibility of last char == '* '; Just iter over it. 
* Now both P and S are finished iteration, We found Match.
* Time: O(s); eventhough when '* ' we need to traverse different possibility, but it will aways be < s;
* Space: O(1);
*/

import java.util.*;
import java.io.*;

class WildcardMatching {

  public static boolean isMatch(String s, String p) {
    char [] schars = s.toCharArray();
    char [] pchars = p.toCharArray();
    boolean [][] dp = new boolean[pchars.length + 1][schars.length + 1];
    
    // init S="" and P=""; true
    dp[0][0] = true;
    // init null/0 S="" and P vals.
    for (int i = 1; i <= pchars.length; i++) {
      // S='' only when * at the beginning can match.
      if (pchars[i - 1] == '*') {
        // ex: p = "*..." or "***.."
        if (i == 1 || (i > 1 && dp[i - 1][0])) {
          dp[i][0] = true;
        }
      } else {
        break;
      }
    }
    // start with 1, use S="" and P="" row col as init.
    for (int pi = 1; pi <= pchars.length; pi++) {
      for (int si = 1; si <= schars.length; si++) {
        // if p is letter
        if (pchars[pi - 1] >= 'a' && pchars[pi - 1] <= 'z') {
          // if p char == s char, check \ dir
          if (pchars[pi - 1] == schars[si - 1]) {
            dp[pi][si] = dp[pi - 1][si - 1];
          }
        } else if (pchars[pi - 1] == '?') {
          // if p='?' char, it matches any char, so check \ dir
          dp[pi][si] = dp[pi - 1][si - 1];
        } else {
          // if p='*', either use as '' empty, or match any ith chars.
          // | dir dp[pi - 1][si] means '*' as empty, it just carry over the prev val of P.
          // <- dp[pi][si - 1] means match 1 or more chars, as long as the start string matches, all afterward are matches.
          dp[pi][si] = dp[pi - 1][si] || dp[pi][si - 1];
        }
      }
    }
    System.out.println(Arrays.deepToString(dp));
    return dp[pchars.length][schars.length];
  }

  /**
   * Solution 2: use 2 pointers to compare p and s
   */
  public boolean isMatch2(String s, String p) {
    char [] sch = s.toCharArray();
    char [] pch = p.toCharArray();
    // p and s pointers to compare
    int si = 0;
    int pi = 0;
    // place keeper for *
    int starIdx = -1;
    // find char match after * in S.
    int match = -1;
    // matching all sequence chars
    while (si < sch.length) {
      // case '?' or 'a-z' ==
      if (pi < pch.length && (pch[pi] == '?' || pch[pi] == sch[si])) {
        si++;
        pi++;
      } else if (pi < pch.length && pch[pi] == '*') {
        // case '*', keep pos so we can recompare different possibilities
        starIdx = pi;
        // index for finding char right after '*', ex: '*c..' find 'c';
        match = si;
        // go to next p char start compare for possibilities
        pi++;
      } else if (starIdx != -1) {
        // going back to where last * is, b/c cur match not work out.
        // recompare, pi goes back to char right after *
        pi = starIdx + 1;
        // iter until finding next match char for char next to '*';
        match++;
        // si update so first condition will compare pch[pi] == sch[si].
        // it will goes back to this condition until find a possible match or next '*' appear.
        si = match;
      } else {
        // pch[pi] != sch[si], pch[pi] != '?', pch[pi] != '*'
        return false;
      }
    }
    // check if '*' is last char/chars
    while (pi < pch.length && pch[pi] == '*') {
      pi++;
    }
    // p at the end, means match.
    return pi == pch.length;
  }

  public static void main(String[] args) {
  	String s = "abefgchb";
  	String p = "*ab*c?b";
  	boolean ans = isMatch(s, p);
  	System.out.println("ans " + ans);
  }
}