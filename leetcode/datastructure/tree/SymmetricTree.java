/**
101. Symmetric Tree
Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).

For example, this binary tree [1,2,2,3,4,4,3] is symmetric:

    1
   / \
  2   2
 / \ / \
3  4 4  3
But the following [1,2,2,null,3,null,3] is not:
    1
   / \
  2   2
   \   \
   3    3
Note:
Bonus points if you could solve it both recursively and iteratively.
*/

/**
Solution 1: traverse leftBranch inorder, traverse rightBranch right, root, left.
How to Arrive:
* Looking at the tree, I notice that left branch and right branch are reversed.
* Meaning we can traverse them in reverse order and compare. traverse leftBranch inorder, traverse rightBranch right, root, left.
* If there is difference return false.
* Time: O(n);
* Space: O(h); O(n) in worst; 
*/

import treepackage.*;
import java.util.*;
import java.io.*;

class SymmetricTree {
	/**
	 * Solution: iterative
	 */
  public boolean isSymmetric(TreeNode root) {
    if (root == null) {
      return true;
    }
    Stack<TreeNode> inorder = new Stack<>();
    Stack<TreeNode> postorder = new Stack<>();
    // inorder left branch.
    TreeNode cur = root.left;
    while (cur != null) {
      inorder.push(cur);
      cur = cur.left;
    }
    // postorder right branch.
    cur = root.right;
    while (cur != null) {
      postorder.push(cur);
      cur = cur.right;
    }
    
    while (!inorder.isEmpty() && !postorder.isEmpty()) {
      TreeNode leftmost = inorder.pop();
      TreeNode rightmost = postorder.pop();
      if (leftmost.val != rightmost.val || inorder.size() != postorder.size()) {
        return false;
      }
      // traverse inorder.
      if (leftmost.right != null) {
        TreeNode next = leftmost.right;
        while (next != null) {
          inorder.push(next);
          next = next.left;
        }
      }
      // traverse postorder.
      if (rightmost.left != null) {
        TreeNode next = rightmost.left;
        while (next != null) {
          postorder.push(next);
          next = next.right;
        }
      }
    }
    
    if (inorder.size() != postorder.size()) {
      return false;
    }
    return true;
  }

  /**
   * Solution: recursive
   */
  public boolean isSymmetric2(TreeNode root) {
    if (root == null) {
      return true;
    }
    return traverseOpposite(root.left, root.right);
  }
  
  public boolean traverseOpposite(TreeNode inorder, TreeNode opposite) {
    if (inorder == null && opposite == null) {
      return true;
    }
    // 1 of them is null
    if (inorder == null || opposite == null) {
      return false;
    }
    // check outter nodes.
    boolean outter = traverseOpposite(inorder.left, opposite.right);
    if (!outter) {
      return false;
    }
    // root check
    if (inorder.val != opposite.val) {
      return false;
    }
    // check inner nodes
    return traverseOpposite(inorder.right, opposite.left);
  }
}