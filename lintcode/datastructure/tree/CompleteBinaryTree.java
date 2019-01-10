/**
467. Complete Binary Tree

Check a binary tree is completed or not. 
A complete binary tree is a binary tree that every level is completed filled except the deepest level. 
In the deepest level, all nodes must be as left as possible. See more definition
Example
    1
   / \
  2   3
 /
4
is a complete binary.
    1
   / \
  2   3
   \
    4
is not a complete binary.
Challenge
Do it in O(n) time
*/

/**
Solution: Check for nodes after a nullNode.
			1
		 / \
		2   3
	 / \  /
	4   5 6
 /
7

False, any case if node found after null.

How to Arrive:
* A complete binary tree, there should not be any nodes after a nullNode.
* So we just do a level traversal, set a flag on nullNode. 
* If there is node after a nullNode return false;
* We cannot add null elm to queue, so check it's children left and right == null;
* != null check for nullFlag, and add to queue if !nullFlag.
* == null turn on nullFlag.
* Time: O(n)
* Space: O(n)
*/

import java.io.*;
import java.util.*;

public class CompleteBinaryTree {
	
	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
			left = right = null;
		}
	}

	public boolean isComplete(TreeNode root) {
		// null is complete.
		if (root == null) {
			return true;
		}
		boolean nullFlag = false;
		Deque<TreeNode> queue = new ArrayDeque<>();
		queue.addLast(root);
		// level traverse, check for null
		while (!queue.isEmpty()) {
			// cur level size.
			int levelSize = queue.size();
			for (int i = 0; i < levelSize; i++) {
				TreeNode curNode = queue.pollFirst();
				// add its children
				if (curNode.left != null) {
					// if this node come after a nullNode.
					if (nullFlag) {
						return false;
					}
					queue.addLast(curNode.left);
				} else {
					nullFlag = true;
				}
				// right
				if (curNode.right != null) {
					// if this node come after a nullNode.
					if (nullFlag) {
						return false;
					}
					queue.addLast(curNode.right);
				} else {
					nullFlag = true;
				}
			}
		}
		// no nodes after a null.
		return true;
	}

	public static void main(String[] args) {
		CompleteBinaryTree obj = new CompleteBinaryTree();
		CompleteBinaryTree.TreeNode n1 = obj.new TreeNode(1);
		CompleteBinaryTree.TreeNode n2 = obj.new TreeNode(2);
		CompleteBinaryTree.TreeNode n3 = obj.new TreeNode(3);
		CompleteBinaryTree.TreeNode n4 = obj.new TreeNode(4);
		CompleteBinaryTree.TreeNode n5 = obj.new TreeNode(5);
		CompleteBinaryTree.TreeNode n6 = obj.new TreeNode(6);
		CompleteBinaryTree.TreeNode n7 = obj.new TreeNode(7);
		n1.left = n2;
		n1.right = n3;
		n2.left = n4;
		n2.right = n5;
		// n3.left = n6;
		n3.right = n7;
		// n4.left = n7;
		if (obj.isComplete(n1)) {
			System.out.println("complete");
		} else {
			System.out.println("not complete");
		}
	}


}