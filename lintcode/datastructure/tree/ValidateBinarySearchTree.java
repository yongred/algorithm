/**
95. Validate Binary Search Tree
Given a binary tree, determine if it is a valid binary search tree (BST).

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
A single node tree is a BST

Solution 1: inorder Iterative using Stack

How to arrive:
* BST going through inorder traverse, is smallest -> largest.
* Key: prev Val < cur val, (== is false); Also, empty or null tree is true.
* Use a Stack to traverse inorder. Goes to leftmost as starting point, then push until finding the next right node. 
* Key: **Keep a prevNode to compare to curNode**. if (prevNode == null) it's the first Node, no need to compare.
* Time: O(n);
* Space: O(h) worst: O(n);


Solution 2: inorder recursive

How to arrive:
* same thing with iterative using stack. 
* Different: keep a member integer lastVal to compare to curVal. 
* Key: **Don't compare the first Node** if you init lastVal = MIN_VAL; the 1st Node.val could also be MIN_VAL;  (prev Val == cur val) return false; Not correct.
	* So we need a boolean to byPass firstNode. 
	* How? Leftmost would reach first. So on Leftmost, byPass firstNode from comparing, and set firstNode = false;
* Time: O(n);
* Space: O(h) worst: O(n);


Solution 3: divide and conquer
	2
 / \
1   4
		 / \
		3   5
		
How to arrive:
* Notice the root parent is the max boundary of left branch, min boundary of right branch.
* in this case: 2 is the min boundary of 3->5; 2 is also the max boundary of 1.  2 is the min boundary of 3, 4 is the max boundary of 3; (2 < 3 < 4);
* That means we can pass down the cur val as a Min or Max for comparason for children.
* if curNode <= min || curNode >= max, then it is not a BST.
* **Key**: What is the initial boundary? Integer.MIN_VALUE & MAX_VALUE?
	* No, if the node.val is Integer, we should use greater bits, **Long**, b/c one of the node may have Integer.MIN_VALUE or MAX_VALUE;
* Basically: MIN < left < Parent, Parent > right > MAX;
* Time: O(n);
* Space: O(h) worst: O(n);
*/
import treepackage.*;
import java.util.*;
import java.io.*;

public class ValidateBinarySearchTree {
  /**
   * @param root: The root of binary tree.
   * @return: True if the binary tree is BST, or false
   * Solution 1: iterative inorder using Stack
   */
  public boolean isValidBST(TreeNode root) {
    if (root == null) {
      return true;
    }
    boolean res = true;
    Stack<TreeNode> stack = new Stack<>();
    
    while (root != null) {
      stack.push(root);
      root = root.left;
    }
    TreeNode lastNode = null;
    while (!stack.isEmpty()) {
      TreeNode curNode = stack.pop();
      if (lastNode != null) {
        if (curNode.val <= lastNode.val) {
          res = false;
          break;
        }
      }
      lastNode = curNode;
      if (curNode.right != null) {
        TreeNode findNext = curNode.right;
        while (findNext != null) {
          stack.push(findNext);
          findNext = findNext.left;
        }
      }
    }
    
    return res;
  }


  
  /**
   * @param root: The root of binary tree.
   * @return: True if the binary tree is BST, or false
   * Solution 2: recursive inorder.
   */

  private int lastVal = Integer.MIN_VALUE;
  private boolean firstNode = true;

  public boolean isValidBST2(TreeNode root) {
    // null tree is BST
    if (root == null) {
      return true;
    }
    // inorder, left first
    if (!isValidBST2(root.left)) {
      return false;
    }
    // don't compare firstNode, as firstNode should be the min val.
    // ex 1st node.val == Integer.MIN_VALUE, cur.val > lastVal becomes false
    if (!firstNode && root.val <= lastVal) {
      return false;
    }
    // leftmost will reach here first. Next time, it's not firstNode
    firstNode = false;
    lastVal = root.val;
    // right traverse
    if (!isValidBST2(root.right)) {
      return false;
    }
    return true;
  }

  /**
   * @param root: The root of binary tree.
   * @return: True if the binary tree is BST, or false
   */
  public boolean isValidBST3(TreeNode root) {
    return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);
  }
  
  public boolean helper(TreeNode root, long min, long max) {
    if (root == null) {
      return true;
    }
    // left & right limit of the tree
    if (root.val <= min || root.val >= max) {
      return false;
    }
    
    boolean leftCheck = helper(root.left, min, root.val);
    boolean rightCheck = helper(root.right, root.val, max);
    return leftCheck && rightCheck;
  }
}