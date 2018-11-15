/**
Minimum Depth of Binary Tree
Given a binary tree, find its minimum depth.

The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.

Example
Given a binary tree as follow:

  1
 / \ 
2   3
   / \
  4   5  
The minimum depth is 2.
*/

/**
Solution: DFS
How to arrive:
* Key: We want to find the shortest  **leaf node**, any node with children doesn't count.
* Leaf means no child. So, when no left and right. It is a leaf. just return 1.
* If it has children, the min height is either on the left or right branch.
* So we just need to go to the left, go to the right, and compare the 2.
* +1 to the MIN(left, right);
* Go down until we Find the leaf node.
* Time: O(n)
* Space: O(h);

Solution 2: Iterative using Queue, Level traverse
How to arrive:
* **Key**: we just need to find the **First Leaf Node**. That means we don't need to go all the way.
* Go down level by level, just soon as the first leaf is found, we return.
* Can do this by level traversal.
* Using a Queue w/ FIFO. We can go in level order.
* To know When to stop at the end of the level. We count the cur size of queue first (level size). Then loop according to it.
* When we find a node with no left and right, return the cur level.
* Time: O(n);
* Space: O(n);
*/

import treepackage.*;
import java.util.*;
import java.io.*;

public class MinDepthBinaryTree {
  /**
   * @param root: The root of binary tree
   * @return: An integer
   */
  public int minDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    
    int left = minDepth(root.left);
    int right = minDepth(root.right);
    if (left == 0) {
      return right + 1;
    }
    if (right == 0) {
      return left + 1;
    }
    return Math.min(left, right) + 1;
  }

  /**
   * @param root: The root of binary tree
   * @return: An integer
   */
  public int minDepth2(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int level = 0;
    Deque<TreeNode> queue = new ArrayDeque<>();
    queue.add(root);
    
    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      level++;
      
      for (int i = 0; i < levelSize; i++) {
        TreeNode curNode = queue.poll();
        if (curNode.left == null && curNode.right == null) {
          return level;
        }
        if (curNode.left != null) {
          queue.add(curNode.left);
        }
        if (curNode.right != null) {
          queue.add(curNode.right);
        }
      }
    }
    
    return level;
  }
}