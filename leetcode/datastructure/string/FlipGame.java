/**
293. Flip Game
You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

Write a function to compute all possible states of the string after one valid move.

Example
Given s = "++++", after one move, it may become one of the following states:

[
  "--++",
  "+--+",
  "++--"
]
If there is no valid move, return an empty list [].
*/

/**
Solution: Check cur and next, if both ++ flip.
Ex: +++++ = --+++, +--++, ++--+, +++--;
How to arrive:
* Straight foward, all we need to check is if 2 +s are together, so c[i] and c[i+1] == +; Then flip both to -, and Store in res list.
* **Key**: After store recover the flipped +s i and i+1 to origin. So we can use it for next pos check.
* Time: O(s), str len.
* Space: O(s^2) = T(s-1) * T(s), s chars * (max # of possible flips = s-1; 
* Ex: +++++ = --+++, +--++, ++--+, +++--; 4 ways = 5-1; total chars -1 ways; 
*/

public class FlipGame {

  /**
   * @param s: the given string
   * @return: all the possible states of the string after one valid move
   * Solution: Check cur and next, if both ++ flip.
   * Time: O(s), str len.
   * Space: O(s^2) = T(s-1) * T(s), s chars * (max # of possible flips = s-1); 
   * Ex: +++++ = --+++, +--++, ++--+, +++--; 4 ways = 5-1; total chars -1 ways; 
   */
  public List<String> generatePossibleNextMoves(String s) {
    // "++++" -> "--++", "+--+", "++--"
    if (s == null || s.length() == 0 || s.length() == 1) {
      return new ArrayList<>();
    }
    List<String> res = new ArrayList<>();
    char[] chArr = s.toCharArray();
    // loop through chars in s. until i < len-1;
    for (int i = 0; i < chArr.length - 1; i++) {
      // if cur == +, and next == +, change cur and next pos into -.
      if (chArr[i] == '+' && chArr[i + 1] == '+') {
        chArr[i] = '-';
        chArr[i + 1] = '-';
        // convert charArr to string save in res.
        res.add(new String(chArr));
        // recover the 2 pos back to +;
        chArr[i] = '+';
        chArr[i + 1] = '+';
      }
    }
    return res;
  }
}