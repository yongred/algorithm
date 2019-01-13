/**
298. Binary Tree Longest Consecutive Sequence
Given a binary tree, find the length of the longest consecutive sequence path.

The path refers to any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The longest consecutive path need to be from parent to child (cannot be the reverse).

Example
For example,

   1
    \
     3
    / \
   2   4
        \
         5
Longest consecutive sequence path is 3-4-5, so return 3.

   2
    \
     3
    / 
   2    
  / 
 1
Longest consecutive sequence path is 2-3,not3-2-1, so return 2.
*/

/**
Solution: DFS, preorder. Maintain a len of path.
How to Arrive:
* Question is confusing, here consecutive means, increase by 1 each child node. So basically finding longest path like 1,2,3,4..;
* This is a typical DFS type of problem.
* We keep a len of consecutive path so far as we recursively go down the level.
* We check if curLen > longest. Update if it is.
* Then there is only 2 path to go down, Longest is either on left or right.
* We have to check is curLen can continue on left or/and right.
* If left or right value is curRoot.val + 1 then it is consecutive, pass down the len + 1. Else, pass down len = 1, for new starting path.
* Check left or right is null first.
* Time: O(n);
* Space: O(n);
*/

/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */

public class BinaryTreeLongestConsecutiveSequence {
  
  private int longest = 0;

  /**
   * @param root: the root of binary tree
   * @return: the length of the longest consecutive sequence path
   */
  public int longestConsecutive(TreeNode root) {
    if (root == null) {
      return 0;
    }
    // preorder, go left and right, check if child = parent + 1.
    helper(root, 1);
    return longest;
  }
  
  public void helper(TreeNode root, int len) {
    if (root == null) {
      return;
    }
    // if cur path is longer than longest.
    longest = Math.max(longest, len);
    // max is either on left or right
    if (root.left != null) {
      // check consecutive, increase by 1.
      if (root.val + 1 == root.left.val) {
        helper(root.left, len + 1);
      } else {
        helper(root.left, 1);
      }
    }
    // check right
    if (root.right != null) {
      if (root.val + 1 == root.right.val) {
        helper(root.right, len + 1);
      } else {
        helper(root.right, 1);
      }
    }
  }
  
  
}