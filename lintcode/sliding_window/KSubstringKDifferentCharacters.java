/**
1639. K-Substring with K different characters
Given a string S and an integer K.
Calculate the number of substrings of length K and containing K different characters

Example
String: "abcabc"
K: 3

Answer: 3
substrings:  ["abc", "bca", "cab"]
String: "abacab"
K: 3

Answer: 2
substrings: ["bac", "cab"]
*/

/**
Solution: Sliding Window, keep a window charCount Ascii[256]; Use HashSet for unique substrs with K unique chars.
How to Arrive:
* Question ask for K len substr with K unique chars, that means we just need to keep a count of unique chars in cur K len substr.
* Sliding window is fitting for this situation.
* We keep a window of K len substr. Use winChars[256] to record the cur window's chars count.
* We also keep a var Uniques for unique chars in winChars[256]; 
	* When winChar[curchar] == 1; it is unique, Uniques++;
	* When removing prev chars, if winChar[curchar] == 0; Uniques--;
* When i > K-1, we know curstr is K len substr:
	* We remove prev char winChar[i - K]--; 
	* Then check if winChar[curchar] == 0; if so Uniques--;
	* Then we check if uniques == K && if HashSet Not contains curSubStr:
		* Add curSubStr to HashSet.
* Return the Size of HashSet at the end. For number of substr w/ K chars.
* Time: O(n);
* Space: O(n);

*/

public class KSubstringKDifferentCharacters {

  /**
   * @param stringIn: The original string.
   * @param K: The length of substrings.
   * @return: return the count of substring of length K and exactly K distinct characters.
   * Solution: Sliding Window, keep a window charCount Ascii[256]; Use HashSet for unique substrs with K unique chars.
   * Time: O(n);
   * Space: O(n);
   */
  public int KSubstring(String stringIn, int K) {
    // keep a count unique chars.
    int uniques = 0;
    // ascii 256. Chars count in cur window.
    int[] winChars =  new int[256];
    Set<String> strsSet = new HashSet<>();
    // slide window when count == k;
    for (int i = 0; i < stringIn.length(); i++) {
      char cur = stringIn.charAt(i);
      winChars[cur]++;
      if (winChars[cur] == 1) {
        uniques++;
      }
      // check from i-(k-1) to i substr, 
      if (i > K - 1) {
        // remove chars before k len.
        winChars[stringIn.charAt(i - K)]--;
        if (winChars[stringIn.charAt(i - K)] == 0) {
          uniques--;
        }
        // check unique substring with k unique chars.
        String substr = stringIn.substring(i - (K - 1), i + 1);
        if (uniques == K && !strsSet.contains(substr)) {
          strsSet.add(substr);
        }
      }
    }
    return strsSet.size();
  }
  
  
  
}