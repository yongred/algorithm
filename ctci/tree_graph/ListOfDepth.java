/**
4.3 List of Depths: Given a binary tree, design an algorithm which creates a linked list of all the nodes
at each depth (e.g., if you have a tree with depth D, you'll have D linked lists).
Hints: #107, #123, #135
*/
import treepackage.*;
import java.util.*;

public class ListOfDepth{

	public class Node{
		public int data = Integer.MIN_VALUE;
		public Node parent = null;
		public Node left = null;
		public Node right = null;

		public Node(int data){
			this.data = data;
		}
	}

	public ArrayList<LinkedList<Node>> levels;
	public ListOfDepth(){
		levels = new ArrayList<LinkedList<Node>>();
	}

	//Time: O(n), goes through every nodes at each level
	//Space: O(n), add every nodes to list
	public void depthLists(Node root){
		LinkedList<Node> curLvList = new LinkedList<Node>();
		if(root != null) //root in first level
			curLvList.add(root);

		while(curLvList.size() > 0){ //if there are nodes on previous level, use as parents
			levels.add(curLvList); //add previous nodes
			LinkedList<Node> parents = curLvList;  //previous list use as parents
			curLvList = new LinkedList<Node>(); //use as cur, for next level
			for(Node parent : parents){ //add each parent's children to cur level
				if(parent.left != null)
					curLvList.add(parent.left);
				if(parent.right != null)
					curLvList.add(parent.right);
			}
			//System.out.println(" " + curLvList.size());
		}
		
	}

	public static void main(String [] args){
		ListOfDepth obj = new ListOfDepth();

		ListOfDepth.Node node1 = obj.new Node(1);
		ListOfDepth.Node node2 = obj.new Node(2);
		ListOfDepth.Node node3 = obj.new Node(3);
		ListOfDepth.Node node4 = obj.new Node(4);
		ListOfDepth.Node node5 = obj.new Node(5);
		ListOfDepth.Node node6 = obj.new Node(6);
		ListOfDepth.Node node7 = obj.new Node(7);
		ListOfDepth.Node node8 = obj.new Node(8);
		ListOfDepth.Node node9 = obj.new Node(9);
		ListOfDepth.Node node10 = obj.new Node(10);
		ListOfDepth.Node node11 = obj.new Node(11);
		ListOfDepth.Node node12 = obj.new Node(12);

		node1.left = node2;
		node1.right = node3;
		node2.left = node4;
		node2.right = node9;
		node3.right = node5;
		node4.left = node6;
		node4.right = node7;
		node9.left = node10;
		node9.right = node11;
		node5.left = node12;
		node7.right = node8;

		obj.depthLists(node1);

		for(LinkedList<ListOfDepth.Node> list : obj.levels){
			
			for(ListOfDepth.Node node : list){
				System.out.print(node.data + ", ");
			}
			System.out.println();
		}
	}


}