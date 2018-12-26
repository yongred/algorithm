/**
212. Word Search II
Given a 2D board and a list of words from the dictionary, find all words in the board.

Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

Example:

Input: 
words = ["oath","pea","eat","rain"] and board =
[
  ['o','a','a','n'],
  ['e','t','a','e'],
  ['i','h','k','r'],
  ['i','f','l','v']
]

Output: ["eat","oath"]
Note:
You may assume that all inputs are consist of lowercase letters a-z.
*/

/**
Solutioin: Build a Trie with words, DFS/Backtrack all possible path of board.
How to Arrive:
* Identify the Problems (though process):
	* We need to search every path in board to find word.
	* To check if pathStr is a word, we can use a HashSet;
	* Problem: how do we know if we want to continue checking in cur path?
		* **Key**: HashSet only check the Whole Word string, Not the Partial Word substr.
		* So we need a dataStructure to check **Partial Word** as well as **Whole Word**;
		* **Trie's startsWith and Search functions can do this.**;
		* What do we want to check when searching in paths?
			* If curPathStr exist in prefix of Trie we continue;
			* If curPathStr isWord we add to ResList, and continue to search longer words.
			* **So we can combine startsWith and Search function into one DFS function**;
* Algorithm:
* Build a Trie with words:
	* Defind Node class, Declare a Root for building Trie of words.
	* Create an usual Insert() function of Trie; (level traverse chars, create Nodes of letters);
	* Then loop every word, insert words in Trie;
* Then we want to DFS from with every grid in board:
	* Define DFS function with board, pos (r, c), visited, and curNode;
	* Visited is for checking cycle of current Path.
	* curNode is this level's node to check children letters match.
	* We also need a Dirs array to make [x,y] into up, down, right, left; new pos.
	`dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};`
* If Visited, curPath traversed grid:
	* Do nothing, return;
* Turn visited to true;
* Check if curLetter in the children of curNode.
	* if Not, turn visited to false (finished with this path dfs), And return false;
* This letter exist in curLevel:
* Check cur letterNode is a word: (letterNode != null)
	* resList add this word;
	* **letterNode.word turn to Null, To prevent duplicate word**;  Word choose.
* Now go 4 dirs from LetterNode to check for words.
* Use Dirs array to create new x and y. Go dfs that pos.
* Turn visited to false (finished with this path dfs);
* Now after DFS from every grid in board, we added all possible path to Word.
* Return resList;
 
* Time: O((rows * cols) * 4^wl); We traverse each board grid, until word's len surpassed (longest branch in Trie).
* Space: O(w * l); words * wordLen;

*/

class WordSearchII {

  // trie node
  class Node {
    // instead of boolean, we want to store the word if exist.
    // easier to add to resList.
    String word = null;
    Node[] children = new Node[26];
  }
  
  // trie root for words.
  Node root = new Node();
  // up down left right.
  int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
  // res list.
  List<String> res = new ArrayList<>();
  
  /**
   * Solutioin: Build a Trie with words, DFS/Backtrack all possible path of board.
	 * Time: O((rows * cols) * 4^wl); We traverse each board grid, until word's len surpassed (longest branch in Trie).
	 * Space: O(w * l); words * wordLen;
	 */
  public List<String> findWords(char[][] board, String[] words) {
    if (words == null || words.length == 0) {
      return res;
    }
    int rows = board.length;
    int cols = board[0].length;
    // build a trie;
    buildTrie(words);
    // dfs the board, check in trie to see if path is a word. Add to resList.
    // visited indicates cur path grid traversed.
    boolean[][] visited = new boolean[rows][cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        // check paths from every grid to see if form a word.
        dfs(board, visited, r, c, root);
      }
    }
    // resList added all exist words in board.
    return res;
  }
  
  public void buildTrie(String[] words) {
    // loop all words
    for (String word : words) {
      // create trie branches for each word.
      insert(word, 0, root);
    }
  }
  
  public void insert(String word, int start, Node curNode) {
    // traversed/inserted all chars in word.
    if (start == word.length()) {
      // set path isWord, store the word at end of this path.
      curNode.word = word;
      return;
    }
    char curLetter = word.charAt(start);
    int letterIndex = curLetter - 'a';
    // if cur path don't have this char
    if (curNode.children[letterIndex] == null) {
      // create new node for this letter in curLevel.
      curNode.children[letterIndex] = new Node();
    }
    // build this path from this childLetterNode.
    insert(word, start + 1, curNode.children[letterIndex]);
  }
  
  public void dfs(char[][] board, boolean[][] visited, int r, int c,
      Node curNode) {
    // visited this grid in current dfs. Cycle.
    if (visited[r][c]) {
      return;
    }
    // prevent cycle.
    visited[r][c] = true;
    // check if curNode children exist this letter.
    char curLetter = board[r][c];
    int letterIndex = curLetter - 'a';
    Node childLetterNode = curNode.children[letterIndex];
    if (childLetterNode == null) {
      // not exist. Don't forget to set visited to false.
      visited[r][c] = false;
      return;
    }
    // if letterNode exist, check if is word
    if (childLetterNode.word != null) {
      // cur path is a word. Add to resList;
      res.add(childLetterNode.word);
      // we only this word to added to res 1 time. Remove duplicate.
      childLetterNode.word = null;
    }
    // go 4 dirs
    int rows = board.length;
    int cols = board[0].length;
    for (int i = 0; i < 4; i++) {
      int x = r + dirs[i][0];
      int y = c + dirs[i][1];
      // check boundary.
      if (x >= 0 && x < rows && y >= 0 && y < cols) {
        // traverse this dir in graph, with letterNode finding match words.
        dfs(board, visited, x, y, childLetterNode);
      }
    }
    // finish visiting this grid's path, no longer in this path.
    // set this grid open for next future dfs. (Backtracking method).
    visited[r][c] = false;
  }
  
}