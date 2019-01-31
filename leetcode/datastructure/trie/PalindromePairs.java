/**
336. Palindrome Pairs
Given a list of unique words, find all pairs of distinct indices (i, j) in the given list, so that the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.

Example 1:

Input: ["abcd","dcba","lls","s","sssll"]
Output: [[0,1],[1,0],[3,2],[2,4]] 
Explanation: The palindromes are ["dcbaabcd","abcddcba","slls","llssssll"]
Example 2:

Input: ["bat","tab","cat"]
Output: [[0,1],[1,0]] 
Explanation: The palindromes are ["battab","tabbat"]
*/

/**
Solution 1: HashMap reversed words and indexes; check reversed prefix and postfix of each words == any other word.
Ex:
["abcd","dcba","lls","s","sssll"] origin
["dcba","abcd","sll","s","llsss"] reversed

Res: [0,1], [1,0], [3,2], [4,2] Done;

prefixes:
0: "(abcd)" prefix abcd = reversed[1] "abcd"; abcd|dcba; (0,1)
1: "(dcba)" prefix dcba = reversed[0] "dcba"; dcba|abcd; (1,0)
2: "lls" no reversed == ll or sll
3: "s" no reversed s == prefix;
4: "sssll" no reversed == prefixes ll, lls, llss, llsss;

postfixes:
0: "(abcd)" postfix abcd = reversed[1] "abcd"; dcba|abcd; (1,0) duplicates
1: "(dcba)" postfix dcba = reversed[0] "dcba"; abcd|dcba; (0,1) duplicates
2: "ll(s)" postfix 's' = reversed[3] "s"; (3,2) s|lls
3: "s" no postfix matches any reversed
4: "ss(sll)" postfix 'sll' = reversed[2] "sll"; (2,4); lls|sssll;

How to Arrive:
**Key**: Palindrome can be formed by 2 strs, if one str is == Reverse of prefix or postfix of another str. And if the rest of the str is palindrome;
* Ex: "acca(bde)" => (edb)acca(bde) is palindrome. Ex: (edb)cca(bde) Not palin, b/c "cca" is not palindrome.
* Use a hashMap to map <reversedWords, indexes>; 
* Loop through each words, form all prefix and postfixes.
* Ex: "" & "abc", "a" & "bc", "ab" & "c", "abc" & ""; **From j=0 to j<= words[i].len**;
* Check if postfix is palindrome, prefix can form a palin with reversedPrefix.
  * ex: "(edb)acca" => (edb)acca(bde) palindrome.
  * Check if any reversed words in hashMap matches prefix: (not itself, and not duplicates); It means a word is == reversed(prefix); 
  * If found match word, (curWord + foundWord). Ex: "(abc)cc" + "cba" = "abccc|cba"
  * add pair (curWord index, foundWord index);
* if prefix is palindrome, postfix can form a palin with reversedPostfix.
  * ex: "acca(bde)" => (edb)acca(bde) palindrome.
  * Check if any words in hashSet matches reversed postfix:
  * If found match word, (foundWord + curWord). Ex: "ee(efg)"+  "gfe" = "gfe|eeefg"
  * Add pair (foundWord index,  curWord index);
* Return res;
* Time: O(N * L^2); N= # of words; L= avg word.len; check palindrome is O(L);
* Space: O(N * L);

-------------------

Solution 2: Use Trie to store reversed words.
Ex:
["ba", "a", "aaa"]
(wordIndex, [indexes of words that this substr can be a prefix of to form a palindrome.]);
          root (-1,[1,2])
            | 'a'
          n1 (1,[0,1,2])
'b' |                 | 'a'
n2 (0,[0])    n3 (-1,[2])
                      | 'a'
                 n4 (2,[2])

How to Arrive:
* Like Solution1, but we replace HashMap with Trie for reversed Words.
**Key**: Palindrome can be formed by 2 strs, if one str is == Reverse of prefix or postfix of another str. And if the rest of the str is palindrome;
* Ex: "acca(bde)" => (edb)acca(bde) is palindrome. Ex: (edb)cca(bde) Not palin, b/c "cca" is not palindrome.

**Define Trie**:
* The Trie we implement will contain reversed words.
* TrieNode contains: TrieNode[26] representing 26 letters, (ask interviewer if it is only 26 letters);
* We need to identify which word is it. Since question asking for indexes pairs. We will store the Index of words[index] array in starting TrieNode. Instead of isWord boolean.
* The TrieNode will also contain a **PrefixList** that have indexes of words that this substr can be a prefix of to form a palindrome. (if word - substr->thisNode == Palindrome) then add that word's index in this list. B/c it can be formed as a Palindrome.
Ex: "cc(cba)", if a word exist as "abc" then they can form "abc|cccba";

**Insert Words**:
* Almost the same as normal Trie insert function.
* Instead, we loop chars reversely (add letters reversely);
* And we assign word's Index in the lastNode to indicate which word it represent.
* Also, when traversing each letterNode, if (0-> i) isPalindrome, we know if a word == reversed substr/postfix (i + 1 -> len-1), then they can form a palindrome.
* So, we add curWord's index to curNode's prefixList.
* We will add curWord's index to itself's starting Node as well, b/c "abc" itself is == postfix "cba" reversed (abc);
* Notice: Root is ""; so "" + "aba" can form palindrome. So if any word == ""; it will also be added to Res pair.

**Search Pairs**:
* Search if curWord's prefixes match any word's reversed postfix.
* Like normal Trie searchWord, loop through each chars, and check for corresponding Nodes.
* If we found a Word (node.index >= 0) while looping chars of curWord, that means curWord is longer, 
* Ex: curWord (abc)cc, cba; if not itself like sss, sss; and the rest is palin. "cc" is palin, so abccc|cba is palin.
* Add pair (curWord's index, TrieNode's index);
* If Node == null; means no more path to check. Return;
* After loop all chars in curWord, and curNode != null. Which means curWord is shorter or == to Trie Path. And curWord matches reversedPostfix of atleast 1 word. (curWord as the prefix); curWord + TrieNode word.
* So we can go through CurNode's prefixList, to see which words can form palindrome with curWord;
* If prefixList index not == curWord index, not itself. We add pair(curWord's index, prefixList's indexes);
* Done;
* Time: O(N * L^2); N= # of words; L= avg word.len; check palindrome is O(L);
* Form Trie = T(N * L) * Palindrome check T(L) == O(N * L^2);  Search 1 word takes O(L) time, search N words T(N * L), times the palindrome check T(L); O(N * L^2);
* Space: O(N * L);
*/

class PalindromePairs {
  
  /**
   * Solution 1: HashMap words, indexes; check reversed prefix and postfix of each words == any other word
   * Time: O(N * L^2); N= # of words; L= avg word.len; check palindrome is O(L);
   * Space: O(N * L);
   */
  public List<List<Integer>> palindromePairs(String[] words) {
    List<List<Integer>> res = new ArrayList<>();
    if (words == null || words.length == 0) {
      return res;
    }
    // use hashMap to map <reversed words, index>
    Map<String, Integer> reversedMap = new HashMap<>();
    for (int i = 0; i < words.length; i++) {
      String reverseWord = new StringBuilder(words[i]).reverse().toString();
      reversedMap.put(reverseWord, i);
    }
    // unique pairs check, format "i:j"
    Set<String> uniques = new HashSet<>();
    // loop each word
    for (int i = 0; i < words.length; i++) {
      // loop possible spliting points 
      // split word into every possible prefixes and postfixes
      // j <= word.len, b/c "aba"(pre) + ""(post) isPalin, "" + "aba" isPalin also.
      for (int j = 0; j <= words[i].length(); j++) {
        String prefix = words[i].substring(0, j);
        String postfix = words[i].substring(j);
        // if prefix is palindrome, postfix can form a palin with reversedPostfix.
        // ex: "acca(bde)" => (edb)acca(bde) palindrome.
        if (isPalindrome(prefix)) {
          // look for any word matches reversedPostfix, Not itself, Not duplicate.
          if (reversedMap.containsKey(postfix) &&
              reversedMap.get(postfix) != i && 
              !uniques.contains(reversedMap.get(postfix) + ":" + i)) {
            // add pair reversedPostfix word + curWord
            res.add(Arrays.asList(reversedMap.get(postfix), i));
            // add to set.
            uniques.add(reversedMap.get(postfix) + ":" + i);
          }
        }
        // if postfix is palindrome, prefix can form a palin with reversedPrefix.
        // ex: "(edb)acca" => (edb)acca(bde) palindrome.
        if (isPalindrome(postfix)) {
          if (reversedMap.containsKey(prefix) &&
             reversedMap.get(prefix) != i &&
             !uniques.contains(i + ":" + reversedMap.get(prefix))) {
            // add pair curWord + reversedPrefix word.
            res.add(Arrays.asList(i, reversedMap.get(prefix)));
            // add to set
            uniques.add(i + ":" + reversedMap.get(prefix));
          }
        }
      }
    }
    return res;
  }
  
  public boolean isPalindrome(String str) {
    int i = 0;
    int j = str.length() - 1;
    while (i < j) {
      if (str.charAt(i) != str.charAt(j)) {
        return false;
      }
      i++;
      j--;
    }
    return true;
  }



  /**
   * Solution 2: Trie, reverse the words.
   * Time: O(N * L^2); N= # of words; L= avg word.len; check palindrome is O(L);
   * Space: O(N * L);
   */
  class TrieNode {
    TrieNode[] letters;
    // index in words array. this node is Starting letter of that word.
    int index;
    // indexes of words that this substr can be a prefix of to form a palindrome.
    List<Integer> prefixList;
    
    TrieNode() {
      letters = new TrieNode[26];
      index = -1;
      prefixList = new ArrayList<>();
    }
  }
  
  TrieNode root = new TrieNode();
  List<List<Integer>> res = new ArrayList<>();
  
  public List<List<Integer>> palindromePairs(String[] words) {
    if (words == null || words.length == 0) {
      return res;
    }
    // form a Trie of reversed words
    for (int i = 0; i < words.length; i++) {
      insert(words[i], i);
    }
    // search prefixes + postfixes palindrome.
    for (int i = 0; i < words.length; i++) {
      searchPair(words, i);
    }
    return res;
  }
  
  // add words, insert to Trie.
  public void insert(String word, int index) {
    // index is index in words array. Indicate it isWord.
    TrieNode curNode = this.root;
    // reversely traverse chars in word
    for (int i = word.length() - 1; i >= 0; i--) {
      char letter = word.charAt(i);
      // search 26 letters nodes.
      if (curNode.letters[letter - 'a'] == null) {
        // not exist curLetter in path. Create it.
        curNode.letters[letter - 'a'] = new TrieNode();
      }
      // check if rest of word - curPrefix/postfix == palindrome.
      if (isPalindrome(word, 0, i)) {
        // n->i+1 can form a palin with its reversed.
        curNode.prefixList.add(index);
      }
      // go to letter node, continue creation of word. Next letter.
      curNode = curNode.letters[letter - 'a'];
    }
    // finished insert chars in path. 
    // word itself is a prefix of itself.
    // "abc" = pre("") post("abc") or pre("abc") post("")
    curNode.prefixList.add(index);
    // Assign index indicate which word it represent.
    curNode.index = index;
  }
  
  public void searchPair(String[] words, int index) {
    // check if curWord's prefixes match any word's reversed postfix.
    String word = words[index];
    TrieNode curNode = root;
    for (int i = 0; i < word.length(); i++) {
      char letter = word.charAt(i);
      // curNode is a word, means substr is reverse of nodeWord.
      // ex: (abc)cc, cba; if not itself like sss, sss; and the rest is palin.
      // ex: cc is palin, so abccc|cba is palin.
      if (curNode.index >= 0 && curNode.index != index &&
          isPalindrome(word, i, word.length() - 1)) {
        res.add(Arrays.asList(index, curNode.index));
      }
      // go to next letter.
      curNode = curNode.letters[letter - 'a'];
      // no more path to check.
      if (curNode == null) {
        return;
      }
    }
    // word letter finished, curNode is at last letter of word.
    // check for any word that can form a palindrome with this substr.
    // ex: "abc" "cc(cba)"; 
    for (int wordIndex : curNode.prefixList) {
      // if itself, skip.
      if (index == wordIndex) {
        continue;
      }
      res.add(Arrays.asList(index, wordIndex));
    }
  }
  
  public boolean isPalindrome(String str, int i, int j) {
    while (i < j) {
      if (str.charAt(i) != str.charAt(j)) {
        return false;
      }
      i++;
      j--;
    }
    return true;
  }
  
}