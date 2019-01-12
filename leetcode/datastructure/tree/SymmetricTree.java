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
Solution 1: traverse leftBranch root, left, right; traverse rightBranch root, right, left.
Ex:
          1
       /     \
      2       2
     / \    /   \
    3   4   4    3
   / \  /    \  /  \
  5  6 7      7 6  5
How to Arrive:
* Looking at the tree, I notice that left branch and right branch are reversed.
* Meaning we can traverse them in reverse order and compare. Traverse leftBranch root, left, right; traverse rightBranch root, right, left.
* If there is difference return false.
**Key**: for iterative solution: be careful with the null cases.
  * !null & !null: add(n1.left, n2.right), (n1.left = null) XOR (n2.right = null): return false.
  * null & null: ignore, true, no need to add to stack.
  * **Use XOR to check if one of them is null**. Or add another condition to check (null & null).
* Time: O(n);
* Space: O(h); O(n) in worst;  
*/

import treepackage.*;
import java.util.*;
import java.io.*;

class SymmetricTree {

	/**
	 * Solution: iterative
   * Time: O(n);
   * Space: O(n);
	 */
  public boolean isSymmetric(TreeNode root) {
    // iterative, root left right <-> root right left
    if (root == null) {
      return true;
    }
    Stack<TreeNode> preorder = new Stack<>();
    Stack<TreeNode> opposite = new Stack<>();
    // go with root first, 
    preorder.push(root);
    opposite.push(root);
    while (!preorder.isEmpty() || !opposite.isEmpty()) {
      // compare nodes
      TreeNode node1 = preorder.pop();
      TreeNode node2 = opposite.pop();
      // compare their val
      if (node1.val != node2.val) {
        return false;
      }
      // rootVal same, compare n1.left -> n2.right, n1.right -> n2.left subtree
      // n1 R L stack flip => L R; n2 L R stack flip R L
      if (node1.left != null && node2.right != null) {
        // n1.left, n2.right are not nulls
        preorder.push(node1.left);
        opposite.push(node2.right);
      } else if (node1.right != null ^ node2.left != null) {
        // Use XOR here, or means null + null is also true, we don't want that.
        // if only one is node, the other is null. False.
        return false;
      }
      // if both are null, do nothing. No need to add to stack. nil == nil.
      if (node1.right != null && node2.left != null) {
        // if both n1.right and n2.left are nodes
        preorder.push(node1.right);
        opposite.push(node2.left);
      } else if (node1.right != null ^ node2.left != null) {
        // Use XOR here, or means null + null is also true, we don't want that.
        // if only one is node, the other is null. False.
        return false;
      }
    }
    return true;
  }

  /**
   * Solution: recursive
   * Time: O(n)
   * Space: O(n)
   */
  public boolean isSymmetric(TreeNode root) {
    if (root == null) {
      return true;
    }
    return helper(root.left, root.right);
  }
  
  public boolean helper(TreeNode n1, TreeNode n2) {
    // compare n1 root to n2 root, n1 left -> n2 right, n1 right -> n2 left.
    if (n1 == null && n2 == null) {
      return true;
    }
    // only one is null
    if (n1 == null || n2 == null) {
      return false;
    }
    // compare root
    boolean sameVal = (n1.val == n2.val);
    if (!sameVal) {
      return false;
    }
    // compare n1.left branch -> n2.right branch
    boolean leftRight = helper(n1.left, n2.right);
    if(!leftRight) {
      return false;
    }
    // compare n1.right branch -> n2.left branch
    return helper(n1.right, n2.left);
  }
}