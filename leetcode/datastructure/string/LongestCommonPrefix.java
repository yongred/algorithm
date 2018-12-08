/**
14. Longest Common Prefix
Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string "".

Example 1:

Input: ["flower","flow","flight"]
Output: "fl"
Example 2:

Input: ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
Note:

All given inputs are in lowercase letters a-z.
*/

/**
Solution 1: Vertical Scan, traverse chars, see if every str have that char, yes add to stringBuilder.
Ex: [abb, ab, abcd]; use 1st str, 1st char = a; in every string. Res add "a";
1st str sec char = 'b'; all have, res add 'b' = 'ab'; 
1st str 3rd char = b; 2nd string ab len < 3; 
Done. Res = "ab";

How to arrive:
* prefix will be at most the shortest str in arr. 
* So we just scan 1 char at a time, when there is a mismatch, we are done scaning.
* Time: O(n * m); strs * shortest string chars.
* Space: O(m); shortest string len. Using StringBuilder. Using str.substring will be space: O(1), java 7 substring time is O(n), but we can just remember the lastIndex, and do strs[0].substring(0, last+1);

----------
Solution 2: Horizontal Scan, set longest possible prefix to 1st str, reduce overtime.
Ex: [abb, ab, abc] prefix = abb, ab !contains abb, abb - b = ab; ab contains ab. prefix = ab;
abc contains ab; Return "ab";

How to arrive:
* At most, prefix is the shortest str. So we use 1st str and compare with all other strings.
* As we compare, we reduce the chars 1 by 1, until all strs have this substring.
* Use indexOf(res) != 0 to check is it is prefix or not.
* Time: O(n * l); strs * longest prefix, Worst case compares all chars in all strs (when all strs are same) IndexOf compares every single chars. Note: Java7 substring() is O(n).
* Space: O(1)
*/

class LongestCommonPrefix {

	/**
	 * Solution: Vertical Scan, traverse chars, see if every str have that char, yes add to stringBuilder.
	 * Time: O(n * m); strs * shortest string chars.
	 * Space: O(m); shortest string len if use StringBuilder. Use substring at the end, remember lastMatchIndex O(1);
	 */
  public String longestCommonPrefix(String[] strs) {
    if (strs.length == 0) {
      return "";
    }
    // last index of matching char.
    int lastIndex = -1;
    // cur char pos.
    int c = 0;
    // make sure str have c pos.
    while (c < strs[0].length()) {
      char curChar = strs[0].charAt(c);
      // strs index.
      int s = 1;
      while (s < strs.length) {
        String curStr = strs[s];
        if (c >= curStr.length()) {
          break;
        }
        if (curStr.charAt(c) != curChar) {
          break;
        }
        s++;
      }
      // curchar in all strs pos c.
      if (s >= strs.length) {
        lastIndex = c;
      } else {
        break;
      }
      c++;
    }
    
    if (lastIndex == -1) {
      return "";
    }
    return strs[0].substring(0, lastIndex + 1);
  }

  /**
   * Solution: Horizontal Scan, set longest possible prefix to 1st str, reduce overtime.
   * Time: O(n * m); strs * longest string chars. Note: Java7 substring() is O(n)
   * Space: O(1)
   */
  public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) {
      return "";
    }
    // longest possible prefix, reduce over time.
    String res = strs[0];
    // check all strs, reduce res until all strs have that substr.
    for (int i = 1; i < strs.length; i++) {
      // if not a prefix of cur str.
      while (strs[i].indexOf(res) != 0) {
        // reduce size by 1.
        res = res.substring(0, res.length() - 1);
      }
    }
    return res;
  }
}