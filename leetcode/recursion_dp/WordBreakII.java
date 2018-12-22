/**
140. Word Break II
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences.

Note:

The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.
Example 1:

Input:
s = "catsanddog"
wordDict = ["cat", "cats", "and", "sand", "dog"]
Output:
[
  "cats and dog",
  "cat sand dog"
]
Example 2:

Input:
s = "pineapplepenapple"
wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
Output:
[
  "pine apple pen apple",
  "pineapple pen apple",
  "pine applepen apple"
]
Explanation: Note that you are allowed to reuse a dictionary word.
Example 3:

Input:
s = "catsandog"
wordDict = ["cats", "dog", "sand", "and", "cat"]
Output:
[]
*/

/**
Solution: recursion + memorization top-down.

How to arrive:
* We just need to find the break between the words.
* We loop through chars, if we find a substr in dict. We find a break, Now just check is the right substring can become sentences (words combos);
* If found sentences in right substr, add leftword to all these sentences to form new sentences.
Ex: "wearethedigit" words=[we,are,digit,the,thedigit];
leftword = we; right sentences: [are the digit, are thedigit];
combine them: [we are the digit, we are thedigit];
* For memorization, we use a hashmap <s, listOfCombos>;
* Base case: for "" s, return [];
* Time: O(2^n); for cases like: s = "aaaaaaaaaaaaa", dict = ["a", "aa", "aaa", "aaaa"]
* Space: O(2^n);
*/

class WordBreakII {

	/**
	 * Solution 2: recursion + memorization top-down.
	 * Time: O(2^n); for cases like: s = "aaaaaaaaaaaaa", dict = ["a", "aa", "aaa", "aaaa"]
	 * Space: O(2^n);
	 */
	public List<String> wordBreak(String s, List<String> wordDict) {
    Set<String> dict = new HashSet(wordDict);
    Map<String, List<String>> memo = new HashMap<>();
    return helper(s, dict, memo);
  }
  
  public List<String> helper(String s, Set<String> dict,
      Map<String, List<String>> memo) {
    List<String> sentences = new ArrayList<>();
    if (s.equals("")) {
      // empty str, return [];
      return sentences;
    }
    // already have the sentences of s.
    if (memo.containsKey(s)) {
      return memo.get(s);
    }
    
    for (int i = 0; i < s.length(); i++) {
      String leftword = s.substring(0, i + 1);
      // check if leftSubstr is a word.
      if (dict.contains(leftword)) {
        // left is a word, we found a break.
        // find sentences combos for right substring.
        List<String> rightCombos = helper(s.substring(i + 1), dict, memo);
        // check if find any sentence in right.
        if (rightCombos.size() == 0) {
          // no sentence can be formed in right substr.
          // check if leftword is the same len as s. right = ""
          if (leftword.length() == s.length()) {
            // then sentence is just leftword.
            sentences.add(leftword);
          }
        } else {
          // Add leftword to every possible sentences.
          for (String rightSentence : rightCombos) {
            String fullSentence = leftword + " " + rightSentence;
            // possible sentences added to result.
            sentences.add(fullSentence);
          }
        }
      }
    }
    // cache the ans for s.
    memo.put(s, sentences);
    return sentences;
  }

	/**
	 * Solution 1: backtracking, time limit exceed.
	 * Time: O(n!); Ex: abcd => a,bcd; ab,cd; abc,d; a,bc,d; a
	 * space: O(w!)
	 */
  private List<String> res = new ArrayList<>();
  
  public List<String> wordBreak(String s, List<String> wordDict) {
    Set<String> wordSet = new HashSet<>(wordDict);
    helper(s, wordSet, "");
    return res;
  }
  
  public void helper(String s, Set<String> wordSet, String sentence) {
    // s = ""
    if (s.length() == 0) {
      res.add(sentence);
      return;
    }
    StringBuilder sb = new StringBuilder(sentence);
    // check every possible substr
    for (int i = 1; i <= s.length(); i++) {
      String substr = s.substring(0, i);
      // if substr is a word
      if (wordSet.contains(substr)) {
        // not beggin of sentence.
        if (!sentence.equals("")) {
          sb.append(" ");
        }
        // add this word to sentence.
        sb.append(substr);
        helper(s.substring(i), wordSet, sb.toString());
        // remove added word to try another word in this sentence.
        // prev sentence.len = 1st char of append word, which " ".
        sb.delete(sentence.length(), sb.length());
      }
    }
  }
}