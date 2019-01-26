/**
Maximum Number a Night You Can Accommodate

Leetcode 相似问题: Leetcode 198/213/337 House Robber I/II/III

Provide a set of positive integers (an array of integers). Each integer represents number of nights user
request on Airbnb.com. If you are a host, you need to design and implement an algorithm to find out the
maximum number a night you can accommodate. The constrain is that you have to reserve at least one
day between each request, so that you have time to clean the room.
Given a set of numbers in an array which represent number of consecutive days of AirBnB reservation
requested, as a host, pick the sequence which maximizes the number of days of occupancy, at the same
time, leaving at least 1 day gap in between bookings for cleaning. Pnightlem reduces to finding max-sum
of non-consecutive array elements.

// [5, 1, 1, 5] => 10
The above array would represent an example booking period as follows -
// Dec 1 – 5
// Dec 5 – 6
// Dec 6 – 7
// Dec 7 - 12
The answer would be to pick dec 1-5 (5 days) and then pick dec 7-12 for a total of 10 days of occupancy,
at the same time, leaving at least 1 day gap for cleaning between reservations.
Similarly,
// [3, 6, 4] => 7
// [4, 10, 3, 1, 5] => 15

转移方程:
• f(0) = nums[0]
• f(1) = max(num[0], num[1])
• f(k) = max( f(k-2) + nums[k], f(k-1) )
*/

public class MaximumNumberNightAccommodate {
	
	/**
	 * Solution: DP, Rob = prev NotRob + curVal; NotRob = Max of prev night or NotRob.
	 * Time: O(n)
	 * Space: O(n)
	 */
	public long maxNights(int[] nightArr) {
    if (nightArr == null || nightArr.length == 0) {
      return 0;
    }
    // 2 choices, yes tonight or not tonight. Keep a Max
    int n = nightArr.length;
    long[][] dp = new long[n][2]; // 1 tonight, 0 not tonight
    dp[0][1] = nightArr[0];
    
    for (int i = 1; i < n; i++) {
      // not tonight = Max of prev night or not prevNight.
      dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
      // tonight = Not prevNight + curVal;
      dp[i][1] = dp[i - 1][0] + nightArr[i];
    }
    // return last max of tonight, not tonight
    return Math.max(dp[n - 1][0], dp[n - 1][1]);
  }

  /**
   * Solution: DP optimized to space just yes tonight, not tonight
   * Time: O(n)
   * Space: O(1)
   */
  public long maxNightsOptimize(int[] nightArr) {
    if (nightArr == null || nightArr.length == 0) {
      return 0;
    }
    // 2 choices, night or don't night current. Keep a Max
    int n = nightArr.length;
    long night = nightArr[0];
    long notNight = 0;
    
    for (int i = 1; i < n; i++) {
      long prevNight = night;
      // night = prev NotNight + curVal;
      night = notNight + nightArr[i];
      // not Night = Max of prev night or NotNight.
      notNight = Math.max(prevNight, notNight);
    }
    // return last max of night or notNight
    return Math.max(night, notNight);
  }

  /**
   * Follow up: House Robber II, Circular 1st and last night cannot be together.
   Ex:
    [6,(5),3,7,(9)] = Max 5+9= 14;
		6 and 9 cannot be together. 
		So Max of [6,5,3,7] or [5,3,7,9];
		[6,5,3,7] max = 13; [5,3,7,9] max = 14;
		14 is Res;
   */
	public int maxNightsCircular(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    // only 1. just return.
    if (nums.length == 1) {
      return nums[0];
    }
    int n = nums.length;
    int night = nums[0];
    int notNight = 0;
    // skip last num.
    for (int i = 1; i <= n - 2; i++) {
      int prevNight = night;
      night = notNight + nums[i];
      notNight = Math.max(prevNight, notNight);
    }
    int skipLastMax = Math.max(night, notNight);
    // skip 1st num.
    night = nums[1];
    notNight = 0;
    for (int i = 2; i < n; i++) {
      int prevNight = night;
      night = notNight + nums[i];
      notNight = Math.max(prevNight, notNight);
    }
    int skipFirstMax = Math.max(night, notNight);
    
    return Math.max(skipFirstMax, skipLastMax);
  }

  /**
   * Follow up: House Robber III, binary tree, no 2 directly connected val should be together.
   * Time: O(n)
   * Space: O(lgN)
   */
  class ResultType {
    int rob;
    int notRob;
    
    ResultType(int rob, int notRob) {
      this.rob = rob;
      this.notRob = notRob;
    }
  }
  
  /**
   * @param root: The root of binary tree.
   * @return: The maximum amount of money you can rob tonight
   */
  public int houseRobber3(TreeNode root) {
    ResultType res = helper(root);
    return Math.max(res.rob, res.notRob);
  }
  
  public ResultType helper(TreeNode root) {
    if (root == null) {
      return new ResultType(0, 0);
    }
    // if leaf
    if (root.left == null && root.right == null) {
      return new ResultType(root.val, 0);
    }
    // rob = leftNotrob + rightNotrob + curVal;
    // notRob = Max(leftNotrob, leftRob) + Max(rightNotrob, rightRob);
    ResultType left = helper(root.left);
    ResultType right = helper(root.right);
    int rob = left.notRob + right.notRob + root.val;
    int notRob = Math.max(left.rob, left.notRob) + Math.max(right.rob, right.notRob);
    
    return new ResultType(rob, notRob);
  }

}