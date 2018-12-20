/**
376. Wiggle Subsequence
A sequence of numbers is called a wiggle sequence if the differences between successive numbers strictly alternate between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence with fewer than two elements is trivially a wiggle sequence.

For example, [1,7,4,9,2,5] is a wiggle sequence because the differences (6,-3,5,-7,3) are alternately positive and negative. In contrast, [1,4,7,2,5] and [1,7,4,5,5] are not wiggle sequences, the first because its first two differences are positive and the second because its last difference is zero.

Given a sequence of integers, return the length of the longest subsequence that is a wiggle sequence. A subsequence is obtained by deleting some number of elements (eventually, also zero) from the original sequence, leaving the remaining elements in their original order.

Example 1:

Input: [1,7,4,9,2,5]
Output: 6
Explanation: The entire sequence is a wiggle sequence.
Example 2:

Input: [1,17,5,10,13,15,10,5,16,8]
Output: 7
Explanation: There are several subsequences that achieve this length. One is [1,17,10,13,10,16,8].
Example 3:

Input: [1,2,3,4,5,6,7,8,9]
Output: 2
Follow up:
Can you do it in O(n) time?
*/

/**
Solution: DP, record max of up and down at each pos.
[1,7,10,5,6,6]
       1  7  10  5  6  6
/ dir: 1  2  2   2  4  4 
\ dir: 1  1  1   3  3  3

7>1 / dir: down1+1=2; 10>7 / dir: down1+1=2; 5<10 \ dir: up2+1=3;
6>5 / dir: down3+1=4; 6=6 nothing change.
ANS = 4;

How to Arrive:
* This question can be transform into looking for the max direction changes.
* Ex: [1,3,4,6,1,2,0,4]; Notice: b/c we can eliminates nums that don't fit the seq, [1,3,4,6] can just be counted as 1 / dir, which is 2 in len; 
* so we have (1,3,4,6)up, 1down, 2up, 0down, 4up; Ans= 6;
* When there is **dir change we + 1**;
* There are 2 possibilities for dir change: 
* Case 1: from / to \; down dir:
	* Find the previous or leftmost up dir count, and +1 to it;
	* Ex: [4,7,4,3,2] leftmost / dir in 4->7: count=2; so at right \ dir num 2: 2+1=3;
* Case 2: from \ to /; up dir:
	* Find the previous or leftmost down dir count, and +1;
	* Ex: [7,7,4,5,6,3,3,4]; Leftmost \ dir in num 6->3: count=4; So at num 4: 4+1=5;
* At each pos, we record BOTH their max count for Up and Down dir.
	* If prev < cur, it is upMax = prevDown + 1; downMax stay the same.
	* if prev > cur, downMax = prevUp + 1; upMax stay same.
	* if prev = cur, nothing changes.
* Ex: either last one is up or down or =.
	* /\/\/  Or \/\/\ or /\/=/\=;
* Time: O(n);
* Space: Notice we only need maxDown and maxUp upto prev pos. 
* so O(1) for 2 vars. O(n) if use a array to record every pos max up and down.
*/

class WiggleSubsequence {

	/**
	 * Solution 2: DP, Optimized space, using 2 var upLen and downLen as memorized val.
	 * Time: O(n)
	 * space: O(1)
	 */
	public int wiggleMaxLength(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    // max wiggle for cur Up dir, and Down dir. Init 1 for 1st char;
    int downLen = 1;
    int upLen = 1;
    // loop and calc up and down max wiggle
    for (int i = 1; i < nums.length; i++) {
      // prev < cur. / dir: check down+1
      if (nums[i] > nums[i - 1]) {
        upLen = downLen + 1;
      } else if (nums[i] < nums[i - 1]) {
        // prev > cur. \ dir: check up+1
        downLen = upLen + 1;
      }
      // == stay the same
    }
    // max is either / dir or \ dir.
    return Math.max(downLen, upLen);
  }

	/**
	 * Solution 1: DP, record max of up and down at each pos;
	 * Time: O(n)
	 * Space: O(n)
	 */
  public int wiggleMaxLength(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    // max wiggle for up 1 and down 0 at each pos.
    int[][] dp = new int[nums.length][2];
    // 1st num is always 1;
    dp[0][0] = 1;
    dp[0][1] = 1;
    // loop and calc up and down max wiggle
    for (int i = 1; i < nums.length; i++) {
      // prev < cur. / dir: check down+1
      if (nums[i] > nums[i - 1]) {
        dp[i][1] = dp[i - 1][0] + 1;
        // max of down dir is still the same;
        dp[i][0] = dp[i - 1][0];
      } else if (nums[i] < nums[i - 1]) {
        // prev > cur. \ dir: check up+1
        dp[i][0] = dp[i - 1][1] + 1;
        // max of up dir is still the same.
        dp[i][1] = dp[i - 1][1];
      } else {
         // prev = cur. Nothing change. 
        dp[i][0] = dp[i - 1][0];
        dp[i][1] = dp[i - 1][1];
      }
    }
    // max is either / dir or \ dir.
    return Math.max(dp[nums.length - 1][0], dp[nums.length - 1][1]);
  }
}