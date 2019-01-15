/**
235. Lowest Common Ancestor of a Binary Search Tree
Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Given binary search tree:  root = [6,2,8,0,4,7,9,null,null,3,5]


Example 1:

Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
Explanation: The LCA of nodes 2 and 8 is 6.
Example 2:

Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
Output: 2
Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
 

Note:

All of the nodes' values will be unique.
p and q are different and both values will exist in the BST.
*/

/**
Solution: like binarySearch, check rootVal > both, < both, or in between.
How to  Arrive:
* P and Q is either both on left or right of Root, or they are on diff side of Root.
* If they are on diff side of Root, that means Root is the LCA.
* So we compare RootVal to p and q vals,  rootVal > both means p, q on the left of root.
* rootVal < both, means p, q on the right of root.
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
class LowestCommonAncestorBST {

  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) {
      return null;
    }
    // if rootVal > both, go left.
    if (root.val > p.val && root.val > q.val) {
      return lowestCommonAncestor(root.left, p, q);
    }
    // if rootVal < both, go right.
    if (root.val < p.val && root.val < q.val) {
      return lowestCommonAncestor(root.right, p, q);
    }
    // if root == to one of them , or q and p on opposite side of root.
    // then root is the lca
    return root;
  }
}