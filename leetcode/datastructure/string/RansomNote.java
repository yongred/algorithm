/**
383. Ransom Note
Given an arbitrary ransom note string and another string containing letters from all the magazines, write a function that will return true if the ransom note can be constructed from the magazines ; otherwise, it will return false.

Each letter in the magazine string can only be used once in your ransom note.

Note:
You may assume that both strings contain only lowercase letters.

canConstruct("a", "b") -> false
canConstruct("aa", "ab") -> false
canConstruct("aa", "aab") -> true
*/

/**
Solution: Use Ascii as hash to count ransomNote chars.
How to arrive:
* This question is asking: Can the letters in magazineStr able to form ransomStr.
* So all we need to do is count the letters in ransomStr, and then count if the same letters apprears >= amount of times in magazineStr.
* To do that we can use Ascii[26], since it's just 26 letters mentioned in question.
* We use a count var to count the letters appears in magazineStr, if the letters[26] > 0 then we increments count++; Until count == ransom.len; 
* Or you can loop through 26 letters again after decrement, and see which one have leftover > 0;
* Time: O(n);
* Space: O(1);
*/

class RansomNote {
	
	/**
	* Solution: Use Ascii as hash to count ransomNote chars.
	* Time: O(n);
	* Space: O(1);
	*/
  public boolean canConstruct(String ransomNote, String magazine) {
    if (ransomNote.length() == 0) {
      return true;
    }
    int[] letters = new int[26];
    for (char c : ransomNote.toCharArray()) {
      letters[c - 'a']++;
    }
    int count = 0;
    for (char c : magazine.toCharArray()) {
      if (letters[c - 'a'] > 0) {
        count++;
        letters[c - 'a']--;
      }
      if (count == ransomNote.length()) {
        return true;
      }
    }
    return false;
  }
}