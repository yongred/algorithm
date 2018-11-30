/**
49. Group Anagrams
Given an array of strings, group anagrams together.

Example:

Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
Output:
[
  ["ate","eat","tea"],
  ["nat","tan"],
  ["bat"]
]
Note:

All inputs will be in lowercase.
The order of your output does not matter.
*/

/**
Solution: sort each word, store in Hash as key.
How to arrive:
* Anagram just the arrange of same chars. So sort them will become the same.
* We save the sorted word as key in HashMap.
* if HashMap doesn't contains the sortedWord, we create a new list with the word, and map the sortedWord as index to that word's pos in list. So we can retrieve that list to add same Anagram words.
* if HashMap contains the sortedWord, we get the index from Hash and use it to retrieve the list in res, and add the word.
```
if (!hash.containsKey(sortedWord)) {
        List<String> wordList = new ArrayList<>();
        wordList.add(word);
        res.add(wordList);
        hash.put(sortedWord, res.size() - 1);
      } else {
        int index = hash.get(sortedWord);
        res.get(index).add(word);
      }
```
* Time: O(n * k lg k); k is the longest word.
* Space: O(nk); words in strs and len.

Solution: Use ascii instead of sort.
How to arrive:
* All we need to know is the count of each letter, so we can determine whether it's an Anagram.
* To count letters, we just need an Ascii array of 26. Store each in pos [letter - 'a']; 
* Now loop from 0->25, check each letter, make the count + letter a hashMap key.
	* Ex: 'aaa' => '3a';
* Use the hashMap same as before.
* Time: O(nk); str size and counting chr for each word.
* Space: O(nk);

*/

class GroupAnagram {

	/**
   * Solution: Sort each word, put into Hash
   * Time: O(n klgk)
   * Space: O(nk)
   */
  public List<List<String>> groupAnagrams(String[] strs) {
    List<List<String>> res = new ArrayList<>();
    // string: sorted word, Int: res index.
    HashMap<String, Integer> hash = new HashMap<>();
    for (String word : strs) {
      char[] chArr = word.toCharArray();
      Arrays.sort(chArr);
      String sortedWord = new String(chArr);
      // a new word w/ diff chars. Create new list.
      if (!hash.containsKey(sortedWord)) {
        List<String> wordList = new ArrayList<>();
        wordList.add(word);
        res.add(wordList);
        hash.put(sortedWord, res.size() - 1);
      } else {
        int index = hash.get(sortedWord);
        res.get(index).add(word);
      }
    }
    
    return res;
  }

  /**
   * Solution: Using Ascii instead of sort
   * Time: O(nk)
   * Space: O(nk)
   */
  public List<List<String>> groupAnagrams(String[] strs) {
    List<List<String>> res = new ArrayList<>();
    // string: sorted word, Int: res index.
    HashMap<String, Integer> hash = new HashMap<>();
    // 26 letters
    int[] ascii = new int[26];
    for (String word : strs) {
      // clear counts for new word.
      Arrays.fill(ascii, 0); 
      char[] chArr = word.toCharArray();
      // count the letters in word.
      for (char c : chArr) {
        ascii[c - 'a']++;
      }
      StringBuilder sb = new StringBuilder();
      // check from a-z, so anagrams will have the same.
      for (int i = 0; i < 26; i++) {
        if (ascii[i] > 0) {
          // count and char
          sb.append(ascii[i]);
          char letter = (char)(i + 'a');
          sb.append(letter);
        }
      }
      String key = sb.toString();
      // a new word w/ diff chars. Create new list.
      if (!hash.containsKey(key)) {
        List<String> wordList = new ArrayList<>();
        wordList.add(word);
        res.add(wordList);
        hash.put(key, res.size() - 1);
      } else {
        int index = hash.get(key);
        res.get(index).add(word);
      }
    }
    
    return res;
  }
}