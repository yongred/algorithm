/**
242. Valid Anagram
Given two strings s and t , write a function to determine if t is an anagram of s.

Example 1:

Input: s = "anagram", t = "nagaram"
Output: true
Example 2:

Input: s = "rat", t = "car"
Output: false
Note:
You may assume the string contains only lowercase alphabets.

Follow up:
What if the inputs contain unicode characters? How would you adapt your solution to such case?
*/

/**
Solution: use ASCII 26 for s, check t chars have same count.
How to Arrive:
* Anagram means same amount of same chars with diff arangement.
* **Key**: Check s.len == t.len; they have to have same len.
* So, just count one of them (s) with hash, in this case ASCII 26 lowercase letters.
* Then, decrement with T str, If they are anagram, all of hash counts should decrement to exactly 0s.
* Since we already make sure they are the same len, there won't be any >= 1 counts left without having another char count become negative.
* So if there is a negative, means a diff char not in S str. Return false;
* Time: O(n)
* Space: O(1)
* Follow up: If there is Unicode, cannot use just ASCII, use a HashMap.
*/

class ValidAnagram {

	/**
	 * Solution: use ASCII 26 for s, check t chars have same count.
	 * Time: O(n)
	 * Space: O(1)
	 */
  public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }
    // s chars, count map
    int[] smap = new int[26];
    // fill smap with s chars count.
    for (int i = 0; i < s.length(); i++) {
      smap[s.charAt(i) - 'a']++;
    }
    // loop t chars, count until matches len
    for (int i = 0; i < t.length(); i++) {
      char tchar = t.charAt(i);
      // check same amount of chars
      if (smap[tchar - 'a'] > 0) {
        smap[tchar - 'a']--;
      } else {
        // not same count of chars in t.
        return false;
      }
    }
    return true;
  }
}