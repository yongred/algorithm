/**
211. Add and Search Word - Data structure design
Design a data structure that supports the following two operations:

void addWord(word)
bool search(word)
search(word) can search a literal word or a regular expression string containing only letters a-z or .. A . means it can represent any one letter.

Example:

addWord("bad")
addWord("dad")
addWord("mad")
search("pad") -> false
search("bad") -> true
search(".ad") -> true
search("b..") -> true
Note:
You may assume that all words are consist of lowercase letters a-z.
*/

/**
Solution: Use a Trie definition implementation of Insert and search. With DFS of '.' case.
How to Arrive:
* The question is clear that it wants us to implement a trie like datastructure.
* We first define a Node class:
	* 2 variables: 
	* *isEndOfWord*: indicates from root to curNode this word exist or not.
	* *children*: Node of 26 lowercase letters. Array. Init to Null.
* Now we can declare a member Root of the trie.
* For Add function: same as a trie.
	* We do a level traversal:
		* At each charPos of the word, check the same level Children contain that letter.
		* If curNode.children don't have that letter: (null);
			* We need to Create a new Node for that letter in curNode.children;
			`int letterIndex = word.charAt(level) - 'a';
      if (curNode.children[letterIndex] == null) {
        curNode.children[letterIndex] = new Node();
      }
      // use this letterNode to connect next letter.
      curNode = curNode.children[letterIndex];`
			* then we traverse down this newNode and add next letter to it, to create the word branch path.
			* After finish adding all chars, we need to set lastNode's isEndOfWord to true;
* For Search function: DFS
	* We will pass arguments: the word, cur index of the word, and curNode.
	* Base case:
		* if pos = word.len; means finished all chars:
			* Check if curNode isEndOfWord;
			* return true if it is. Else false;
	* Recursive Case:
		* if curChar == '.'
			* B/c '.' matches all letters, we don't know which path can leads to a correct match.
			* So we have to search all existing letter's path of curLevel.
			* Loop 26 letters children of curNode.
				* if child is not Null, means letter path exist.
					* we search that path (dfs)
					* if found the match from that path:
						* return true;
				`if (curNode.children[i] != null) {
          // dfs this path.
          boolean found = dfs(word, curNode.children[i], pos + 1);
          if (found) {
            return true;
          }
        }`
		* If curChar is a letter:
			* check if that letter exist in curNode's children:
				* if exist return true;
	      * return false otherwise;
* Time: Add is O(w) word's len;  
	* Search is O(n) at worst the whole Trie, all paths traverse once.
* Space: O(n) all keys;
*/

class AddSearchWordDesign {
  
  // implement a trie
  class Node {
    // root -> curChar is a word or not.
    boolean isEndOfWord = false;
    // array of children nodes. 26 letters;
    Node[] children = new Node[26];
    
    Node() {}
  }
  
  private Node root;

  /** Initialize your data structure here. */
  public AddSearchWordDesign() {
    root = new Node();
  }

  /** Adds a word into the data structure. 
  Add is O(w) word's len;  
  */
  public void addWord(String word) {
    // search and insert not existed chars.
    Node curNode = root;
    for (int level = 0; level < word.length(); level++) {
      // check if char exist in cur level.
      int letterIndex = word.charAt(level) - 'a';
      if (curNode.children[letterIndex] == null) {
        // this char not in this level. 
        // Create node for this letter in curLevel.
        // != Null means letter in curLevel exist.
        curNode.children[letterIndex] = new Node();
      }
      // use this letterNode to connect next letter.
      curNode = curNode.children[letterIndex];
    }
    // curNode at last inserted letterNode.
    // this letterNode is an endOfWord.
    curNode.isEndOfWord = true;
  }

  /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. 
  Search is O(n) at worst the whole Trie, all paths traverse once.
  */
  public boolean search(String word) {
    // dfs start with 1st char and root.
    return dfs(word, root, 0);
  }
  
  public boolean dfs(String word, Node curNode, int pos) {
    // base case, finished traverse all chars in word.
    // and parentNode is a word.
    if (pos == word.length() && curNode != null && curNode.isEndOfWord) {
      return true;
    } 
    // check all chars, but not a word.
    if (pos >= word.length()){
      return false;
    }
    char curChar = word.charAt(pos);
    if (curChar == '.') {
      // for '.' matches a letter, but we don't know which path is correct.
      // check if any path from cur level leads to a match.
      for (int i = 0; i < 26; i++) {
        // look for exist letter/path in this level.
        if (curNode.children[i] != null) {
          // dfs this path.
          boolean found = dfs(word, curNode.children[i], pos + 1);
          if (found) {
            return true;
          }
        }
      }
    } else {
      // curChar is a letter. Check if letter exist in curLevel.
      if (curNode.children[curChar - 'a'] != null) {
        return dfs(word, curNode.children[curChar - 'a'], pos + 1);
      }
    }
    return false;
  }
  
  
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */