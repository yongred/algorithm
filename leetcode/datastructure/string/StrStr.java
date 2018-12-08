/**
28. Implement strStr()
Implement strStr().

Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

Example 1:

Input: haystack = "hello", needle = "ll"
Output: 2
Example 2:

Input: haystack = "aaaaa", needle = "bba"
Output: -1
Clarification:

What should we return when needle is an empty string? This is a great question to ask during an interview.

For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
*/

/**
Solution 1: brute force, compare substring with needle.
How to arrive:
* straight foward, just begin with each char in haystack, compare until needle size left.
```
for (int i = 0; i <= haystack.length() - needle.length(); i++) {
	for (int j = 0; j < needle.length(); j++) {
		// if not match break inner loop.
		if (needle.charAt(j) != haystack.charAt(i + j)) {
			break;
		}
		if (j == needle.length() - 1) {
			return i;
		}
	}
}
```
* Time: O(m * n)
* Space: O(1)

-------------

Solution 2: KMP algorithm, LPS (longest proper prefix) table.
Ex: haystack 'abcabcabd'
needle: 'abcabd', lps = [0,0,0,1,2,0] 
substr 'abcabc' != 'abcabd', find last matched char 'b', check on lps[4] 'b' = 2 char 'c' (right after 'b') can be a match; go to check needle 'abc' = substr abc'(abc)'; 
Result : match like below.
abca(b)cabd
			a(b)cabd

How to arrive:
* KMP algorithm: If we have repeated same prefix == suffix, when mismatch happens in the suffix, we use the last matched suffix char to can go back to the prefix pos with the same char.
Ex:
haystack: abcab(c)abd
needle:     abcab(d)
* Here c and d is a mismatch, last matched char is 'b', which 'ab' can match 2 len prefix in needle as well.  So we bring needle to prefix 'ab' pos and compare next char 'c' with haystack cur char 'c' also. Now we have a match.
haystack: abcab(c)abd
		needle:       ab(c)abd
* To perform this, we need a lps table recording at each pos of needle str, how long of prefix str does cur pos match.
Ex: abab; at index 3; it match len 2 (chars) of prefix 'ab'; at index 2: it matches len 1 of prefix, 'a'; The lpsTable = [0,0,1,2]; index 0 will always be matching 0 len, since it has no prev chars.
* Now use this table to determine which char in needle we can go back to.
Ex: 
haystack: abcab(c)abd
needle:     abcab(d)
lps = [0,0,0,1,2,0] 
needle charAt 4: 'b' is the last matched. We check it's prefix match on lps table = 2;
so we assign j = 2; needle[j] = 'c' which is 'ab(c)' the char right after 'ab', the same prefix as suffix. So we compare 'c' with haystack char haystack[5] = 'c' as well.
haystack: abcab(c)abd
		needle:       ab(c)abd
* Time: O(m + n); time for constructing lps table + iteration of haystack.
* Space: O(m); for the table.
*/

class StrStr {

	/**
	 * Solution: KMP algorithm, creat LPS table.
	 * Time: O(m + n)
	 * Space: O(m)
	 */
	public int strStr(String haystack, String needle) {
    if (needle.length() == 0) {
      return 0;
    }
    if (haystack.length() == 0) {
      return -1;
    }
    // longest proper prefix table.
    int[] lps = kmpTable(needle);
    int i = 0;
    // cur matched. lps index.
    int j = 0;
    while (i < haystack.length() && j < needle.length()) {
      if (haystack.charAt(i) == needle.charAt(j)) {
        // if matched last char of needle.
        if (j == needle.length() - 1) {
          return (i - j);
        }
        i++;
        j++;
      } else {
        // cur char != needle char.
        if (j > 0) {
          // if not 1st lps char, check prev matched shorter prefix.
          // ex: [aaac] != [aaaa]; prefix <- move back = [aaa]c; suffix -> a[aaa]; aaa=aaa;
          j = lps[j - 1];
        } else {
          // j < 0, 1st char.
          i++;
        }
      }
    }
    return -1;
  }
  
  public int[] kmpTable(String needle) {
    if (needle.length() == 0) {
      return new int[0];
    }
    // longest proper prefix
    int[] lps = new int[needle.length()];
    // 1st elm have no prev prefix matches.
    lps[0] = 0;
    int i = 1;
    // from index 0 -> matchlen prefix is matched with cur suffix.
    // how long does cur suffix match the prefix.
    int matchlen = 0;
    while (i < needle.length()) {
      // compare cur char to prefix char. Ex: a(b)ca(b) 'b'='b'; [4]=2; 2nd char of prefix.
      if (needle.charAt(i) == needle.charAt(matchlen)) {
        matchlen++;
        lps[i] = matchlen;
        i++;
      } else {
        // prefix != cur suffix.
        if (matchlen != 0) {
          // look for matches in shorter prefix.
          // look at last matched char of prefix. Comparing cur i pos shorter suffix.
          // Ex: aacaaa, 0-2'aac' != 3-5'aaa', go back 0-1'aa' = 4-5'aa';
          matchlen = lps[matchlen - 1];
        } else {
          // when matchlen = 0; back to the beginning, prefixLen 0, compare 1st char.
          // no char in prefix matches cur suffix char.
          lps[i] = 0;
          i++;
        }
      }
    }
    return lps;
  }


	/**
	 * Solution: brute force, nested loops.
	 * Time: O(m * n)
	 * Space: O(1)
	 */
  public int strStr(String haystack, String needle) {
    if (needle.equals("")) {
      return 0;
    }
    for (int i = 0; i <= haystack.length() - needle.length(); i++) {
      for (int j = 0; j < needle.length(); j++) {
        // if not match break inner loop.
        if (needle.charAt(j) != haystack.charAt(i + j)) {
          break;
        }
        if (j == needle.length() - 1) {
          return i;
        }
      }
    }
    
    return -1;
  }
}