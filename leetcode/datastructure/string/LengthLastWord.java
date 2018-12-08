/**
58. Length of Last Word
Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last word in the string.

If the last word does not exist, return 0.

Note: A word is defined as a character sequence consists of non-space characters only.

Example:

Input: "Hello World"
Output: 5
*/

/**
Solution 1: use split() function, get the last String in array, return len.
* Be careful, remember to check split len == 0; If s is all "   " empty. Then split() will return [];
* Time: O(n)
* Space: O(s), split into array, size of string.

--------
Solution 2: Scan reversely right to left. Count chars, stop at space after we find any char.
* **Key**: Don't forget the beginning and end trailling " " chars. 
* **Key** So, we cannot just find " " and len - pos(last " "); b/c " " in the beginning and end of str doesn't count as word.
* Use trim() to remove trailling " " s; Create var, cannot trim the original.
```
String trimmed = s.trim();
```
* Or add a condition, if (len > 0); This means there are atleast 1 char found, so this " " is in between words.
```
if (s.charAt(i) != ' ') {
	len++;
} else if (len > 0) {
	// this " " is after we find last word.
	break;
}
```
* Time: O(n); much better on average b/c we are finding last word, can skip all the chars before.
* Space: O(1);
*/

class LengthLastWord {

	/**
	 * Solution: Same soluton scan reversely, but with trim(), avoid "  asd bsd " spaces beg end;
	 * Time: O(n);
	 * Space: O(1);
	 */
	public int lengthOfLastWord(String s) {
    if (s == null || s.length() == 0) {
      return 0;
    }
    // notice we cannot just find " " and len - pos(last " "); 
    // b/c " " in the beginning and end of str doesn't count as word.
    int len = 0;
    String trimmed = s.trim();
    // last word, so from the back find 1st char as start, then find " " as end.
    for (int i = trimmed.length() - 1; i >= 0; i--) {
      if (trimmed.charAt(i) != ' ') {
        len++;
      } else {
        // this " " is after we find last word.
        break;
      }
    }
    return len;
  }

	/**
	 * Solution: Scan reversely right to left. Count chars, stop at space after we find any char.
	 * Time: O(n); much better on average b/c we are finding last word, can skip all the chars before.
	 * Space: O(1);
	 */
	public int lengthOfLastWord(String s) {
    if (s == null || s.length() == 0) {
      return 0;
    }
    // notice we cannot just find " " and len - pos(last " "); 
    // b/c " " in the beginning and end of str doesn't count as word.
    int len = 0;
    // last word, so from the back find 1st char as start, then find " " as end.
    for (int i = s.length() - 1; i >= 0; i--) {
      if (s.charAt(i) != ' ') {
        len++;
      } else if (len > 0) {
        // this " " is after we find last word.
        break;
      }
    }
    return len;
  }

	/**
	 * Solution: use split() function, get the last String in array, return len.
	 * Time: O(n)
	 * Space: O(s), split into array, size of string.
	 */
  public int lengthOfLastWord(String s) {
    if (s == null || s.equals("")) {
      return 0;
    }
    String[] res = s.split(" ");
    if (res.length == 0) {
      return 0;
    }
    return res[res.length - 1].length();
  }
}