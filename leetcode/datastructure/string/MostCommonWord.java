/**
819. Most Common Word
Given a paragraph and a list of banned words, return the most frequent word that is not in the list of banned words.  It is guaranteed there is at least one word that isn't banned, and that the answer is unique.

Words in the list of banned words are given in lowercase, and free of punctuation.  Words in the paragraph are not case sensitive.  The answer is in lowercase.

 

Example:

Input: 
paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
banned = ["hit"]
Output: "ball"
Explanation: 
"hit" occurs 3 times, but it is a banned word.
"ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph. 
Note that words in the paragraph are not case sensitive,
that punctuation is ignored (even if adjacent to words, such as "ball,"), 
and that "hit" isn't the answer even though it occurs more because it is banned.
 

Note:

1 <= paragraph.length <= 1000.
1 <= banned.length <= 100.
1 <= banned[i].length <= 10.
The answer is unique, and written in lowercase (even if its occurrences in paragraph may have uppercase symbols, and even if it is a proper noun.)
paragraph only consists of letters, spaces, or the punctuation symbols !?',;.
There are no hyphens or hyphenated words.
Words only consist of letters, never apostrophes or other punctuation symbols.
*/

class MostCommonWord {

	/**
	 * Solution: Use hashMap to count words, loop and look for word break. HashSet for banned words.
	 * Time: O(n)
	 * Space: O(n)
	 */
  public String mostCommonWord(String paragraph, String[] banned) {
    // hashmap store appeared words, ignore punctuation. 
    // word, count.
    Map<String, Integer> map = new HashMap<>(); 
    // hashset for banned words.
    Set<String> banSet = new HashSet<>();
    // add banned words to banSet
    for (String ban : banned) {
      banSet.add(ban);
    }
    // loop paragraph.
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < paragraph.length(); i++) {
      char c = paragraph.charAt(i);
      // word stop at space.
      // if letters, add to cur word str.
      if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
        sb.append(c);
        if (i == paragraph.length() - 1) {
          // endOfParagraph add word, check in banned.
          String word = sb.toString().toLowerCase();
          if (!banSet.contains(word) && word.length() != 0) {
            // not banned, count in map.
            map.put(word, map.getOrDefault(word, 0) + 1);
          }
          // reset sb word
          sb.setLength(0);
        }
      } else {
        // not a letter, word break.
        String word = sb.toString().toLowerCase();
        if (!banSet.contains(word) && word.length() != 0) {
          // not banned, count in map.
          map.put(word, map.getOrDefault(word, 0) + 1);
        }
        // reset sb word
        sb.setLength(0);
      }
      
    }
    // check most counted/common word
    String mostCommon = null;
    for (String key : map.keySet()) {
      if (mostCommon == null || map.get(key) > map.get(mostCommon)) {
        mostCommon = key;
      }
    }
    return mostCommon;
  }
  
  
  
}