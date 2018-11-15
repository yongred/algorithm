/** 
binary search tree implementation
*/
package treepackage;
import java.util.*;

public class BinarySearchTree{
	
	public class Node{
		private int data = Integer.MIN_VALUE;
		private Node parent = null;
		private Node left = null;
		private Node right = null;

		public Node(){

		}

		public Node(int data){
			this.data = data;
		}

		public Node getParent(){
			return parent;
		}

		public void setParent(Node parent){
			this.parent = parent;
		}

		public int getData(){
			return this.data;
		}

		public void setData(int data){
			this.data = data;
		}

		public Node getLeft(){
			return left;
		}
		public void setLeft(Node left){
			this.left = left;
		}
		public Node getRight(){
			return right;
		}
		public void setRight(Node right){
			this.right = right;
		} 
	}//end Node

	private Node root = null;

	public BinarySearchTree(){

	}
	public BinarySearchTree(int num){
		root = new Node(num);
	}

	public void insert(Node cur, Node node){
		if(node == null)
			return;
		if(this.root == null){
			this.root = node;
			return;
		}
		if(node.getData() < cur.getData()){ //go left
			if(cur.getLeft() == null){
				//if cur.left = null, no smaller child to compare, then node becomes cur.left
				cur.setLeft(node);
				node.setParent(cur);
				return;
			}
			insert(cur.getLeft(), node);
		}
		else{
			if(cur.getRight() == null){
				cur.setRight(node);
				node.setParent(cur);
				return;
			}
			insert(cur.getRight(), node);
		}

	}

	public void delete(Node target){
		if(target.getLeft() == null)
			//no left child, put right child as replacement
			transplant(target, target.getRight());
		else if(target.getRight() == null)
			transplant(target, target.getLeft());
		else{  //both left & right not null, use successor as replacement, (predecessor also works)
			Node replace = treeMin(target.getRight()); //successor
			//if replace is root of subtree, then right subtree is set
			if(replace.getParent() != target){
				//make replaceNode the root of subtree
				transplant(replace, replace.getRight());  //replace is the leftmost, move right subtree of replace to replace's position
				//right subtree connect to replace instead of target
				replace.setRight(target.getRight()); 
				target.getRight().setParent(replace);
			}
			//left subtree connect to replace
			replace.setLeft(target.getLeft());
			target.getLeft().setParent(replace);
		}

	}

	//replace one subtree node with another subtree node
	public void transplant(Node target, Node replace){
		if(target == this.root)
			this.root = replace;
		else if(target.getParent().getLeft() == target)
			target.getParent().setLeft(replace);
		else
			target.getParent().setRight(replace);

		if(replace != null)
			replace.setParent(target.getParent());
	}

	public Node successor(Node target){
		//one node larger than target
		if(target.getRight() != null)
			//if have right child, min of right is successor
			return treeMin(target);
		Node parent = target.getParent();
		while(parent != null && parent.getLeft() != target){
			//loop until curNode is left child (<) of parent
			target = parent;
			parent = target.parent;
		}
		return parent;  //if null means no successor
	}

	public Node predecessor(Node target){
		//one node less than target
		if(target.getLeft() != null)
			//if have left child, max of left is predecessor
			return treeMax(target);
		Node parent = target.getParent();
		while(parent != null && parent.getRight() != target){
			//loop until curNode is right child (>) of parent
			target = parent;
			parent = target.parent;
		}
		return parent;  //if null means no successor
	}

	public Node search(Node cur, int data){
		if(cur == null || cur.getData() == data)
			return cur;
		if(data < cur.getData())
			return search(cur.getLeft(), data);
		else
			return search(cur.getRight(), data);
	}

	public void inorderWalk(Node cur){
		if(cur == null)
			return;
		inorderWalk(cur.getLeft());
		System.out.print(cur.getData() + " ");
		inorderWalk(cur.getRight());

	}

	public void preorderWalk(Node cur){
		if(cur == null)
			return;
		System.out.print(cur.getData() + " ");
		preorderWalk(cur.getLeft());
		preorderWalk(cur.getRight());

	}

	public Node treeMin(Node root){
		if(root == null)
			return null;
		Node cur = this.root;
		while(cur.getLeft() != null){
			cur = cur.getLeft();
		}
		return cur;
	}

	public Node treeMax(Node root){
		if(root == null)
			return null;
		Node cur = this.root;
		while(cur.getRight() != null){
			cur = cur.getRight();
		}
		return cur;
	}

	public Node getRoot(){
		return root;
	}
	public void setRoot(Node root){
		this.root = root;
	}

	public static void main(String [] args){
		BinarySearchTree obj = new BinarySearchTree();
		obj.insert(obj.getRoot() ,obj.new Node(5));
		BinarySearchTree.Node root = obj.getRoot();
		obj.insert(obj.getRoot() ,obj.new Node(10));
		obj.insert(obj.getRoot() ,obj.new Node(3));
		//System.out.println(root.getRight().getData());
		obj.insert(obj.getRoot() ,obj.new Node(1));
		obj.insert(obj.getRoot() ,obj.new Node(2));
		obj.insert(obj.getRoot() ,obj.new Node(9));
		obj.insert(obj.getRoot() ,obj.new Node(13));
		obj.insert(obj.getRoot() ,obj.new Node(11));
		obj.inorderWalk(root);
		BinarySearchTree.Node target = obj.search(root, 9);
		System.out.println("\nsearch " + target.getData());
		System.out.println("successor " + obj.successor(target).getData());
		System.out.println("predecessor " + obj.predecessor(target).getData());
		System.out.println("deleting.. ");
		obj.delete(target);
		obj.inorderWalk(root);
		System.out.println();
		obj.preorderWalk(root);
	}
}

