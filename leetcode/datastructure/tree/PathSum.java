/**
112. Path Sum
Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.

Note: A leaf is a node with no children.

Example:

Given the below binary tree and sum = 22,

      5
     / \
    4   8
   /   / \
  11  13  4
 /  \      \
7    2      1
return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
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
class PathSum {

	/**
	 * Solution: preorder traverse, keep a pathsum, when at a leaf check pathSum == sum;
	 * Time: O(n)
	 * Space: O(n)
	 */
  public boolean hasPathSum(TreeNode root, int sum) {
    return helper(root, 0, sum);
  }
  
  public boolean helper(TreeNode root, int pathSum, int sum) {
    if (root == null) {
      return false;
    }
    // leaf node
    if (root.left == null && root.right == null) {
      return (pathSum + root.val == sum) ? true : false;
    }
    // preorder, root, left right.
    boolean left = helper(root.left, pathSum + root.val, sum);
    // if found sum in left.
    if (left) {
      return true;
    }
    // right check
    return helper(root.right, pathSum + root.val, sum);
  }
}