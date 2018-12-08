/**
53. Reverse Words in a String
DescriptionHintsSubmissionsDiscussSolution
Given an input string, reverse the string word by word.

Example:  

Input: "the sky is blue",
Output: "blue is sky the".
Note:

A word is defined as a sequence of non-space characters.
Input string may contain leading or trailing spaces. However, your reversed string should not contain leading or trailing spaces.
You need to reduce multiple spaces between two words to a single space in the reversed string.
*/

/**
Solution 1: Reverse the whole String, then reverse each word.
Ex: " the sky  is blue " => trim "the sky  is blue"; 
reverse "eulb si  yks eht";
reverse words, while remove extra space "blue is sky the";
How to arrive:
* When we reverse the whole string the word's place is in reversed order which is what we want.
* Either 2 pointers swap or just Stringbuilder.reverse is fine.
* **Key**: because we need to remove extra spaces between words, we need StringBuilder.deleteCharAt function.
* **Key**: Also trim the space, so we don't have to worry about leading or trailing spaces.
* The chars of the words are reversed too, so just reverse word chars 1 by 1.
* To do that, we can use 2 pointers. Start will find the next word's startIndex, End will find the next word's endIndex. Then swap and reverse the word using those 2 pointers. 
* Declare an extra function to do reverse of words in StringBuilder would be helpful.
```
public StringBuilder reverse(StringBuilder sb, int start, int end) {
  while (start < end) {
    char temp = sb.charAt(start);
    sb.setCharAt(start, sb.charAt(end));
    sb.setCharAt(end, temp);
    start++;
    end--;
  }
  return sb;
}
```
* When start pointer reaches the end of String, we are done.
* Time: O(S)
* Space: O(S)

---------------
Solution 2: Split the words, then reverse the words, then join.
Ex: " the sky  is blue " => trim "the sky  is blue"; 
**split(" +") or split("\\s+)**: 1 or more space; => ["the", "sky", "is", "blue"]
reverse the words => ["blue", "is", "sky", "the"];
join(" ") => "blue is sky the";

How to arrive:
* If string are words array, then we just have to reverse the array words order.
* So using split and join function help us do that.
* **Key**: split(" +") or split("\\s+); Since we are split 1 or more space; Otherwise empty string will be words as well.
* Time: O(n);
* Space: O(n);
*/

public class ReverseWords {

  /**
   * Solution: Use StringBuilder to reverse and remove extra space.
   * Time: O(S)
   * Space: O(S)
   */
  public String reverseWords(String s) {
    if (s == null || s.length() == 0) {
      return s;
    }
    // trim so we don't have to worry about leading or trailing spaces.
    StringBuilder sb = new StringBuilder(s.trim());
    // reverse the cur string first.
    sb.reverse();
    // start of word
    int start = 0;
    // start at last char means finished. 
    while (start < sb.length() - 1) {
      // end of word
      int end = start;
      // find wordend, next is space.
      while (end < sb.length() - 1 && sb.charAt(end + 1) != ' ') {
        end++;
      }
      sb = reverse(sb, start, end);
      start = end + 1;
      // remove all multiple spaces.
      while (start < sb.length() - 1 && sb.charAt(start + 1) == ' ') {
        sb.deleteCharAt(start + 1);
      }
      // next is not space.
      start++;
    }
    
    return sb.toString();
  }
  
  public StringBuilder reverse(StringBuilder sb, int start, int end) {
    while (start < end) {
      char temp = sb.charAt(start);
      sb.setCharAt(start, sb.charAt(end));
      sb.setCharAt(end, temp);
      start++;
      end--;
    }
    return sb;
  }

  /**
   * Solution 2: Split the words, then reverse the words, then join.
   * Time: O(n)
   * Space: O(n)
   */
  public String reverseWords(String s) {
    if (s == null || s.length() == 0) {
      return s;
    }
    // split into words. RegEx 1 more space. "\\s+" or " +";
    String[] words = s.trim().split(" +");
    if (words.length == 0) {
      return "";
    }
    int i = 0;
    int j = words.length - 1;
    // swap 1st last words.
    while (i < j) {
      String temp = words[i];
      words[i] = words[j];
      words[j] = temp;
      i++;
      j--;
    }
    return String.join(" ", words);
  }

}

