/**
4.5 Validate BST: Implement a function to check if a binary tree is a binary search tree.
Hints: #35, #57, #86, # 113, # 128
*/
import treepackage.*;
import java.util.*;

public class ValidateBST{

	public class Node{
		public int data = Integer.MAX_VALUE;
		public Node parent = null;
		public Node left = null;
		public Node right = null;

		public Node(int data){
			this.data = data;
		}
	}
	/*
	for this question: for duplicated values, put on left not right
	ex: 10.left = 10 correct, 10.right = 10 false;
	*/
	public boolean isBST(Node root){
		if(root == null)
			return false;
		return (inorderCheck(root) != Integer.MAX_VALUE);
	}
	/*O(n) time to traverse every node, inorder walk, 
	O(log n) Space, half each time till bottom
	*/
	public int inorderCheck(Node root){
		if(root.left == null && root.right == null) //its a leaf, return data
			return root.data;
		if(root.left != null){
			int left = inorderCheck(root.left);
			if(left > root.data) // left 10.left = 10; is correct for this case
				return Integer.MAX_VALUE;
		}
		if(root.right != null){
			int right = inorderCheck(root.right);
			if(right <= root.data) // 10.right = 10 is not correct
				return Integer.MAX_VALUE;
			else
				return right; //right is largest
		}
		return root.data; //if right is null
	}

	public static void main(String [] args){
		ValidateBST obj = new ValidateBST();

		ValidateBST.Node node1 = obj.new Node(1);
		ValidateBST.Node node2 = obj.new Node(2);
		ValidateBST.Node node3 = obj.new Node(3);
		ValidateBST.Node node4 = obj.new Node(4);
		ValidateBST.Node node5 = obj.new Node(5);
		ValidateBST.Node node6 = obj.new Node(6);
		ValidateBST.Node node7 = obj.new Node(7);
		ValidateBST.Node node8 = obj.new Node(8);
		ValidateBST.Node node9 = obj.new Node(9);
		ValidateBST.Node node10 = obj.new Node(10);
		ValidateBST.Node node11 = obj.new Node(11);
		ValidateBST.Node node12 = obj.new Node(12);

		node7.left = node4;
		node7.right = node10;
		node4.left = node2;
		node4.right = node5;
		node2.left = node1;
		node2.right = node3;
		node5.right = node6;
		node10.left= node8;
		node10.right = node12;
		node8.right = node9;
		node12.left = node11;

		System.out.println(obj.isBST(node7));
	}
}