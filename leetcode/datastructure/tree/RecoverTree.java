/**
99. Recover Tree
Two elements of a binary search tree (BST) are swapped by mistake.

Recover the tree without changing its structure.

Example 1:

Input: [1,3,null,null,2]

   1
  /
 3
  \
   2

Output: [3,1,null,null,2]

   3
  /
 1
  \
   2
Example 2:

Input: [3,1,4,null,null,2]

  3
 / \
1   4
   /
  2

Output: [2,1,4,null,null,3]

  2
 / \
1   4
   /
  3
Follow up:

A solution using O(n) space is pretty straight forward.
Could you devise a constant space solution?
*/

/**
Solution: Inorder, Find prev > cur
How to Arrive:
* BST when traverse inorder is sorted in ASC.
* So as we traverse inorder, we can find 2 nums not in order.
* **Key**: Finding node1 is simple, just Find prevNode > curNode. The prevNode is Node1.
* **Key**: Finding node2, After finding node1, *node2 is the smallest on the right side of Node1*.
	* There are 2 cases. If node1 adjacent node2, then there will only be 1 > happens.
	* Ex: 1,4, 2, 5, 6;  4 > 2; That's it.
	* If node1 Not adj node2, then there will be 2 > happens.
	* Ex: 1, 7, 3, 5, 6, 2, 8;  7 > 3, 6 > 2;  7 and 2 swap;
* So We need a Prev to compare to cur. Set node1 = prev when find prev > cur.
* And set node2 = cur, assuming Adjacent. Then as we traverse more, when prev > cur again, update node2 = cur.
* Time: O(n);
* Space: O(h);
*/

import treepackage.*;
import java.util.*;
import java.io.*;

class RecoverTree {

  public void recoverTree(TreeNode root) {
    if (root == null) {
      return;
    }
    Stack<TreeNode> stack = new Stack<>();
    TreeNode node1 = null;
    TreeNode node2 = null;
    TreeNode cur = root;
    while (cur != null) {
      stack.push(cur);
      cur = cur.left;
    }
    
    TreeNode prev = null;
    while (!stack.isEmpty()) {
      TreeNode leftmost = stack.pop();
      if (prev != null) {
        if (prev.val >= leftmost.val) {
          if (node1 == null) {
            node1 = prev;
            node2 = leftmost;
          } else {
            node2 = leftmost;
            break;
          }
        }
      }
      
      if (leftmost.right != null) {
        TreeNode next = leftmost.right;
        while (next != null) {
          stack.push(next);
          next = next.left;
        }
      }
      prev = leftmost;
    }
    
    swap(node1, node2);
  }
  
  public void swap(TreeNode node1, TreeNode node2) {
    if (node1 == null || node2 == null) {
      return;
    }
    int temp = node1.val;
    node1.val = node2.val;
    node2.val = temp;
  }

  /**
   * Solution2: Recursive.
   */
  private TreeNode node1;
  private TreeNode node2;
  private TreeNode prev;
  
  public void recoverTree2(TreeNode root) {
    helper(root);
    swap(this.node1, this.node2);
  }
    
  public void helper(TreeNode root) {
    if (root == null) {
      return;
    }
    // inoder, left first
    helper(root.left);
    // root 2nd    
    if (prev != null) {
      if (prev.val >= root.val) {
        if (this.node1 == null) {
          this.node1 = prev;
          this.node2 = root;
        } else {
          // done finding 2 nodes to swap;
          this.node2 = root;
        }
      }
    }
    this.prev = root;
    // right last. Prev is the rootNode.
    helper(root.right);
  }
  
}