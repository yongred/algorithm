/**
LintCode: 635 Boggle Game

Given a board which is a 2D matrix includes a-z and dictionary dict, find the largest collection of words on the board, the words can not overlap in the same position. return the size of largest collection.

Leetcode 相似问题: Leecode #79/#212 Word Search I/II
http://www.1point3acres.com/bbs/thread-191416-1-1.html

比如: 给定一个 2d matrix of letters 和一个 dictionary,找出一条 path 包含最多的存在于字典的
word 个数
http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=158462

给你一个 board, 一个 dict 让你计算最多能有多少个 valid 单词出现在这个 Board 上面
http://www.1point3acres.com/bbs/thread-220456-1-1.html

Trie + DFS for search

DFS for max num
这题真的好蛋疼啊,首先你要找出一个单词所有可能出现的位置序列,然后根据每个单词出现的
序列在分别做 DFS,分别是不取这个单词,取第一个序列,取第二个序列,etc。
http://www.1point3acres.com/bbs/thread-158462-1-1.html

比如你现在走了一个词 apple, 那么 a, p, p, l, e 这几个 char 的位置不能继续用了. 于是给你一个
board, 一个 dict 让你计算最多能有多少个 valid 单词出现在这个 Board 上面

此题和 LeetCode #212 Word Search II 的区别在于找出一条 path 包含最多的存在于字典的 word 个
数。 总结一下,有两种解法,都是基于 word search II 的:

• 用 trie+DFS 找到一个 word 之后,wordCounter 加 1,从这个 word 最后一个 character 的位
置再用 trie+DFS 继续查找,知道相邻位置都用过了,或者没有在 trie 里找到 match。

• 用 trie+DFS 找到所有 words 可能出现的位置序列. 然后根据每个单词出现的序列在分别做
DFS,分别是不取这个单词,取第一个序列,取第二个序列,etc。
*/

/**
Solution: Build Trie, DFS backtracking.
How to Arrive:
* Almost the same as solving WordSearchII. 
	* The Main diff: No overlap of words in BoggleGame.
		* WordSearchII we can use char multiple times for diff words. Ex: "abc", "abe" both added to res words;
		* But in BoggleGame, if found "abc" those 3 char pos cannot be used again. meaning "abe" just left with 'e' avaliable.
	* The other diff: Finding Max # of words if we can coExist in board w/o overlapping.
	* To solve these 2 diffs:
		* We pass a List to keep track of cur paths' words. Use curList's Size to compare with Max # of words so far.
		* If curNode is a Word:
			* We do it the backtracking way, where we choose/add a word to curList, ;
			* Since we cannot overlap/reuse these path chars, we keep the visited=true for these pos. 
			* We will search other words in other positions. Starting from the Root again, b/c we looking for new path words;
			* Basically going to next position in Backtracking. 
			* Then, unchoose this word for cur position, If prefix + otherLetter can leads to other word's paths;
    	* Ex: instead of "bad", unchoose 'd' for 't' => "bat"; See if it leads to more words;
			* Return; Not Continuing this path. Because we are looking for Max # of Words. The shorter the word the better chance for more words;

* Time: O((r * c) * 4^wl); dfs start with every grid, going 4 dirs;
* Space: O(wl + rc);

*/

import java.io.*;
import java.util.*;

public class BoggleGame {

	class TrieNode {
    TrieNode[] letters;
    String word;
    
    TrieNode() {
      word = null;
      letters = new TrieNode[26];
    }
  }

  /**
   * Solution: Build Trie, DFS backtracking.
   * Time: O((r * c) * 4^wl); dfs start with every grid, going 4 dirs;
   * Space: O(wl + rc);
   */
  public int boggleGame(char[][] board, String[] dict){
  	buildTrie(dict);
    // dfs start with every x,y point
    int rows = board.length;
    int cols = board[0].length;
    boolean[][] visited = new boolean[rows][cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        dfs(board, r, c, visited, root, new ArrayList<>());
        // clear, reset visited for next startingPoint traverse
        clearVisited(visited);
      }
    }
    return maxWords;
  }

  public void clearVisited(boolean[][] visited) {
  	for (int i = 0; i < visited.length; i++) {
  		Arrays.fill(visited[i], false);
  	}
  }

  int maxWords = 0;

  int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
  
  TrieNode root = new TrieNode();

  public void dfs(char[][] board, int r, int c, boolean[][] visited,
      TrieNode curNode, List<String> foundList) {
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

    int rows = board.length;
    int cols = board[0].length;
    // If cur LetterNode is a word
    if (curNode.letters[letter - 'a'].word != null) {
    	// choose this word for cur position, backtacking.
    	foundList.add(curNode.letters[letter - 'a'].word);
    	// update Max Words
    	maxWords = Math.max(maxWords, foundList.size());
    	// this path's x,y grids occupied, go found other not visited grids for words.
    	for (int x = 0; x < rows; x++) {
    		for (int y = 0; y < cols; y++) {
    			// search other words in other positions.
    			// Starting from the Root, b/c we looking for new path words;
    			dfs(board, x, y, visited, root, foundList);
    		}
    	}
    	// unchoose this word for cur position, If prefix + otherLetter can leads to other word's paths;
    	// Ex: instead of "bad", unchoose 'd' for 't' => "bat"; See if it leads to more words;
    	foundList.remove(foundList.size() - 1);
    	// Don't have to search deeper into this path, since we are looking for Max # of Words.
    	// The shorter the word the better chance for more words;
    	return;
    }

    // continue path, with 4 dirs.
    for (int i = 0; i < 4; i++) {
      int newR = r + dirs[i][0];
      int newC = c + dirs[i][1];
      // check boundary
      if (newR >= 0 && newR < rows && newC >= 0 && newC < cols) {
        // go to path
        dfs(board, newR, newC, visited, curNode.letters[letter - 'a'], foundList);
      }
    }
    // allow overlapping of chars, reuse char in board for diff words;
    visited[r][c] = false;
  }

  public void buildTrie(String[] words) {
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


  public static void main(String[] args) {
  	BoggleGame obj = new BoggleGame();
  	String[] dict = new String[] {"aba", "bed", "fi", "cgd", "cg", "bec"};
  	char[][] board = new char[][] {
  		{'a','b','a'},
  		{'i','e','d'},
  		{'f','c','g'}
  	};
  	System.out.println(obj.boggleGame(board, dict));
  }

}