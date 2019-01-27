/**
LintCode
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

/*
Solution: Use Trie + DP; Trie for word/prefix check, DP for min edit dist calc.
Ex:
a->b->c(w)->d(w)
    ->d(w)
 ->d->c(w)
 
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
	
	class TrieNode {
    TrieNode[] letters;
    boolean isWord;
    
    TrieNode() {
      letters = new TrieNode[26];
      isWord = false;
    }
  }
  
  TrieNode root = new TrieNode();
  
  /**
   * @param words: a set of stirngs
   * @param target: a target string
   * @param k: An integer
   * @return: output all the strings that meet the requirements
   */
  public List<String> kDistance(String[] words, String target, int k) {
    buildTrie(words);
    // dp dist for each row. start from "" -> target.
    int[] dists = new int[target.length() + 1];
    for (int i = 1; i <= target.length(); i++) {
      // from "" -> target = target edits of insertion.
      dists[i] = i;
    }
    search(target, k, dists, "", root);
    return res;
  }
  
  List<String> res = new ArrayList<>();
  
  public void search(String target, int k, int[] dists, String pathStr,
      TrieNode curNode) {
    // check if curNode path is a word, and within k edits
    if (curNode.isWord && dists[target.length()] <= k) {
      res.add(pathStr);
    }
    // continue search this path.
    // check existing letter nodes path.
    for (int i = 0; i < 26; i++) {
      if (curNode.letters[i] == null) {
        continue;
      }
      // found a path;
      // recalc the dists;
      int[] updatedDists = new int[target.length() + 1];
      // 1st col = word -> "" tar; wordLen(pathStr.len) # of dels; = prevDist[0]+1;
      updatedDists[0] = dists[0] + 1;
      // <- + 1 insert, \ + 1 replace, | + 1 del; \ same chars;
      // loop and calc
      char letter = (char) (i + 'a');
      for (int j = 1; j <= target.length(); j++) {
        if (letter == target.charAt(j - 1)) {
          // \ same char;
          updatedDists[j] = dists[j - 1];
        } else {
          // not same letters; 1+ Min of <-, \, |;
          updatedDists[j] = 1 + Math.min(updatedDists[j - 1], 
              Math.min(dists[j - 1], dists[j]));
        }
      }
      // go down this path.
      search(target, k, updatedDists, pathStr + letter, curNode.letters[i]);
    }
  }
  
  public void buildTrie(String[] words) {
    for (int i = 0; i < words.length; i++) {
      insert(words[i], 0, root);
    }
  }
  
  public void insert(String word, int index, TrieNode curNode) {
    if (index >= word.length()) {
      curNode.isWord = true;
      return;
    }
    char letter = word.charAt(index);
    if (curNode.letters[letter - 'a'] == null) {
      curNode.letters[letter - 'a'] = new TrieNode();
    }
    insert(word, index + 1, curNode.letters[letter - 'a']);
  }


}