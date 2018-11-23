/**
139. Word Break
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.

Note:

The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.
Example 1:

Input: s = "leetcode", wordDict = ["leet", "code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".
Example 2:

Input: s = "applepenapple", wordDict = ["apple", "pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
             Note that you are allowed to reuse a dictionary word.
Example 3:

Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
Output: false
*/

/**
Solution: DP, 1d array, bottom up
"" c a t s a n d o g
T  F F T T F F T F F
How to arrive:
* **Key**: from looking at the words break, we can see that the next word can be started from either break point from any prev word ended. Or from the start index 0;
* Ex: S = "catsand"; wordDict = "cat, cats, and, sand"; At index 3, we can choose between starting from 0 w/ 'cats' or start from cur 's'; and for last pos, we can choose from start after "cats" for 'and' or start after "cat" for 'sand'; 
* We don't need to check the pos where there is no word, ex: 'ca' 'tsand', since at index 1, 'ca' is not a word, we don't need to check 'tsand', it's already false.
* Now, we know we just need to check prev True positions to cur substrs, if any of thos substrs are in dict, cur pos becomes true.
* **Key**: "" is always true. Empty str always in dict. Base case.
* Use a 1d DP array to memorize all pos T or F.
* Memorize the prev True positions, create substrs from those pos to cur index, check substrs is in Dict.
* Until last char of String. 
* Time: O(n^2); using hash to check inDict cost O(1); Unlike list.contains, O(n);
* Space: O(n);

Solution: Top-down cached memorization.
Ex: "leetcode"
"" = true, "l" = false, "le" = false", "lee" = false; "leet" = true;
"leetc" => "", "leetc" or "leet", "c"; both false.
How to Arrive:
* We are basically checking wordbreak's left substr and right substr, to see if both is true.
* We check from i to n, every possible breakpoint, check left and right substr both have words in dict. Memorize res in cache.
* helper(s.substring(0, i), dict, cache) && helper(s.substring(i), dict, cache);
* Remember to check cur str first, "" and "curstr"; Split between empty str and whole str.
* If cur Str is already memorized in cache, return that cache val.
* We can use a HashMap to store substr as key and boolean as val. HashMap<String, Boolean> cache.
* Time: O(n^2);
* Space: O(n);
*/

class WordBreak {

  public boolean wordBreak(String s, List<String> wordDict) {
    if (s == null) {
      return false;
    }
    // set up hash to check words in dictionary.
    HashSet<String> hash = new HashSet<>(wordDict);
    
    int n = s.length();
    boolean[] dp = new boolean[n + 1];
    // base case "" is always true, empty str in every dict;
    dp[0] = true;
    // loop from dp[1] to dp[n]
    for (int i = 1; i <= n; i++) {
      //check prev pos with words(true), from 0 to only curChar.
      for (int j = 0; j < i; j++) {
        // skip when no word in that pos
        if (!dp[j]) {
          continue;
        }
        int start = j;
        int end = i;
        String word = s.substring(start, end);
        // if word in dictionary, cur pos is true.
        if (hash.contains(word)) {
          dp[i] = true;
          break;
        }
      }
    }
    return dp[n];
  }

  /**
   * Solution: Memorization Cached top-down
   */
  public boolean wordBreak(String s, List<String> wordDict) {
    if (s == null) {
      return false;
    }
    HashSet<String> dict = new HashSet<>(wordDict);
    HashMap<String, Boolean> cache = new HashMap<>();
    return helper(s, dict, cache);
  }
  
  public boolean helper(String s, HashSet<String> dict, HashMap<String, Boolean> cache) {
    if (s == "") {
      return true;
    }
    if (cache.containsKey(s)) {
      return cache.get(s);
    }
    // 0 -> cur substr
    if (dict.contains(s)) {
      cache.put(s, true);
      return true;
    }
    // prev words in dict as breakpoint, find next word
    for (int i = 1; i < s.length(); i++) {
      boolean left = helper(s.substring(0, i), dict, cache);
      if (!left) {
        continue;
      }
      boolean right = helper(s.substring(i), dict, cache);
      // when left and right are both find in Dict
      if (right) {
        cache.put(s, true);
        // just need 1 possibility
        return true;
      }
    }
    // cur pos is have no word found.
    cache.put(s, false);
    return false;
  }
}