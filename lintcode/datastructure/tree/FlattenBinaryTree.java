/**
Flatten Binary Tree to Linked List:

-Flatten a binary tree to a fake "linked list" in pre-order traversal.
-Here we use the right pointer in TreeNode as the next pointer in ListNode.

Example
              1
               \
     1          2
    / \          \
   2   5    =>    3
  / \   \          \
 3   4   6          4
                     \
                      5
                       \
                        6
*/

import java.util.*;
import java.io.*;

public class FlattenBinaryTree{
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
     * @return: 
     * Solution: 
     	-Find rightmost on the left-branch;
		-connect rightmost.right = cur.right
		-move left tree to right;
		-goes to right branch and do the same
		-until right branch == null
		EX:  
		   1               1  				1
    	  / \             /					 \
   		 2   5    =>     2         =>         2
  		/ \   \         / \   				 / \
 	   3   4   6       3   4				3	4
 	        			    \                    \
 	        			     5					  5
 	        			      \					   \
 	        			       6					6
	 * Time: O(n);
     */
    public void flatten(TreeNode root) {
        if(root == null)
        	return;
        TreeNode cur = root;

        while(cur != null){
        	
        	if(cur.left != null){
        		//find rightmost node on left-branch
        		TreeNode rightmost = cur.left;
        		while(rightmost.right != null){
        			rightmost = rightmost.right;
        		}
        		//connect rightmost.right to right-branch
        		rightmost.right = cur.right;
        		//move left branch to right
        		cur.right = cur.left;
        		cur.left = null;
        	}
        	//iterate to next right-node and do the same
        	cur = cur.right;
        }
    }

    public void printList(TreeNode root){
    	while(root != null){
    		System.out.print(root.val + " -> ");
    		root = root.right;
    	}
    	System.out.println();
    }

    public static void main(String [] args){
  		FlattenBinaryTree obj = new FlattenBinaryTree();
  		FlattenBinaryTree.TreeNode node1 = obj.new TreeNode(1);
  		FlattenBinaryTree.TreeNode node2 = obj.new TreeNode(2);
  		FlattenBinaryTree.TreeNode node3 = obj.new TreeNode(3);
  		FlattenBinaryTree.TreeNode node4 = obj.new TreeNode(4);
  		FlattenBinaryTree.TreeNode node5 = obj.new TreeNode(5);
  		FlattenBinaryTree.TreeNode node6 = obj.new TreeNode(6);
  		FlattenBinaryTree.TreeNode node7 = obj.new TreeNode(7);
  		FlattenBinaryTree.TreeNode node8 = obj.new TreeNode(8);
  		FlattenBinaryTree.TreeNode node9 = obj.new TreeNode(9);
  		FlattenBinaryTree.TreeNode node10 = obj.new TreeNode(10);
  		FlattenBinaryTree.TreeNode node11 = obj.new TreeNode(11);
  		FlattenBinaryTree.TreeNode node12 = obj.new TreeNode(12);

  		node1.left = node2;
  		node1.right = node5;
  		node2.left = node3;
  		node2.right = node4;
  		node5.right = node6;

  		obj.flatten(node1);
  		obj.printList(node1);
  	}

}



