/**
337. House Robber III
The thief has found himself a new place for his thievery again. There is only one entrance to this area, called the "root." Besides the root, each house has one and only one parent house. After a tour, the smart thief realized that "all houses in this place forms a binary tree". It will automatically contact the police if two directly-linked houses were broken into on the same night.

Determine the maximum amount of money the thief can rob tonight without alerting the police.

Example 1:

Input: [3,2,3,null,3,null,1]

     3
    / \
   2   3
    \   \ 
     3   1

Output: 7 
Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.
Example 2:

Input: [3,4,5,1,3,null,1]

     3
    / \
   4   5
  / \   \ 
 1   3   1

Output: 9
Explanation: Maximum amount of money the thief can rob = 4 + 5 = 9.
*/

/**
Solution: DP, postorder; 2 choices rob or notRob curRoot;
Ex:
          (4)
          / \
        2  2
      /       \
    1        (3)
  /              \
(3)              1

How to Arrive:
* From the House Robber question before, we know that we only have 2 choices, either rob or dont rob current root(house);
* So we need 2 vals (rob, notRob) to return, and determine max(rob, notRob);
* Create a ResultType of (rob, notRob);
* How do we get rob and notRob of curRoot?
  * root.rob = left.notrob + right.notrob + root.val;
    * If we decide to rob this Root, that means left and right cannot be robbed, so the max of Not Robbing left and right + curRoot.val = Max of robbing curRoot.
  * root.notRob = max(left.rob, left.notRob) + max(right.rob, right.notRob);
    * If we decide not to rob this Root, Then there are 4 cases:
    * Case1: left.rob + right.rob
    * Case2: left.notrob + right.notrob
    * Case3: left.rob + right.notrob
    * Case4: left.notrob + right.rob
    **Key**: you don't have to rob left or right immediate child if you not robbing curRoot.
    * Finding the Max of these 4 cases = max(left.rob, left.notRob) + max(right.rob, right.notRob);
* Then return (root.rob, root.notRob); Find max between these 2;
* Time: O(n)
* Space: O(n)
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class HouseRobberIII {

  /**
   * Solution: DP, postorder; 2 choices rob or notRob curRoot;
   * Time: O(n)
   * Space: O(n)
   */
  public int rob(TreeNode root) {
    ResultType res = helper(root);
    return Math.max(res.rob, res.notRob);
  }
  
  class ResultType {
    // max of robbing cur root. and not robbing cur root.
    int rob;
    int notRob;
    
    ResultType(int rob, int notRob) {
      this.rob = rob;
      this.notRob = notRob;
    }
  }
  
  public ResultType helper(TreeNode root) {
    // return 2 nums, rob or notrob root.
    if (root == null) {
      return new ResultType(0, 0);
    }
    ResultType left = helper(root.left);
    ResultType right = helper(root.right);
    // root.rob = left.notrob + right.notrob + root.val.
    int rob = root.val + left.notRob + right.notRob;
    // root.notrob = Max OF: left.rob + right.rob, Or left.notrob + right.notrob,
    // Or left.rob + right.notrob, Or left.notrob + right.rob;
    int notRob = Math.max(left.rob, left.notRob) + Math.max(right.rob, right.notRob);
    return new ResultType(rob, notRob);
  }

}