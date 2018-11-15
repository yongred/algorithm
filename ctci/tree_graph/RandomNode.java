/**
4.11 Random Node: You are implementing a binary search tree class from scratch, which, in addition
to insert, find, and delete, has a method getRandomNode() which returns a random node
from the tree. All nodes should be equally likely to be chosen. Design and implement an algorithm
for get Ra ndomNode, and explain how you would implement the rest of the methods.
Hints: #42, #54, #62, #75, #89, #99, #112, #119
*/
import java.util.*;

public class RandomNode{

	//size of the nodes now
	public int sizeNow = 0;

	public class Node{
		public int data;
		public Node parent;
		public Node left;
		public Node right;

		public Node(int data){
			this.data = data;
		}
	}
	/*
		just add left or right child, no need to break the current structure
	*/
	public void insert(Node root, Node node){
		if(node == null)
			return;
		if(root == null){ //if empty tree, then node is the root
			root = node;
			sizeNow++;
			return;
		}

		if(node.data < root.data){ //node < root, go to the left
			if(root.left == null){  //no left child, node become left child
				node.parent = root;
				root.left = node;
				sizeNow++;
				return;
			}
			insert(root.left, node); //traverse left
		}
		else{
			if(root.right == null){  //no right child, node become right child
				node.parent = root;
				root.right = node;
				sizeNow++;
				return;
			}
			insert(root.right, node);  //traverse right
		}
	}

	/*
	delete: case1: no children, just delete no need transplant
		case2: left or right is null, use the 1 that is not null as replacement;
		case3: both left & right exist, use successor or predecessor as replacement
	*/
	public void delete(Node root, Node target){
		if(root == null || target == null)
			return;
		if(target.left == null)
			transplant(root, target, target.right);
		else if(target.right == null)
			transplant(root, target, target.left);
		else{  //both left & right exist, use successor
			Node replace = treeMin(target.right); //find successor
			//if replace is not the immediate child of target, then we need to adjust right subtree, since we removing min node on the right
			if(replace.parent != target){
				//replace is the leftmost child, if it has right child, place right into its place
				transplant(root, replace, replace.right);
				//target's right is now replace's right, connect replace to right subtree
				replace.right = target.right;
				target.right.parent = replace;
			}
			//connect replace to left subtree of target
			replace.left = target.left;
			target.left.parent = replace;
		}
		sizeNow--;
	}

	/*
	transplant a subtree with another subtree
	*/
	public void transplant(Node root, Node target, Node replace){
		if(root == target){ //target is the root, just change the whole tree
			root = replace;
		}
		else if(target.parent.left == target){
			//target is the left child, replace become the left child of target.parent
			target.parent.left = replace;
		}
		else{ //target is right child, parent right replace
			target.parent.right = replace;
		}

		if(replace != null)  //set replace parent to target parent
			replace.parent = target.parent;
	}

	/*
	get min of the tree, get the left most node
	*/
	public Node treeMin(Node root){
		if(root == null)
			return null;
		//traverse left until there is no more left child,
		if(root.left == null)
			return root;
		else
			return treeMin(root.left);
		
	}
	/*
	get max of the tree, get the left most node
	*/
	public Node treeMax(Node root){
		if(root == null)
			return null;
		//traverse left until there is no more left child,
		if(root.right == null)
			return root;
		else
			return treeMax(root.right);
		
	}

	/**
	RandomNode: store in arr, and return random index
	*/
	public Node randNode(Node root){
		this.list = new ArrayList<Node>();
		int count = count(root);
		Random rand = new Random();
		int randNum = rand.nextInt(count);
		return this.list.get(randNum);
	}
	public ArrayList<Node> list = new ArrayList<Node>();

	public int count(Node root){
		if(root == null)
			return 0;
		list.add(root);
		return count(root.left) + count(root.right) + 1;
	}

	public static void main(String [] args){
		RandomNode obj = new RandomNode();
		RandomNode.Node node10 = obj.new Node(10);
		obj.insert(node10, obj.new Node(5));
		obj.insert(node10, obj.new Node(4));
		obj.insert(node10, obj.new Node(8));
		obj.insert(node10, obj.new Node(1));
		obj.insert(node10, obj.new Node(6));
		obj.insert(node10, obj.new Node(7));
		obj.insert(node10, obj.new Node(9));
		obj.insert(node10, obj.new Node(15));
		obj.insert(node10, obj.new Node(11));
		obj.insert(node10, obj.new Node(13));
		obj.insert(node10, obj.new Node(12));
		obj.insert(node10, obj.new Node(14));
		obj.insert(node10, obj.new Node(18));
		obj.insert(node10, obj.new Node(16));
		obj.insert(node10, obj.new Node(19));
		System.out.println(obj.randNode(node10).data);
	}
}