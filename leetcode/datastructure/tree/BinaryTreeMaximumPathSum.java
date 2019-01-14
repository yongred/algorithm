/**
124. Binary Tree Maximum Path Sum
Given a non-empty binary tree, find the maximum path sum.

For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The path must contain at least one node and does not need to go through the root.

Example 1:

Input: [1,2,3]

       1
      / \
     2   3

Output: 6
Example 2:

Input: [-10,9,20,null,null,15,7]

   -10
   / \
  9  20
    /  \
   15   7

Output: 42
*/

/**
Solution: postorder, dfs, clear negative pathSum.
Ex:
               -2
							/   \
						 4     5
							   /   \
								6      7
							/  \    / \
						-1   3  -4  -5
	
Max: 4, 9, 21
pathLevel: 
-2: L (4) => 4; R (5) => 14; (4 + 14 + -2 = 16) < max21, no update. ANS = 21; (3,6,5,7);
(4): update Max=4, return 4;
(5): L (6) => 9, R (7) => 7; (9 + 7 + 5 = 21) > max9; update; ret maxPath: 9 > 7, so 9 + 5 = 14 ret;
(6): L (-1) => -1 => 0, R(3) => 3; (6 + 3 = 9) > max4; update; ret 9.
(-1): no update, return -1.
(3): no update, ret 3
(7): L(-4) => -4 => 0; R(-5) => 0; 7 < max9, no update; ret 7;

How to Arrive:
* Understand the Question: 
	* it is asking for a Path from any node to any node.
	* Path visits each node only once. Cannot go back to parent.
* This question is similar to max continuous subarray sum. Which we don't want to include negative carryOver pathSum. It makes curPath smaller.
* There are 2 things:
	* We want to choose a path to return: either left to root, or right to right. Not both.
	* And we want to update maxPath, by comparing to (leftPath +  curval + rightPath).
		* If leftPath or rightPath is negative, we exclude it, set it to 0.
		* Ex: left = -9, right = 3, cur = 2, then 0 + 2 + 3 = 5; exclude left => 0;
	**Key**: we return larger pathSum, either from left or right + root. And we update maxPath along the way with left + cur + right. **They are 2 diff things**;
* Time: O(n);
* Space: O(n);

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
class BinaryTreeMaximumPathSum {

  private int maxPath = Integer.MIN_VALUE;
  
  public int maxPathSum(TreeNode root) {
    if (root == null) {
      return 0;
    }
    helper(root);
    return maxPath;
  }
  
  public int helper(TreeNode root) {
    // inorder traverse. Return the maxPath.
    if (root == null) {
      return 0;
    }
    // get max pathSum from left and right subtrees.
    // if negative, don't include in curPath.
    int left = Math.max(0, helper(root.left));
    int right = Math.max(0, helper(root.right));
    // update max. compare to path include curRootVal.
    maxPath = Math.max(maxPath, root.val + left + right);
    // return the Max continuous Path, either left or right + curRoot, not both.
    return Math.max(left, right) + root.val;
  }
  
}