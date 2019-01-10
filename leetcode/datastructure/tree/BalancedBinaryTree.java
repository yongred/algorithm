/**
110. Balanced Binary Tree
Given a binary tree, determine if it is height-balanced.

For this problem, a height-balanced binary tree is defined as:

a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

Example 1:

Given the following tree [3,9,20,null,null,15,7]:

    3
   / \
  9  20
    /  \
   15   7
Return true.

Example 2:

Given the following tree [1,2,2,3,3,null,null,4,4]:

       1
      / \
     2   2
    / \
   3   3
  / \
 4   4
Return false.
*/

/**
Solution: PostOrder traverse to find height for left and right, compare diff <= 1. Create a ResultType for height and bool balanced.
How to Arrive:
**Balanced Binary Tree**: a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
* In other words, no 2 branches have height diff > 1;
* Also null or empty tree is balanced.
* So we basically wants to get height of left and height of right to compare if <= 1 diff.
* Also we want to check if left and right branch is balanced itself.
* **Key**: we need 2 things to be returned, Height and Balanced. Create a ResultType for these 2 vals.
* If left or right branch is Not balanced, we can stop and return false in ResultType.
* If both balanced, we compare their height. Return false if diff > 1. Else we return (deeperHeight + 1, true balanced);
*  
* Time: O(n)
* Space: O(lgN)
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
class BalancedBinaryTree {
  
  /**
   * Solution: PostOrder traverse to find height for left and right, compare diff <= 1. 
   	* create a ResultType for height and bool balanced.
   * Time: O(n)
   * Space: O(lgN)
   */
  public boolean isBalanced(TreeNode root) {
    // check height diff. postorder.
    if (root == null) {
      return true;
    }
    // postorder traverse to get height and compare.
    ResultType result = helper(root);
    return result.balanced;
  }
  
  class ResultType {
    int height;
    boolean balanced;
    
    ResultType(int height, boolean balanced) {
      this.height = height;
      this.balanced = balanced;
    }
  }
  
  public ResultType helper(TreeNode root) {
    // base case, null is 0 height, balanced.
    if (root == null) {
      return new ResultType(0, true);
    }
    // postorder left, right heights
    ResultType left = helper(root.left);
    if (!left.balanced) {
      return new ResultType(-1, false);
    }
    ResultType right = helper(root.right);
    if (!right.balanced) {
      return new ResultType(-1, false);
    }
    // both left, right balanced. Check height diff.
    if (Math.abs(left.height - right.height) > 1) {
      return new ResultType(-1, false);
    }
    int rootHeight = 1 + Math.max(left.height, right.height);
    return new ResultType(rootHeight, true);
  }
  
  
  
}