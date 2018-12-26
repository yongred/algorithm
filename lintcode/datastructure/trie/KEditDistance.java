/**
623. K Edit Distance
Given a set of strings which just has lower case letters and a target string, output all the strings for each the edit distance with the target no greater than k.

You have the following 3 operations permitted on a word:

Insert a character
Delete a character
Replace a character
Example
Given words = ["abc", "abd", "abcd", "adc"] and target = "ac", k = 1
Return ["abc", "adc"]
*/

/**
Solution: Use Trie + DP; Trie for word/prefix check, DP for min edit dist calc.
How to Arrive:
* This question is build on Top of EditDistance DP question.
* The first EditDistance question uses dp to calc minEdits from w1 to w2;
* Now we have array of words, need to edit to target in max k times.
* Meaning doing dp for every single one of them. Which would take O(numOfWords * lenWord * targetLen);

* **We build Trie to optimize**.
* Instead of building dp for every word with target, we could just built a Trie for words.
* We search Target in the Trie branches. And we calc the MinEdit to get to that pathStr.
* If the pathStr is a word, check the minEdit is <= K. If yes, the Word can reach to target within k edits. Add to resList.

* **Calc minEdit using DP**
* To calc the minEdit to get from curPathStr to target, we use the knowledge of DP from prev EditDistance problem:
* We keep the prevDp row to determine the cur DP.
* Init 1st row, "" to target:
	* from "" to any str, just str.len fo edits.
	* Ex: target = 'abc'; init pathStr = ""; words = [acc, aa]; k=1;
		"" a  b  c
"" [0, 1, 2, 3]
* Now pass Search(target, root, "", k);
* from root.children we have 'a' letterNode exist.
* We update the DP for number of edits from 'pathStr + letter' to 'target'; 
* From "a" to "abc":
* Init curDp[0] = pathStr + 1; edits from 'pathStr + curLetter' to "", is del pathStr.len + 1 times;
* Case 1 letters match: target[0] char 'a' == 'a':
	* curDP of edits don't change, copy prev \ dir;
	   "" a  b  c
"a" [1, 0, 1, 2];
* We goes down the 'a' path.
* "a" is not a word, we do the same check existing letter children of curNode;
* We found 'c' exist, 
* from "ac" to "abc":
* c != b, the target char.
* Case 2 letters not =, we choose the min edit from 3 options:
	* Delete, | up dir, prevDist[i]; (dp[row - 1][col]);
	* Replace, \ dir, prevDist[i-1]; (dp[row-1][col-1]);
	* Insert, -> dir, curDist[i-1]; (dp[row][col-1]);
	* + 1 to the min of the 3 choices. As 1 edit.
      ""  a  b  c
"ac" [2, 1, 1, 2];
in this case we replace ['a']0+1 = 1 edit from ac -> ab;
* We goes down 'c' node's path, with 1 less k; k=1-1=0;
* Node 'c' has a child letter c.
* From "acc" to target "abc":
* c == c; copy this prev prevDist[i-1]; (dp[row-1][col-1]) \ dir;
	* we have 1;
			 "" a  b  c
"acc" [3, 2, 2, 1];
* Goes down 'c' node:
* We found acc isWord:
	* Check the editNum from this word to target:
	* 1 <= 1(k);  So add word "acc" to resList.

* Time: O(w * l); words * length of word. DFS all possible paths.
* Space: O(w * l);
*/

public class KEditDistance {
  
  class Node {
    // is a word.
    boolean isEndOfWord = false;
    // children 26 letters.
    Node[] children = new Node[26];
    
    Node() {}
  }
  
  // trie for words.
  private Node root = new Node();
  // res list.
  List<String> res = new ArrayList<>();
  
  /**
   * @param words: a set of stirngs
   * @param target: a target string
   * @param k: An integer
   * @return: output all the strings that meet the requirements
   * Solution: Use Trie + DP; Trie for word/prefix check, DP for min edit dist calc.
   * Time: O(w * l); words * lenOfWord. DFS all possible paths.
   * Space: O(w * l);
   */
  public List<String> kDistance(String[] words, String target, int k) {
    // build a Trie.
    buildTrie(words);
    // dp for edits dist from pathStr to target, or wise-versa.
    // Ex: tar= "abc" pathStr= ''; dist: ''(0), 'a'(1), 'ab'(2), 'abc'(3);
    int[] initDist = new int[target.length() + 1];
    for (int i = 0; i < initDist.length; i++) {
      initDist[i] = i;
    }
    // search in Trie, calc min edits dist from target to pathWords.
    search(target, k, "", root, initDist);
    return res;
  }
  
  public void buildTrie(String[] words) {
    for (String word : words) {
      insert(word, 0, root);
    }
  }
  
  public void search(String target, int k, String pathStr,
      Node curNode, int[] prevDist) {
    // base case.
    // if path to this node is a word.
    if (curNode.isEndOfWord && prevDist[target.length()] <= k) {
      // dist[target.length()] is the number of edits needed 
      // from pathStr/curWord to target And wise versa. 
      // Ex: tar = 'abc', pathStr = 'ae'; abc <-> ae, diff is 2;
      res.add(pathStr);
    }
    // not a word yet. Check existing path in curNode children. 26 letters.
    for (int i = 0; i < 26; i++) {
      // look for exist letterNode in curNode's children.
      if (curNode.children[i] == null) {
        continue;
      }
      // curNode exist letter.
      char letter = (char) (i + 'a');
      // calc dp dist of str 'pathStr + curLetter' to 'targetStr';
      // same as edit dist from 'targetStr' to 'pathStr + this letter';
      // Ex: tar= "abc" pathStr= 'ae'; dist: ''(2), 'a'(1), 'ab'(1), 'abc'(2);
      int[] curDist = new int[target.length() + 1];
      // edits from 'pathStr + curLetter' to '', is del pathStr.len + 1 times;
      curDist[0] = pathStr.length() + 1;
      // calc the rest Ex: 'a','ab','abc' dists to 'pathStr + curLetter'.
      for (int j = 1; j <= target.length(); j++) {
        // dp check
        if (target.charAt(j - 1) == letter) {
          // letter match, same edit number as prev, no change. \ dir.
          // ex: tar= "a" pathStr= 'a'; a == a; dp('')= 0; dp('a') still = 0;
          curDist[j] = prevDist[j - 1];
        } else {
          // letter not match. Check for min edit to get to targetStr.
          // prevDist[j - 1] \ dir replace, prevDist[j] | del, curDist[j - 1] -> insert.
          curDist[j] = 1 + Math.min(Math.min(prevDist[j - 1], prevDist[j]),
              curDist[j - 1]);
        }
      }
      // check this childLetterNode's path for words.
      search(target, k, pathStr + letter, curNode.children[i], curDist);
    }
    // curNode has no letterNode exist, End of path.
  }
  
  public void insert(String word, int pos, Node curNode) {
    if (pos == word.length()) {
      // added all chars. Mark this path as word.
      curNode.isEndOfWord = true;
      return;
    }
    // check letter exist.
    char letter = word.charAt(pos);
    int letterIndex = letter - 'a';
    if (curNode.children[letterIndex] == null) {
      // letter not exist, create new node for it.
      curNode.children[letterIndex] = new Node();
    }
    // traverse through this node. Create a path.
    insert(word, pos + 1, curNode.children[letterIndex]);
  }
  
}