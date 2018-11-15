/**
Invert Binary Tree:

Invert a binary tree.

Example
  1         1
 / \       / \
2   3  => 3   2
   /       \
  4         4

*/

import java.util.*;
import java.io.*;

public class InvertBinaryTree {
 	public class TreeNode {
      	public int val;
      	public TreeNode left, right;
      	public TreeNode(int val) {
          this.val = val;
          this.left = this.right = null;
      	}
  	}

    /*
     * @param root: a TreeNode, the root of the binary tree
     * @return: nothing
     Solution1: Recursive Solution
     -using recursion, switch left node with right node.
     -do the same with children, left and right, till every node is switched.
	 -Top-Down
     Time: O(N); Space: O(Lg N);
     */
    public void invertBinaryTree(TreeNode root) {
        if(root == null)
            return;
        //switch imediate children of root.
        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
        //go and invert grandchildren
        invertBinaryTree(root.left);
        invertBinaryTree(root.right);
    }

    public void printTree(TreeNode tmpRoot) {

        Queue<TreeNode> currentLevel = new LinkedList<TreeNode>();
        Queue<TreeNode> nextLevel = new LinkedList<TreeNode>();

        currentLevel.add(tmpRoot);

        while (!currentLevel.isEmpty()) {
            Iterator<TreeNode> iter = currentLevel.iterator();
            while (iter.hasNext()) {
                TreeNode currentNode = iter.next();
                if (currentNode.left != null) {
                    nextLevel.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    nextLevel.add(currentNode.right);
                }
                System.out.print(currentNode.val + " ");
            }
            System.out.println();
            currentLevel = nextLevel;
            nextLevel = new LinkedList<TreeNode>();

        }

    }

    public static void main(String [] args){
  		InvertBinaryTree obj = new InvertBinaryTree();
  		InvertBinaryTree.TreeNode node1 = obj.new TreeNode(1);
  		InvertBinaryTree.TreeNode node2 = obj.new TreeNode(2);
  		InvertBinaryTree.TreeNode node3 = obj.new TreeNode(3);
  		InvertBinaryTree.TreeNode node4 = obj.new TreeNode(4);
  		InvertBinaryTree.TreeNode node5 = obj.new TreeNode(5);
  		InvertBinaryTree.TreeNode node6 = obj.new TreeNode(6);
  		InvertBinaryTree.TreeNode node7 = obj.new TreeNode(7);
  		InvertBinaryTree.TreeNode node8 = obj.new TreeNode(8);
  		InvertBinaryTree.TreeNode node9 = obj.new TreeNode(9);
  		InvertBinaryTree.TreeNode node10 = obj.new TreeNode(10);
  		InvertBinaryTree.TreeNode node11 = obj.new TreeNode(11);
  		InvertBinaryTree.TreeNode node12 = obj.new TreeNode(12);

  		node1.left = node2;
  		node1.right = node5;
  		node2.left = node3;
  		node2.right = node4;
  		node5.right = node6;

  		obj.printTree(node1);
  		System.out.println("----Inverting-----");
  		obj.invertBinaryTree(node1);
  		obj.printTree(node1);
  	}




}