/**
344. Reverse String
Write a function that takes a string as input and returns the string reversed.

Example 1:

Input: "hello"
Output: "olleh"
Example 2:

Input: "A man, a plan, a canal: Panama"
Output: "amanaP :lanac a ,nalp a ,nam A"
*/

/**
Solution: 2 pointers, swap 1st and last, until they meet.
How to arrive:
* Straight foward, transform String to a char[], have 2 pointers one on 1st char, one on last char.
* Swap them and i++, j--; do the same until i >= j;
* We can also use StringBuilder setCharAt function. Or simply use StringBuilder reverse() function.
* Notice String don't have setCharAt function.
* Time: O(n)
* Space: O(s); length of the string.
*/

class ReverseString {

	/**
	 * Solution: 2 pointers, swap 1st and last, until they meet.
	 * Time: O(n)
	 * Space: O(s); length of the string.
	 */
  public String reverseString(String s) {
    if (s == null || s.length() == 0) {
      return s;
    }
    char[] res = s.toCharArray();
    int i = 0;
    int j = s.length() - 1;
    while (i < j) {
      char temp = res[i];
      res[i] = res[j];
      res[j] = temp;
      i++;
      j--;
    }
    return new String(res);
  }
}