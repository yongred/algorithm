/**
97. Maximum Depth of Binary Tree
Given a binary tree, find its maximum depth.
find height.
Example
  1
 / \ 
2   3
   / \
  4   5  
The maximum depth is 3.
*/

/**
Solution: recursive.
How to Arrive:
* The deepest branch is either on the left or the right. So we finding max of the two.
* Key: **Cur level is 1 + lastMax**; So when we find the last max, return last max+1, this is the current depth.
* Base, when null, return 0;
* Time: O(n);
* Space: O(h);

Solution: iterative, using queue to level traverse.
How to arrive:
* We need to go through each level, and count+1.
* So we can loop through nodes in the level, add their children for the next level, pop out the cur ones.
* Using Queue for FIFO, we can add this level first. Increment level, add their children and pop them out.
* Key: we need to know when this level is finished. **Count the queue size** to get current level size.
* Time: O(n);
* Space: O(n); if it's all in 1 level.
*/

import treepackage.*;
import java.util.*;
import java.io.*;

public class MaxDepthBinaryTree {
	/**
   * @param root: The root of binary tree.
   * @return: An integer
   */
  public int maxDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    
    int left = maxDepth(root.left);
    int right = maxDepth(root.right);
    
    return Math.max(left, right) + 1;
  }
  
  /**
   * @param root: The root of binary tree.
   * @return: An integer
   */
  public int maxDepth2(TreeNode root) {
    if (root == null) {
      return 0;
    }
    Deque<TreeNode> queue = new ArrayDeque<>();
    queue.add(root);
    int level = 0;
    
    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      
      for (int i = 0; i < levelSize; i++) {
        TreeNode curNode = queue.poll();
        if (curNode == null) {
          continue;
        }
        if (curNode.left != null) {
          queue.add(curNode.left);
        }
        if (curNode.right != null) {
          queue.add(curNode.right);
        }
      }
      level++;
    }
    return level;
  }
}