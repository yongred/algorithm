/**
345. Reverse Vowels of a String
Write a function that takes a string as input and reverse only the vowels of a string.

Example 1:

Input: "hello"
Output: "holle"
Example 2:

Input: "leetcode"
Output: "leotcede"
Note:
The vowels does not include the letter "y".
*/

/**
Solution: 2 pointers find 1st and last vowel, swap.
How to arrive:
* Use boolean Ascii to assign vowel chars to True. Don't forget Upper case and lower case.
* Have 2 pointers start and end to find 1st and last vowels. Use that Ascii table to check. If both find vowels, swap them.
* Do that until start >= end;
* Time: O(n);
* Space: O(n);
*/

class ReverseVowels {

	/**
	 * Solution: 2 pointers find 1st and last vowel, swap.
	 * Time: O(n);
	 * Space: O(n);
	 */
  public String reverseVowels(String s) {
    if (s == null || s.length() == 0) {
      return s;
    }
    char[] arr = s.toCharArray();
    boolean[] isVowel = new boolean[256];
    // Notice it is ints. Use as keys.
    int[] vowels = {'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'};
    // assign vowels to true; For checking if a char is vowel.
    for (int i = 0; i < vowels.length; i++) {
      isVowel[vowels[i]] = true;
    }
    // use 2 pointers
    int start = 0;
    int end = arr.length - 1;
    // check for vowels and swap.
    while (start < end) {
      // find vowel leftside
      while (start < end && !isVowel[arr[start]]) {
        start++;
      }
      // find vowel rightside.
      while (start < end && !isVowel[arr[end]]) {
        end--;
      }
      // swap left and right vowels
      if (start < end) {
        char temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
        start++;
        end--;
      }
    }
    return new String(arr);
  }
}