/**
387. First Unique Character in a String
Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.

Examples:

s = "leetcode"
return 0.

s = "loveleetcode",
return 2.
Note: You may assume the string contain only lowercase letters.
*/

/**
Solution: Use Ascii 26 letters as hash, count the appearances of char.
How to arrive:
* They want us to find unique chars. Which looks like a HashMap can solve. <Val, Count>;
* However, chars means we can use ASCII if they are all ascii chars. Better yet, the question mentioned only lowercase chars, only 26 letters.
* So we can have a letters[26], and do char - 'a' as indexes.
```
int[] letters = new int[26];
for (int i = 0; i < s.length(); i++) {
	// minus 'a' since it is the 1st val out of 26, 'a-z' are consecutive in ASCII.
	int pos = s.charAt(i) - 'a';
	letters[pos]++;
}
```
* After count, loop throught Str chars again, return the index of 1st count == 1 in letters[26] arr.
* Time: O(n)
* Space: O(1);
*/

class FirstUniqueCharacter {

	/**
	 * Solution: Use Ascii 26 letters as hash, count the appearances of char.
	 * Time: O(n)
	 * Space: O(1);
	 */
  public int firstUniqChar(String s) {
    // only 26 letters inputs.
    int[] letters = new int[26];
    for (int i = 0; i < s.length(); i++) {
      // minus 'a' since it is the 1st val out of 26, 'a-z' are consecutive in ASCII.
      int pos = s.charAt(i) - 'a';
      letters[pos]++;
    }
    // check 1st unique
    for (int i = 0; i < s.length(); i++) {
      int pos = s.charAt(i) - 'a';
      if (letters[pos] == 1) {
        return i;
      }
    }
    return -1;
  }
}