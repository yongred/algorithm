/**
4.10 Check Subtree: T1 and T2 are two very large binary trees, with T1 much bigger than T2. Create an
algorithm to determine if T2 is a subtree of T1.
A tree T2 is a subtree of T1 if there exists a node n in T1 such that the subtree of n is identical to T2.
That is, if you cut off the tree at node n, the two trees would be identical.
Hints: #4, #11, #18, #31, #37
*/
import java.util.*;

public class CheckSubtree{
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
	/**
	First approach: find the same data val as root2, then traverse down to see if identical subtree
	worst case: O(n lg n), ex: T1 is 18 2s, T2 is 3 2s. 
	*/
	public boolean checkSubtree(Node root1, Node root2){
		if(root1 == null || root2 == null){  //if both null count as true
			if(root1 != root2)
				return false;
			else
				return true;

		}
		boolean isSub = false;
		if(root1.data == root2.data) //when root is identical, traverse down
			isSub = isSubtree(root1, root2);
		if(isSub)
			return true;

		boolean left = checkSubtree(root1.left, root2); //check left find identical roots
		if(left)
			return true;
		return checkSubtree(root1.right, root2); //check right find identical roots

	}

	public boolean isSubtree(Node n1, Node n2){
		if(n1 == null || n2 == null){
			if(n1 == n2) //both null
				return true;
			else
				return false;
		}
		if(n1.data != n2.data)
			return false;
		boolean left = isSubtree(n1.left, n2.left);
		if(!left)
			return false;

		return isSubtree(n1.right, n2.right);
	}

	/**
	The Substring approach: if T2 is a subtree of T1, then use of preorder traverse and special null node,
	the string of T2 traverse will be a substring of T1 Traverse.
	O(T1 + T2) Time, and O(T1 + T2) space;
	*/
	public boolean containsTree(Node root1, Node root2){
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		//stringbuilder preorder traverse for both trees
		getString(root1, sb1);  //space: t1, time: t1
		getString(root2, sb2);  //space: t2 time: t2
		// System.out.println(sb1.toString());
		// System.out.println(sb2.toString());
		return (sb1.indexOf(sb2.toString()) != -1);
	}

	public void getString(Node root, StringBuilder sb){
		if(root == null){
			sb.append("X ");
			return;
		} 
		//preorder traverse, root - left - right;
		sb.append(root.data + " ");
		getString(root.left, sb);
		getString(root.right, sb);
	}

	public static void main(String [] args){
		CheckSubtree obj = new CheckSubtree();

		CheckSubtree.Node node1 = obj.new Node(1);
		CheckSubtree.Node node2 = obj.new Node(2);
		CheckSubtree.Node node3 = obj.new Node(3);
		CheckSubtree.Node node4 = obj.new Node(4);
		CheckSubtree.Node node5 = obj.new Node(5);
		CheckSubtree.Node node6 = obj.new Node(6);
		CheckSubtree.Node node7 = obj.new Node(7);
		CheckSubtree.Node node8 = obj.new Node(8);
		CheckSubtree.Node node9 = obj.new Node(9);
		CheckSubtree.Node node10 = obj.new Node(10);
		CheckSubtree.Node node11 = obj.new Node(11);
		CheckSubtree.Node node12 = obj.new Node(12);

		node1.left = node2;
		node1.right = node3;
		node2.left = node4;
		node2.right = node5;
		node3.left = node6;
		node3.right = node7;
		node7.right = node9;
		node7.left = node8;
		//node8.right = node10;

		System.out.println(obj.checkSubtree(node1, node3));
		System.out.println(obj.containsTree(node1, node3));
	}


}