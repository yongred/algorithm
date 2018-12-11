/**
290. Word Pattern
Given a pattern and a string str, find if str follows the same pattern.

Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.

Example 1:

Input: pattern = "abba", str = "dog cat cat dog"
Output: true
Example 2:

Input:pattern = "abba", str = "dog cat cat fish"
Output: false
Example 3:

Input: pattern = "aaaa", str = "dog cat cat dog"
Output: false
Example 4:

Input: pattern = "abba", str = "dog dog dog dog"
Output: false
Notes:
You may assume pattern contains only lowercase letters, and str contains lowercase letters separated by a single space.
*/

/**
Solution: Using HashMap check unique mapping for both.
Ex: aaaa => dog cat cat dog, false;  abba => dog dog dog dog, false;
aba => dog cat cat dog, false; 

How to arrive:
* **Key** both pat and word need to map to only eachother.
* **Key** # of chars in pat need to == # of words in str.
* So we can use one HashMap <pat, word>; 
* If pat have mapped val, compare val to curWord. Not same return false;
* If pat don't have mapped val, then check word exists in other patKey map.containsValue(word); Since they can only map to eachother. Return false if word is already mapped.
* If both not mapped, map.put(pat, word);
* *you could use 2 hashes, pat String[] ascii, and words hash<String, char>*; But it is not necessary.
* **Remember String uses equals() function**
* Time: O(p);
* Space: O(w);
*/

class WordPattern {

	/**
	 * Solution: 1 hashMap, use containsValue to check word mapped already.
	 * Time: O(p)
	 * Space: O(w)
	 */
	public boolean wordPattern(String pattern, String str) {
    // hash for <pat, word>
    String[] words = str.split(" ");
    HashMap<Character, String> map = new HashMap<>();
    // check len ==
    if (pattern.length() != words.length) {
      return false;
    }
    // loop words
    for (int i = 0; i < words.length; i++) {
      char pat = pattern.charAt(i);
      String word = words[i];
      // if pat mapped
      if (map.containsKey(pat)) {
        // if pat mapped to another word already.
        if (!map.get(pat).equals(word)) {
          return false;
        }
      } else {
        // pat is not mapped to any word
        // check word is already mapped to any pat
        if (map.containsValue(word)) {
          return false;
        }
        // if both pat and word not mapped, map them
        map.put(pat, word);
      }
    }
    return true;
  }

	/**
	 * Solution: 2 hashes. 1 for pat, 1 for words.
	 * Time: O(p)
	 * Space: O(w)
	 */
  public boolean wordPattern(String pattern, String str) {
    // both pat and words need a hash map.
    String[] patMap = new String[26];
    String[] words = str.split(" ");
    // pat chars len must = words len.
    if (words.length != pattern.length()) {
      return false;
    }
    // ascii for pat, hash for words
    HashMap<String, Character> wordMap = new HashMap<>();
    // loop through words, compare pat key to word val if exits
    for (int i = 0; i < words.length; i++) {
      char pat = pattern.charAt(i);
      String word = words[i];
      if (patMap[pat - 'a'] != null) {
        // not match prev pat word
        if (!patMap[pat - 'a'].equals(word)) {
          return false;
        }
      } else if (wordMap.containsKey(word)) {
        // check if word key map have 2 vals
        if (wordMap.get(word) != pat) {
          return false;
        }
      } else {
        // if not exist in hashes, add them to hashes
        patMap[pat - 'a'] = word;
        wordMap.put(word, pat);
      }
    }
    return true;
  }
}