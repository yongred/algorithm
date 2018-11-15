/**
86. Binary Search Tree Iterator
Design an iterator over a binary search tree with the following rules:

Elements are visited in ascending order (i.e. an in-order traversal)
next() and hasNext() queries run in O(1) time in average.
Example
in-order traversal by using iterator is [1, 3, 4, 6, 7, 10, 11, 12]

			10
		 /  \
		3    11
	 / \     \
	1		6     12
			/ \
		 4  7
Challenge
Extra memory usage O(h), h is the height of the tree.

Super Star: Extra memory usage O(1)

 * Example of iterate a tree:
 * BSTIterator iterator = new BSTIterator(root);
 * while (iterator.hasNext()) {
 *    TreeNode node = iterator.next();
 *    do something for node
 * } 

*/

/**
**What this is asking**: *Given a root, how do I do inorder traversal Call by Call (next())?*
**How I arrive solution**
* we need to get from  root (10) -> start (1);
	* How? root.left until the leftmost.
* We need to find the next right node, each time next() call. 
	* 2 posibilities: the next right node is either **its parent** or **the leftmost node on the right branch of its parent**;
	* **Key**: So we need the parent for the curNode. But curNode cannot go point to its parent. So we need to *store its parent when we traverse down*.
	* How do we visit parent first, but pop out child first?
		* 1. use an array to store all nodes in Inorder position. Use a curIndex to iterate each time next() is called.
		* 2. Better optimized way in space is using a **Stack**. LIFO.
	* **Key** when we traverse to the leftmost, the parents is pushed into stack as well. Last one pushed is the leftmost. So it will pop first.
	* Then the next Node is either the parent, which is on the stack for next pop, or if the parent have a right branch, then it's the leftmost node on that branch.
	* stack should push till the next node is find, and STOP. b/c it is on top for next pop.
	* As for hasNext(). When the stack is not empty, it has next.
	* Time: next() is O(1) on average, looking for sub branch node. And hasNext() is O(1);
	* Space: average O(h); since we only pop in until the next node is found. Worse case h = n;
*/

import treepackage.*;
import java.util.*;
import java.io.*;

public class BSTIterator {
  public Stack<TreeNode> nodeStack;

  /*
  * @param root: The root of binary tree.
  */
  public BSTIterator(TreeNode root) {
    nodeStack = new Stack<>();
    // Find the 1st node, leftmost.
    while (root != null) {
      nodeStack.push(root);
      root = root.left;
    }
  }

  /*
   * @return: True if there has next node, or false
   */
  public boolean hasNext() {
    return !nodeStack.isEmpty();
  }

  /*
   * @return: return next node
   */
  public TreeNode next() {
    TreeNode curNode = nodeStack.pop();
    // if right is null, then the parent is next, which we already push in stack.
    if (curNode.right != null) {
      TreeNode findNext = curNode.right;
      // if this node have left branch, find the leftmost(smallest) node
      while (findNext != null) {
        nodeStack.push(findNext);
        findNext = findNext.left;
      }
    } 
    return curNode;
  }
  
  
}