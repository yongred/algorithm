/**
913. Flip Game II
You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

Write a function to determine if the starting player can guarantee a win.

Example
Given s = "++++", return true.

Explanation:
The starting player can guarantee a win by flipping the middle "++" to become "+--+".
Challenge
Derive your algorithm's runtime complexity.
*/

/**
Solution: Recursive, memorization, top-down.
() is repeated.
++++++:  --++++, +--+++, ++--++, +++--+, ++++--;
2nd
--++++: ----++, --+--+, --++--,
+--+++: +----+, +--+--,
++--++: (----++), ++----
+++--+: (--+--+), (+----+)
++++--:  (--++--), (+--+--), (++----)
3nd:
----++: ------
--++--: (------)
++----: (------)

How to arrive:
* To know whether cur board will win as starter, we need to check every move, see if it leads to a win.
* To know if cur move leads to a win, pass the new board to check if opponent will win.
* If opponent cannot guarantee a win then this move will guarantee a win.
* So there we have the Recursive Case.
```
if (chArr[i] == '+' && chArr[i + 1] == '+')
	chArr[i] = '-';
	chArr[i + 1] = '-';
	String newBoard = new String(chArr);
	// if opponent cannot guarantee a win.
	res = !helper(newBoard, memo);
```
* Base case: when there is no more move, ex: ------, +----+
* Now that will cause n!!; or n^n: since loop whole string (n); For n!!, recurse every move (n-2), if we only count ++ iterations, (if statement executed);
* To Optimize, we can use a HashMap<String, boolean> key is board, boolean is res (can guarantee a win); To memorize the results.
* Time: O(n^2); w/o memo is n!; or n^n; since loop whole string (n) and recurse every move (n-1);
* With memo: just all possible moves.
* Space: O(n^2); possible moves.

*/

public class FlipGameII {
  
  /**
   * @param s: the given string
   * @return: if the starting player can guarantee a win
   * Solution: recursive, memorization, top-down.
   * Time: O(n^2); w/o memo is n!; or n^n; since loop whole string (n) and recurse every move (n-1);
   * 		With memo: just all possible moves.
   * Space: O(n^2); all possible moves.
   */
  public boolean canWin(String s) {
    // have a hashmap to store prev calculated.
    HashMap<String, Boolean> memo = new HashMap<>();
    boolean res = helper(s, memo);
    return res;
  }
  
  public boolean helper(String s, HashMap<String, Boolean> memo) {
    if (s == null || s.length() <= 1) {
      return false;
    }
    // memorized 
    if (memo.containsKey(s)) {
      return memo.get(s);
    }
    boolean res = false;
    char[] chArr = s.toCharArray();
    // loop each pos, find test all possible next move
    for (int i = 0; i < chArr.length - 1; i++) {
      // if find a move ++, flip and see if leads to win.
      if (chArr[i] == '+' && chArr[i + 1] == '+') {
        chArr[i] = '-';
        chArr[i + 1] = '-';
        String newBoard = new String(chArr);
        // if opponent cannot guarantee a win.
        res = !helper(newBoard, memo);
        // if found a win.
        if (res) {
          break;
        }
        // flip back
        chArr[i] = '+';
        chArr[i + 1] = '+';
      }
    }
    // memorized
    memo.put(s, res);
    return res;
  }
    
    
}