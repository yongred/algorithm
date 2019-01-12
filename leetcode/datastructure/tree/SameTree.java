/**
Given two binary trees, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical and the nodes have the same value.

Example 1:

Input:     1         1
          / \       / \
         2   3     2   3

        [1,2,3],   [1,2,3]

Output: true
Example 2:

Input:     1         1
          /           \
         2             2

        [1,2],     [1,null,2]

Output: false
Example 3:

Input:     1         1
          / \       / \
         2   1     1   2

        [1,2,1],   [1,1,2]

Output: false
*/

/**
Solution: preorder traverse and compare.
How to Arrive:
* Just need to compare each node in the same traverse order.
* Time: O(n)
* Space: O(lgN) or worst O(n)
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
class SameTree {
  
  /**
   * Solution: preorder traverse and compare.
   * Time: O(n)
   * Space: O(lgN) or worst O(n)
   */
  public boolean isSameTree(TreeNode p, TreeNode q) {
    // preorder traversal, compare
    if (p == null && q == null) {
      return true;
    }
    if (p == null || q == null) {
      return false;
    }
    // compare root, left, right
    boolean sameVal = (p.val == q.val);
    if (!sameVal) {
      return false;
    }
    // left
    boolean leftCompare = isSameTree(p.left, q.left);
    if (!leftCompare) {
      return false;
    }
    // right
    return isSameTree(p.right, q.right);
  }  
  
  
}