/**
111. Minimum Depth of Binary Tree
Given a binary tree, find its minimum depth.

The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.

Note: A leaf is a node with no children.

Example:

Given binary tree [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
return its minimum depth = 2.
*/

/**
Solution: DFS, preorder. Update minPath when reach to a leaf node.
How to Arrive:
**Key**: Remember, only reached to a leafNode you can compare the PathLen to minLen.
* So we travel/DFS to every leafNode, see which one is the minLen.
* 
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
class MinimumDepthBinaryTree {

  /**
   * Solution: DFS, preorder. Update minPath when reach to a leaf node.
   * Time: O(n)
   * Space: O(n)
   */
  private int minPath = Integer.MAX_VALUE;
  
  public int minDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    // if root is leaf it's 1 as len.
    helper(root, 1);
    return minPath;
  }
  
  public void helper(TreeNode root, int pathlen) {
    // DFS, until reached leaf node.
    if (root == null) {
      return;
    }
    // leaf node. Only when reached to a leaf node this path count.
    // Cannot calc min if node have any child.
    if (root.left == null && root.right == null) {
      // reached leaf
      minPath = Math.min(minPath, pathlen);
      return;
    }
    // not a leaf, check left and right for shortest dist leaf.
    helper(root.left, pathlen + 1);
    helper(root.right, pathlen + 1);
  }
  
}