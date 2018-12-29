/**
318. Maximum Product of Word Lengths
Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where the two words do not share common letters. You may assume that each word will contain only lower case letters. If no such two words exist, return 0.

Example 1:

Input: ["abcw","baz","foo","bar","xtfn","abcdef"]
Output: 16 
Explanation: The two words can be "abcw", "xtfn".
Example 2:

Input: ["a","ab","abc","d","cd","bcd","abcd"]
Output: 4 
Explanation: The two words can be "ab", "cd".
Example 3:

Input: ["a","aa","aaa","aaaa"]
Output: 0 
Explanation: No such pair of words.
*/

/**
Solution 1: Naive, Using Ascii as hash. Count every chars in words every time.
How to Arrive:
* Use a 26 ASCII array to count words[i] letters;
* Use the Counts array to compare words[j] letters:
	* if (Counts[c] > 0) means exist in words[i], So they have same char.
	* If no same char found,   Counts[jchars]  all = 0:
		* Then chars are all unique, check and calc w1.Len * w2.Len > maxVal;
* Time: O((w * l)^2); Count every chars in words every time
* Space: O(1)

--------------

Solution 2: use 26 bits as 26 letter appearance, create bitwise Hash.
How to Arrive:
* There are 26 letters, there are 32 bits in Integer. Which means we can use bits to represent letter exist in a word.
Ex: 'a' = ..0001; 'b' = ..0010; 'ab' = ..0011;
* We will create a hash array of wordBits to represent letter exist in each word;
* Init wordBits by looping through chars in each word:
	* Use: wordBits[i] | (1 << c - 'a') to assign 1 to that letter bit.
	* c - 'a' is ith letter. 1 << c - 'a' times, move 1 to that letter bit.
	* then ith pos OR 1 to setBit as 1;
* Then we just do a nested loop to compare 2 words' letters using wordBits array.
	* We compare by AND '&', bits[w1] & bits[w2] will give us 0s if all 26 bits/letters are unique. Any 1 & 1 = 1s; 
	* If unique, calc the w1Len * w2Len, check MaxVal;

* Optimized speed to just T(words^2); No need to count chars every time.
* Time: ((w * l) + w ^ 2); T(words * wordlen) + T(words^2); 
* create bithash loop every chars in words. Then we just loop every word's bithash.
* Space: O(n);

*/

class MaximumProductWordLengths {

	/**
	 * Solution 2: use 26 bits as 26 letter appearance, create bitwise Hash.
	 * Time: ((w*l) + w ^ 2); T(words * wordlen) + T(words^2); 
	 * create bithash loop every chars in words. Then we just loop every word's bithash.
	 * Space: O(n);
	 */
	public int maxProduct(String[] words) {
    if (words == null || words.length == 0) {
      return 0;
    }
    int maxVal = 0;
    // bit hash mapping word letters to bits.
    // 26 bits for 26 letters.
    // 'a' = ..0001; 'b' = ..0010; 'ab' = ..0011;
    int[] wordBits = new int[words.length];
    // init letter appearances for each words, save in wordBits arr.
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      for (char c : word.toCharArray()) {
        // c - 'a' is ith letter. 1 << c - 'a' times, move 1 to that letter bit.
        // then we ith pos OR 1 to setBit as 1; letter exist in word.
        wordBits[i] = wordBits[i] | (1 << c - 'a');
      }
    }
    // now compare words' letters using wordBits array hash.
    for (int i = 0; i < words.length - 1; i++) {
      for (int j = i + 1; j < words.length; j++) {
        // i is word1, j is word2; Compare their letters in wordBits.
        // (wordBits[i] & wordBits[j]) put in parantheses, == higher precedence than bitwise & | ^;
        if ((wordBits[i] & wordBits[j]) == 0) {
          // we AND the 2 binary, if all unique bits, it will == 0;
          // compare and calc maxVal
          maxVal = Math.max(maxVal, words[i].length() * words[j].length());
        }
      }
    }
    return maxVal;
  }
	
	/**
	 * Solution 1: Using Ascii as hash.
	 * Time: O((w * l)^2); Count every chars in words every time
	 * Space: O(1)
	 */
  public int maxProduct(String[] words) {
    if (words == null || words.length == 0) {
      return 0;
    }
    int max = 0;
    int[] counts = new int[26];
    // loop each words
    for (int i = 0; i < words.length - 1; i++) {
      String word1 = words[i];
      // chars in word1
      for (char c : word1.toCharArray()) {
        counts[c - 'a']++;
      }
      for (int j = i + 1; j < words.length; j++) {
        // compare word1 with other words.
        String word2 = words[j];
        boolean same = false;
        for (char c : word2.toCharArray()) {
          if (counts[c - 'a'] > 0) {
            same = true;
            break;
          }
        }
        if (!same) {
          max = Math.max(max, word1.length() * word2.length());
        }
      }
      // reset counts for next word.
      Arrays.fill(counts, 0);
    }
    return max;
  }
  
}