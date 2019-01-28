/**
132. Word Search II
Given a matrix of lower alphabets and a dictionary. Find all words in the dictionary that can be found in the matrix. A word can start from any position in the matrix and go left/right/up/down to the adjacent position. One character only be used once in one word. No same word in dictionary

Example
Given matrix:

doaf
agai
dcan
and dictionary:

{"dog", "dad", "dgdg", "can", "again"}

return {"dog", "dad", "can", "again"}


dog:
doaf
agai
dcan
dad:

doaf
agai
dcan
can:

doaf
agai
dcan
again:

doaf
agai
dcan
Challenge
Using trie to implement your algorithm.
*/

public class WordSearchII {
  
  class TrieNode {
    TrieNode[] letters;
    String word;
    
    TrieNode() {
      word = null;
      letters = new TrieNode[26];
    }
  }
  
  /**
   * @param board: A list of lists of character
   * @param words: A list of string
   * @return: A list of string
   * Time: O((rows * cols) * 4^wl); We traverse each board grid, until word's len surpassed (longest branch in Trie).
	 * Space: O(w * l); words * wordLen;
   */
  public List<String> wordSearchII(char[][] board, List<String> words) {
    buildTrie(words);
    // dfs start with every x,y point
    int rows = board.length;
    int cols = board[0].length;
    boolean[][] visited = new boolean[rows][cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        dfs(board, r, c, visited, root);
      }
    }
    return res;
  }
  
  List<String> res = new ArrayList<>();
  
  int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
  
  TrieNode root = new TrieNode();
  
  public void dfs(char[][] board, int r, int c, boolean[][] visited,
      TrieNode curNode) {
    // cycle
    if (visited[r][c]) {
      return;
    }
    visited[r][c] = true;
    char letter = board[r][c];
    // check for letter node path.
    if (curNode.letters[letter - 'a'] == null) {
      visited[r][c] = false;
      return;
    }
    // exist letter path.
    // check if is word
    if (curNode.letters[letter - 'a'].word != null) {
      res.add(curNode.letters[letter - 'a'].word);
      // prevent repeat, remove the word added to res; Ex: dad = reversed(dad);
      curNode.letters[letter - 'a'].word = null;
    }
    // continue path, with 4 dirs.
    int rows = board.length;
    int cols = board[0].length;
    for (int i = 0; i < 4; i++) {
      int newR = r + dirs[i][0];
      int newC = c + dirs[i][1];
      // check boundary
      if (newR >= 0 && newR < rows && newC >= 0 && newC < cols) {
        // go to path
        dfs(board, newR, newC, visited, curNode.letters[letter - 'a']);
      }
    }
    // allow overlapping of chars, reuse char in board for diff words;
    visited[r][c] = false;
  }
  
  public void buildTrie(List<String> words) {
    for (String word : words) {
      insert(word, root);
    }
  }
  
  public void insert(String word, TrieNode root) {
    if (word == null || word.length() == 0) {
      return;
    }
    TrieNode curNode = root;
    // loop word chars
    for (int i = 0; i < word.length(); i++) {
      char letter = word.charAt(i);
      if (curNode.letters[letter - 'a'] == null) {
        curNode.letters[letter - 'a'] = new TrieNode();
      }
      curNode = curNode.letters[letter - 'a'];
    }
    curNode.word = word;
  }
  
  
}