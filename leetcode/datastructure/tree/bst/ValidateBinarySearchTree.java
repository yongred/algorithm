/**
98. Validate Binary Search Tree
Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
Example 1:

Input:
    2
   / \
  1   3
Output: true
Example 2:

    5
   / \
  1   4
     / \
    3   6
Output: false
Explanation: The input is: [5,1,4,null,null,3,6]. The root node's value
             is 5 but its right child's value is 4.
*/

/**
Solution 1: Inorder traversal, keep a largestSoFar to compare right nodes.
How to Arrive:
* To know whether it is a valid BST, we can do a inorder traverse and make sure every left node is smaller than right nodes.
* To do that we need to keep a var Largest indicates the left largest node val so far.
* Use it to compare to next right node. And update it.
* We also need to boolean firstInit to indicates if the first node val (smallest) is initialized.
* The leftmost node is the smallest. Init that node as first val of Largest var.
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
class ValidateBinarySearchTree {

  /**
  Solution 1: Inorder traversal, keep a largestSoFar to compare right nodes.
  How to Arrive
  * Time: O(n);
  * Space: O(n);
  */
  public boolean isValidBST(TreeNode root) {
    // inorder traverse, find node < prev.
    return helper(root);
  }
  
  private int largest = Integer.MIN_VALUE;
  boolean firstInit = false;
  
  public boolean helper(TreeNode root) {
    // inorder traverse, maintain ASC order.
    if (root == null) {
      return true;
    }
    // find the leftmost node as starting point. If firstVal not assigned yet.
    if (!firstInit && root.left == null) {
      // first smallest val in left.
      largest = root.val;
      firstInit = true;
      // if it have right branch, check if it is BST.
      return helper(root.right);
    }
    // inorder. left root right
    boolean left = helper(root.left);
    // check left is BST and largest < root.val
    if (!left || largest >= root.val) {
      return false;
    }
    // update largest to rootVal.
    largest = root.val;
    // go right to check largest < all right nodes.
    return helper(root.right);
  }
  
  
}