/**
236. Lowest Common Ancestor of a Binary Tree
Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]

Example 1:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.
Example 2:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
 

Note:

All of the nodes' values will be unique.
p and q are different and both values will exist in the binary tree.
*/

/**
Solution: postorder or preorder traverse. Count found nodes, if == 2, lca is root.
How to Arrive:
* P and Q are either both on left or right of root, or root is the LCA.
* We keep a count, Found.
* If root.val == q or p; Found++;
* Then we check left, (use boolean as return), if func(root.left) is true, means it found p or q or both.
* We use a member LCA var as final res.
* if left found both, just return true, since we already found LCA.
* if left is true, but LCA = null, means left have 1 found p or q. We increment Found++.
* if (Found == 2) LCA = root. return true;
* If not found both yet, we go to right, func(root.right); Check if right have p or q or both.
* if LCA != null, means LCA on the right side, return true;
* if LCA == null, but Right is true, then we found 1 on right.
* if (Found == 2) LCA = root. return true;
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
class LowestCommonAncestorBinaryTree {

	/**
	Solution: postorder or preorder traverse. Count found nodes, if == 2, lca is root.
	* Time: O(n);
	* Space: O(n);
	*/
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    helper(root, p, q);
    return lca;
  }
  
  private TreeNode lca = null;
  
  public boolean helper(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) {
      return false;
    }
    int found = 0;
    if (root.val == p.val || root.val == q.val) {
      found++;
    }
    // go left and right to check for p or q.
    boolean left = helper(root.left, p, q);
    // if found lca on left branch, we can stop.
    if (lca != null) {
      return true;
    }
    // check left found one p or q.
    if (left) {
      found++;
      if (found == 2) {
        lca = root;
        return true;
      }
    }
    // right check
    boolean right = helper(root.right, p, q);
    // if found lca on right, we can stop.
    if (lca != null) {
      return true;
    }
    // if 1 of them p or q found on right.
    if (right) {
      found++;
      if (found == 2) {
        lca = root;
        return true;
      }
    }
    // return true if found p or q.
    return (found > 0) ? true : false;
  }
}