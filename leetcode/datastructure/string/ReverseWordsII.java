/**
186. Reverse Words in a String II
Given an input character array, reverse the array word by word. A word is defined as a sequence of non-space characters.

The input character array does not contain leading or trailing spaces and the words are always separated by a single space.

Example
Given s = "the sky is blue",
after reversing : "blue is sky the"

Challenge
Could you do it in-place without allocating extra space?
*/

/**
Solution: Reverse the whole String, then reverse each word.
Ex: "the sky is blue"; 
reverse "eulb si yks eht";
reverse words, "blue is sky the";

How to arrive:
* When we reverse the whole string the word's place is in reversed order which is what we want.
* Declare a reverse function that take 2 pointers to swap and reverse the char[];
```
public char[] reverse(char[] str, int start, int end) {
	while (start < end) {
		char temp = str[start];
		str[start] = str[end];
		str[end] = temp;
		start++;
		end--;
	}
	return str;
}
```
* After reverse the Whole String, The chars of the words are reversed too, so just reverse word chars 1 by 1.
* To do that, we can use 2 pointers. Start will find the next word's startIndex, End will find the next word's endIndex. Then swap and reverse the word using those 2 pointers. 
```
int start = 0;
// when start reaches last char, we are done.
while (start < str.length - 1) {
	int end = start;
	// find word end;
	while (end < str.length - 1 && str[end + 1] != ' ') {
		end++;
	}
	str = reverse(str, start, end);
	// find next word start, end+1 is space or last char.
	// next start is after the space, 2 pos after end. Since we only have 1 space.
	start = end + 2;
}
```
* Time: O(n)
* Space: O(1)

*/

public class ReverseWordsII {

  /**
   * @param str: a string
   * @return: return a string
   * Solution: Reverse the whole string, then reverse word by word.
   * Time: O(n)
   * Space: O(1)
   */
  public char[] reverseWords(char[] str) {
    if (str == null || str.length == 0) {
      return str;
    }
    // reverse whole str first
    str = reverse(str, 0, str.length - 1);
    // reverse word by word.
    int start = 0;
    // when start reaches last char, we are done.
    while (start < str.length - 1) {
      int end = start;
      // find word end;
      while (end < str.length - 1 && str[end + 1] != ' ') {
        end++;
      }
      // reverse swap the word.
      str = reverse(str, start, end);
      // find next word start, end+1 is space or last char.
      // next start is after the space, 2 pos after end. Since we only have 1 space.
      start = end + 2;
    }
    
    return str;
  }
  
  public char[] reverse(char[] str, int start, int end) {
    while (start < end) {
      char temp = str[start];
      str[start] = str[end];
      str[end] = temp;
      start++;
      end--;
    }
    return str;
  }
}