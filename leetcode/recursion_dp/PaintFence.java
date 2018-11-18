/**
276. Paint Fence
There is a fence with n posts, each post can be painted with one of the k colors.
You have to paint all the posts such that no more than two adjacent fence posts have the same color.
Return the total number of ways you can paint the fence.

Example
Given n=3, k=2 return 6

      post 1,   post 2, post 3
way1    0         0       1 
way2    0         1       0
way3    0         1       1
way4    1         0       0
way5    1         0       1
way6    1         1       0
Notice
n and k are non-negative integers.
*/

/**
Solution: DP same & diff choices.
How to arrive:
* Some math involve in comming up with solution.
* There are 2 possibilities, either **Same or Diff** as the prev color. Meaning *Total Choices = Same choices + Diff choices*.
* Now, what is the base?
	* Thinking about 1 post, and k colors. Same color as prev = 0 choices, since no prev color yet. So, k choices for diff color.
	* P1 = 0 for same, k for diff, total = 0+k = **k choices** for p=1;
	
* Now P2, same color as P1 choice is 1. So k * 1 for 2 posts have same color. 
* **Key**: notice k * 1 here k is the prevDiff of P1, not the total. B/c **in order for this to be same as prev, prev 2 posts have to be diff**. Since p1 only have diffs, it's k. 
* P2 diff color from P1, choice is k-1. So k * (k-1) for 2 posts have diff color.
* **Key**: notice k * (k-1);  (k-1) is the p2 diff. K in this case is the Total of p1 the prev. B/c **whether prev 2 have same or diff colors, the cur Diff choices will always be k-1**.
* Total = k + k * (k-1); same + diff; for 2 posts.
* To clearify the idea, Let's look at P3. Same idea as P2.
	* P3 Same color as prev = prevDiff * 1; **Only 1 Choice to be same as prev**, prevDiff is k * (k-1), So curSame = k * (k-1) * 1;
	* Ex: k=2 [B,R],  p=3; 2 posts have 2 ways B,R or R,B; 2 * (2-1) = 2; Now for 3rd to be same, choices {B,R,R} or {R,B,B} still 2 choices.
	* **Again**: in order for this to be same as prev, prev 2 posts have to be diff;
	* P3 Diff color as prev = prevTotal + (k-1); 
	* **Again**:  Whether prev 2 have same or diff colors, the cur Diff choices will always be k-1.
	* Ex: k=3 [B,R,Y], first 2 is diff {B,R}, the 3rd can only be Y or B to be Diff from prev, as for prev Same {B,B} 3rd can be Y or R to be diff. Either is k-1;
* Now we just carry the result of Same and Diff choices of current to the next one to calc next Same and Diff.
* return same + diff choices as total.
* Time: O(n);
* Space: O(1);
*/

import java.util.*;
import java.io.*;

public class PaintFence {

  /**
   * @param n: non-negative integer, n posts
   * @param k: non-negative integer, k colors
   * @return: an integer, the total number of ways
   */
  public int numWays(int n, int k) {
    if (n == 0) {
      return 0;
    }
    // 1st post choices. No prev color yet, so all diff.
    int same = 0;
    int diff = k;
    // start from 2nd post.
    for (int i = 2; i <= n; i++) {
      int prevSame = same;
      // same color as prev = prevDiff x 1; only 1 choice to be same.
      // and prev 2 must have diff colors, so just prevDiff.
      same = diff;
      // k-1 choices for cur to be diff color than last.
      // total prevChoices * cur diff choices.
      diff = (prevSame + diff) * (k - 1);
    }
    return same + diff;
  }
}