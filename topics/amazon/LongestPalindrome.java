/**
409. Longest Palindrome
Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.

This is case sensitive, for example "Aa" is not considered a palindrome here.

Note:
Assume the length of given string will not exceed 1,010.

Example:

Input:
"abccccdd"

Output:
7

Explanation:
One longest palindrome that can be built is "dccaccd", whose length is 7.
*/

/**
Solution: Palidrome only 1 oddCount char allowed, use oddCount-1 for all oddCount chars except for one.
Ex:
abc(b|c)cba = 7
only one odd count char allowed.
so if more than 1 odd, use odd-1 chars to build palindrome, left w/ only 1 odd.
So all other odd-1 counts, except for one;

aaabbbbbccd
abbc(a|b|d)cbba = 9

How to Arrive:
* Palidrome only 1 oddCount char allowed;
Ex: abbcbba or abbcccbba. Only c is odd. No abbbcccbba;  b and c is odd.
* Question ask for longest palindrome we can build using the existing chars, **We don't have to use all chars, we ca eliminates**;
* Which means oddCount chars, we can oddCount - 1 = even;
Ex: abbbcccbba => abbcccbba Or acbbbbbca, either get rid of 1 c or 1 b, left only 1 oddCount.
* So we can just do Total += oddCount - 1, when encounter odd, Then add 1 char at the end, because only 1 oddCount allowed.
* As for even we just add the count as it is. Total += evenCount.
* Use a ASCII[256] to count.
* Time: O(n)
* Space: O(1)

*/

class LongestPalindrome {

	/**
	 * Solution: Palidrome only 1 oddCount char allowed, use oddCount-1 for all oddCount chars except for one.
	 * Time: O(n)
	 * Space: O(1)
	 */
  public int longestPalindrome(String s) {
    // ascii hash count letters
    int[] count = new int[256];
    for (char c : s.toCharArray()) {
      count[c]++;
    }
    int longest = 0;
    // Add 1 to totalCount if odd exist.
    int oddExist = 0;
    for (int i = 0; i < 256; i++) {
      if (count[i] % 2 != 0) {
        // odd. use odd-1 (even) chars to build palindrome
        longest += count[i] - 1;
        if (oddExist == 0){
          oddExist = 1;
        }
      } else {
        // even, add to existing palindrome.
        longest += count[i];
      }
    }
    return longest + oddExist;
  }
  
  
  
}