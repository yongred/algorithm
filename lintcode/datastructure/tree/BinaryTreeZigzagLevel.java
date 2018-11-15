/**
71. Binary Tree Zigzag Level Order Traversal
Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).

Example
Given binary tree {3,9,20,#,#,15,7},

    3
   / \
  9  20
    /  \
   15   7
 

return its zigzag level order traversal as:

[
  [3],
  [20,9],
  [15,7]
]
*/

import treepackage.*;
import java.util.*;
import java.io.*;

public class BinaryTreeZigzagLevel {
  /**
   * @param root: A Tree
   * @return: A list of lists of integer include the zigzag level order traversal of its nodes' values.
   * Solution: 2 Stacks
   * It's also level traverse, so we need to keep only cur level nodes in a dataStructure.
	* Notice: lv 1,3,5 ->; lv 2,4,6 <-; So we need a Boolean to determine add left or right node first.
	* Problem: if we use Queue, FIFO, lv 2: [3,2] So we will end up 3 poping 5 first, then 2 pop 4; [5,4] instead of [4,5]
	* To solve this problem we can use **2 Stacks**, when current Stack 3 pop 5, 2 pop 4; Push it into another Stack.
		*  Next Stack(5,4) LIFO, so 4 pop first, then 5; [4,5]
	* So we can have curLevel Stack and nextLevel Stack. CurLevel pop into NextLevel stack.
	* When curLevel stack is Empty, goes to nextLevel Stack.
	* Time: O(n); Space: O(n);
   */
  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    Stack<TreeNode> curLevel = new Stack<>();
    Stack<TreeNode> nextLevel = new Stack<>();
    boolean normalOrder = true;

    curLevel.push(root);
    while (!curLevel.empty()) {
      List<Integer> levelList = new ArrayList<>();
      while (!curLevel.empty()) {
        TreeNode curNode = curLevel.pop();
        levelList.add(curNode.val);
        // determine dir, LIFO
        if (normalOrder) {
          // next level is reverse, right to left, wants right top
          // left bottom.
          if (curNode.left != null) {
            nextLevel.push(curNode.left);
          }
          if (curNode.right != null) {
            nextLevel.push(curNode.right);
          }
        } else {
          // next level is normal, left to right, wants left top
          // right bottom
          if (curNode.right != null) {
            nextLevel.push(curNode.right);
          }
          if (curNode.left != null) {
            nextLevel.push(curNode.left);
          }
        }
      }
      //prepare for next level
      Stack<TreeNode> temp = curLevel;
      curLevel = nextLevel;
      nextLevel = temp;
      normalOrder = !normalOrder;
      res.add(levelList);
    }
    return res;
  }
}