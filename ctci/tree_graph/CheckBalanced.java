/**
4.4 Check Balanced: Implement a function to check if a binary tree is balanced. 
For the purposes of this question, a balanced tree is defined to be a tree such that the heights of the two subtrees of any node never differ by more than one.
Hints: #21, #33, #49, #105, #124
*/
import treepackage.*;
import java.util.*;

public class CheckBalanced{

	public class Node{
		public int data = Integer.MIN_VALUE;
		public Node parent = null;
		public Node left = null;
		public Node right = null;
		public boolean visited = false;

		public Node(int data){
			this.data = data;
		}
	}

	//public int max = 1;

	/*
	actual question balanced by height
	O(n) Time b/c traversed every nodes to find max height
	*/
	public boolean isBalancedTree1(Node root){
		if(root == null)
			return false;
		return (checkHeight(root) != Integer.MIN_VALUE);
		
	}
	/*
	check each time if subtree is balanced
	it Goes through each nodes so Time: O(n);
	Space: O(Height) since recursive stack goes from root to bottom each call. so, one stack(H) at a time
	*/
	public int checkHeight(Node root){
		if(root == null)
			return 0;
		if(root.left == null && root.right == null)
			return 1;
		int leftH = checkHeight(root.left);
		if(leftH == Integer.MIN_VALUE) //left subtree not balanced
			return Integer.MIN_VALUE;
		int rightH = checkHeight(root.right); 
		if(rightH == Integer.MIN_VALUE) //right subtree not balanced
			return Integer.MIN_VALUE;

		int diff = Math.abs(rightH - leftH);
		//this tree is not balanced, if heights differs more than 1
		if(diff > 1) 
			return Integer.MIN_VALUE;
		else {
		//return the height if balanced, which is maxHeight subtree + 1
			System.out.println(Math.max(rightH, leftH) + 1);
			return Math.max(rightH, leftH) + 1;
		}
	}

	//traversed every node, so O(n) time, Space O(n) also b/c recursion stack
	public int maxHeight(Node root){
		if(root == null)
			return 0;
		if(root.left == null && root.right == null) //isleaf return 1
			return 1;
		int max = 1; //max height
		//left subtree height + 1 == cur height
		if(maxHeight(root.left) + 1 > max) 
			max = maxHeight(root.left) + 1;
		//check right subtree height, if greater than left height
		if(maxHeight(root.right) + 1 > max) 
			max = maxHeight(root.right) + 1;

		return max;
	}

	//is balanced by number, not height, wrong solution
	public boolean isBalancedTree(Node root){
		if(root == null)
			return false;
		int countLeft = DFS(root.left);
		int countRight = DFS(root.right);
		System.out.println("left: " + countLeft + " right " + countRight);
		return (Math.abs(countRight - countLeft) <= 1);
	}

	public int DFS(Node root){
		if(root == null)
			return 0;
		int count = 0;
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);
		while(!stack.isEmpty()){
			Node cur = stack.pop();
			count++;
			if(!cur.visited){
				cur.visited = true;
				if(cur.right != null){
					if(!cur.right.visited)
						stack.push(cur.right);
				}
				if(cur.left != null){
					if(!cur.left.visited)
						stack.push(cur.left);
				}
				
			}
		}
		return count;
	}



	public static void main(String [] args){
		CheckBalanced obj = new CheckBalanced();

		CheckBalanced.Node node1 = obj.new Node(1);
		CheckBalanced.Node node2 = obj.new Node(2);
		CheckBalanced.Node node3 = obj.new Node(3);
		CheckBalanced.Node node4 = obj.new Node(4);
		CheckBalanced.Node node5 = obj.new Node(5);
		CheckBalanced.Node node6 = obj.new Node(6);
		CheckBalanced.Node node7 = obj.new Node(7);
		CheckBalanced.Node node8 = obj.new Node(8);
		CheckBalanced.Node node9 = obj.new Node(9);
		CheckBalanced.Node node10 = obj.new Node(10);
		CheckBalanced.Node node11 = obj.new Node(11);
		CheckBalanced.Node node12 = obj.new Node(12);

		node1.left = node2;
		node1.right = node3;
		node2.left = node4;
		node2.right = node5;
		node3.left = node6;
		node3.right = node7;
		node7.right = node9;
		node7.left = node8;
		//node8.right = node10;
	
		//System.out.println(obj.isBalancedTree(node1));
		System.out.println(obj.isBalancedTree1(node1));
	}


}




