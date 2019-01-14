/**
104. Maximum Depth of Binary Tree
Given a binary tree, find its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

Note: A leaf is a node with no children.

Example:

Given binary tree [3,9,20,null,null,15,7],

    3
   / \
  9  20
    /  \
   15   7
return its depth = 3.
*/

/**
Solution: DFS, preorder. Update maxPath when reach to a leaf node.
How to Arrive:
**Key**: Remember, only reached to a leafNode you can compare the PathLen to maxLen.
* So we travel/DFS to every leafNode, see which one is the maxLen.
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
class MaximumDepthBinaryTree {

  /**
  Solution: DFS, preorder. Update maxPath when reach to a leaf node.
	* Time: O(n)
	* Space: O(n)
	*/
  private int maxPath = Integer.MIN_VALUE;
  
  public int maxDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    // if rootNode itself is leaf, it's 1.
    helper(root, 1);
    return maxPath;
  }

  public void helper(TreeNode root, int pathLen) {
    if (root == null) {
      return;
    }
    // leaf
    if (root.left == null && root.right == null) {
      maxPath = Math.max(maxPath, pathLen);
      return;
    }
    // max on left or right.
    helper(root.left, pathLen + 1);
    helper(root.right, pathLen + 1);
  }
  
}