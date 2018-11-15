/**
4.8 First Common Ancestor: Design an algorithm and write code to find the first common ancestor
of two nodes in a binary tree. Avoid storing additional nodes in a data structure. NOTE: This is not
necessarily a binary search tree.
Hints: # 10, #16, #28, #36, #46, #70, #80, #96
*/

import java.util.*;

public class FirstCommonAncestor{
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
	/*
	ask if nodes can link to its parent.
	if not solution: traverse from the root, if nodes on diff sides
	then root is the fca, if on the same side, traverse to that side
	perform search on that lower level again
	Time: O(n);  T(n + n + lgn + lgn)
	Space: O(lg n);
	*/
	public Node FCA(Node root, Node node1, Node node2){
		//check if nodes in the tree first
		if(!DFS(root, node1) || !DFS(root, node2)){
			return null;
		}
		return fcaHelper(root, node1, node2);
	}

	public Node fcaHelper(Node root, Node node1, Node node2){
		if(root == null || node1 == null || node2 == null)
			return null;
		if(root == node1 || root == node2)
			return root;
		boolean left1, left2;
		left1 = DFS(root.left, node1);
		left2 = DFS(root.left, node2);
		if(left1 != left2) //on the different side
			return root;
		if(left1 && left2)
			return FCA(root.left, node1, node2);
		else
			return FCA(root.right, node1, node2);
	}

	public boolean DFS(Node root, Node target){
		if(root == null)
			return false;
		if(root == target){
			//System.out.println(root.data);
			return true;
		}
		if(DFS(root.left, target)){
			return true;
		}else{
			return DFS(root.right, target);
		}

	}

	/**
	Optimized Solution: instead of traversing the whole tree and then repeatedly traverse subtrees,
	we just recursively finding 2 nodes at the same time
	*/
	public class Result{  //helper class for returning 2 values, node and boolean
		public Node node;
		public boolean isFCA;
		public Result(Node node, boolean isFCA){
			this.node = node;
			this.isFCA = isFCA;
		}
	}

	public Node optimizedFCA(Node root, Node n1, Node n2){
		Result ans = optimizedHelper(root, n1, n2);
		if(ans.isFCA)
			return ans.node;
		return null;
	}

	/*
	Time: O(n), just cover each node once, like seaching for 2 nodes same time
	Space: O(d), recursive stack is how Deep the tree is 
	*/
	public Result optimizedHelper(Node root, Node n1, Node n2){
		if(root == null) //base case
			return new Result(null, false);
		if(root == n1 && root == n2) //n1 & n2 is same node
			return new Result(root, true);

		Result left = optimizedHelper(root.left, n1, n2);  //check left
		if(left.isFCA) //already found fca in left subtree
			return left;

		Result right = optimizedHelper(root.right, n1, n2);
		if(right.isFCA) //already found fca in right subtree
			return right;

		//if found nodes in both sides, root is the fca
		if(left.node != null && right.node != null)
			return new Result(root, true);
		else if(root == n1 || root == n2){ //currently on 1 of the node
			//ex: n1 is parent/grandparent of n2, when left/right have 1 node found
			boolean isFCA = left.node != null || right.node != null; 
			return new Result(root, isFCA);
		}else{ //return the side that finds the node, if both not found return null node
			Node node = (left.node != null) ? left.node : right.node;
			return new Result(node, false);
		}

	}

	/**
	solution if allowed to access parent (linked)
	-calculate the depth of the 2 nodes, move the one lower up to the same level
	-then traverse up till they meet, that meeting node is fca
	*/
	/*
	Time: O(d); finding how depth both are is about T(d1 + d2)
	just traverse the level of 2 nodes till meeting is less than T(d)
	Space: O(1)
	 */
	public Node FCA2(Node node1, Node node2){
		int d1 = depth(node1);
		int d2 = depth(node2);
		int diff = Math.abs(d1 - d2);
		//System.out.println("diff " + diff + " node1 " + node1.data + " node2 " + node2.data);
		if( d1 > d2){
			node1 = moveUp(node1, diff);
			//System.out.println(node1.data);
		}
		else{
			node2 = moveUp(node2, diff);
			//System.out.println(node2.data);
		}
		Node fca = findCA(node1, node2);
		return fca;
	}
	/*
	what level is the node on
	*/
	public int depth(Node node){
		int dep = 1;
		while(node.parent != null){
			node = node.parent;
			dep++;
			//System.out.print( dep + " ");
		}
		return dep;
	}
	/*
		move the one lower to equal level
	*/
	public Node moveUp(Node node, int moves){
		while(moves > 0 && node != null){
			node = node.parent;
			moves--;
		}

		return node;
	}
	/*
		traverse up till both nodes meet
	*/
	public Node findCA(Node node1, Node node2){
		while(node1 != node2 && node1 != null && node2 != null){
			node1 = node1.parent;
			node2 = node2.parent;
		}

		return (node1 == null || node2 == null) ? null : node1; 
	}

	/**
	some function to set up tree, ignore this
	*/
	public void connect(Node parent, Node child, boolean isLeft){
		child.parent = parent;
		if(isLeft)
			parent.left = child;
		else
			parent.right = child;
	}

	public static void main(String [] args){
		FirstCommonAncestor obj = new FirstCommonAncestor();

		FirstCommonAncestor.Node node1 = obj.new Node(1);
		FirstCommonAncestor.Node node2 = obj.new Node(2);
		FirstCommonAncestor.Node node3 = obj.new Node(3);
		FirstCommonAncestor.Node node4 = obj.new Node(4);
		FirstCommonAncestor.Node node5 = obj.new Node(5);
		FirstCommonAncestor.Node node6 = obj.new Node(6);
		FirstCommonAncestor.Node node7 = obj.new Node(7);
		FirstCommonAncestor.Node node8 = obj.new Node(8);
		FirstCommonAncestor.Node node9 = obj.new Node(9);
		FirstCommonAncestor.Node node10 = obj.new Node(10);
		FirstCommonAncestor.Node node11 = obj.new Node(11);
		FirstCommonAncestor.Node node12 = obj.new Node(12);

		obj.connect(node1, node2, true);//node1.left = node2;
		obj.connect(node1, node3, false);//node1.right = node3;
		obj.connect(node2, node4, true);//node2.left = node4;
		obj.connect(node2, node5, false);//node2.right = node5;
		obj.connect(node3, node6, true);//node3.left = node6;
		obj.connect(node3, node7, false);//node3.right = node7;
		obj.connect(node7, node9, false);//node7.right = node9;
		//node7.left = node8;
		obj.connect(node9, node10, false);//node9.right = node10;
		obj.connect(node10, node11, false);//node10.right = node11;
		obj.connect(node11, node12, false);//node11.right = node12;

		FirstCommonAncestor.Node ans = obj.FCA(node1, node6, node12);
		System.out.println("fca " + ans.data);

		ans = obj.FCA2(node6, node12);
		System.out.println("fca2 " + ans.data);

		ans = obj.optimizedFCA(node1, node6, node12);
		System.out.println("optimized fca " + ans.data);
	}
}