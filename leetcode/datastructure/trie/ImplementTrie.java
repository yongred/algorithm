/**
208. Implement Trie (Prefix Tree)
Implement a trie with insert, search, and startsWith methods.

Example:

Trie trie = new Trie();

trie.insert("apple");
trie.search("apple");   // returns true
trie.search("app");     // returns false
trie.startsWith("app"); // returns true
trie.insert("app");   
trie.search("app");     // returns true
Note:

You may assume that all inputs are consist of lowercase letters a-z.
All inputs are guaranteed to be non-empty strings.
*/

/**
Solution: Level search for node children[26] letters, using ASCII [char-'a'];
How to Arrive:
* We first define a Node class:
	* 2 variables: 
	* *isEndOfWord*: indicates from root to curNode this word exist or not.
	* *children*: Node of 26 lowercase letters. Array. Init to Null.
* Now we can declare a member Root of the trie.
* All 3 functions follows the same level search format:
	* declare a Node = root;
	* Loop every letters of Word:
		* At each charPos of the word, check the same level Children contain that letter.
		*  If curNode.children don't have that letter: (null);
			*  For Insert function: we create newNode for that letter in curNode.children;
			*  For Search and StartsWith function: we just return false; Since not found.
		* Go down to that letterNode's path; curNode = curNode.children[letterIndex];
	* Finished checking every letters in word, curNode is at last LetterNode;
	* For Insert function: curNode isEndOfWord turn true;
	* For Search function: check curNode isEndOfWord, yes return true, else false;
	* For StartsWith function: just return true, since we checked every letters, and we just want to know if prefix exist, not a word exist.
* Time: O(L) just the len of the word, for search, for 3 functions.
* Space: O(w * l); Every letters in words inserted.

*/

class ImplementTrie {
  
  class Node {
    // is end of word
    boolean isEndOfWord = false;
    // children 26 letters.
    Node[] children = new Node[26];
    
    Node() {}
  }
  
  Node root;

  /** Initialize your data structure here. */
  public ImplementTrie() {
    root = new Node();
  }

  /** Inserts a word into the trie. */
  public void insert(String word) {
    // level search.
    Node curNode = root;
    // chars in word
    for (int level = 0; level < word.length(); level++) {
      char letter = word.charAt(level);
      int letterIndex = letter - 'a';
      // find letter in cur level.
      if (curNode.children[letterIndex] == null) {
        // letter not exist, create letter node.
        curNode.children[letterIndex] = new Node();
      }
      // create path from new node.
      curNode = curNode.children[letterIndex];
    }
    // curNode at last letter Node. IsEndOfWord = true;
    curNode.isEndOfWord = true;
  }

  /** Returns if the word is in the trie. */
  public boolean search(String word) {
    // level search.
    Node curNode = root;
    for (int level = 0; level < word.length(); level++) {
      char letter = word.charAt(level);
      int letterIndex = letter - 'a';
      // find letter in cur level.
      if (curNode.children[letterIndex] == null) {
        // letter not exist.
        return false;
      }
      // letter exist. Get letter node, go down the path.
      curNode = curNode.children[letterIndex];
    }
    // curNode at last LetterNode. Check isEndOfWord.
    return curNode.isEndOfWord;
  }

  /** Returns if there is any word in the trie that starts with the given prefix. */
  public boolean startsWith(String prefix) {
    // do level search. Without checking isEndOfWord.
    Node curNode = root;
    for (int level = 0; level < prefix.length(); level++) {
      char letter = prefix.charAt(level);
      int letterIndex = letter - 'a';
      // find letter in cur level.
      if (curNode.children[letterIndex] == null) {
        // letter not exist.
        return false;
      }
      // letter exist. Get letter node, go down the path.
      curNode = curNode.children[letterIndex];
    }
    // prefix letters all exist.
    return true;
  }
}