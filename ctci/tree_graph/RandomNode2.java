/**
4.11 Random Node: You are implementing a binary search tree class from scratch, which, in addition
to insert, find, and delete, has a method getRandomNode() which returns a random node
from the tree. All nodes should be equally likely to be chosen. Design and implement an algorithm
for get Ra ndomNode, and explain how you would implement the rest of the methods.
Hints: #42, #54, #62, #75, #89, #99, #112, #119
*/
import java.util.*;

public class RandomNode2{
	public int data;
	public RandomNode2 left;
	public RandomNode2 right;
	public RandomNode2 parent;
	public int size = 0;

	public RandomNode2(int data){
		this.data = data;
		size++;
	}

	/*
		just add left or right child, no need to break the current structure
	*/
	public void insert(int nodeData){
		RandomNode2 node = new RandomNode2(nodeData);
		if(nodeData < this.data){ //node < root, go to the left
			if(this.left == null){  //no left child, node become left child
				node.parent = this;
				this.left = node ;
				size++;
				return;
			}
			left.insert(node); //traverse left
		}
		else{
			if(this.right == null){  //no right child, node become right child
				node.parent = this;
				this.right = node;
				size++;
				return;
			}
			right.insert(node);  //traverse right
		}
	}

	/*
	search: smaller go left, bigger go right;
	*/
	public Node search(int data){
		if(this.data == data)
			return this;
		if(data < this.data)
			return (left != null) ? search(left) : null;
		if(data > this.data)
			return (right != null) ? search(right) : null;
	}

	/**
	RandomNode: each node should have 1/N chances, should have leftSize/N chance go left, 
	and rightSize/N chance go right;
	ex: left= 5 nodes, right = 5nodes, root = 1 node; 0-4 = left, 5 = root, 6-10 = right
	time: O(lg N); Space: O(Height); divide half each time
	*/
	public Node randNode(){
		int randnum = new Random().nextInt(this.size);
		if(randnum == left.size) //middle: 5
			return this;
		else if(randnum < left.size) //ex: 0-4
			return left.randNode();
		else //ex: 6-10
			return right.randNode();
	}

	/**
	RandomNode2: use get Ith element in the tree, and get a random i;
	avoid picking num each time, using random each time is costly,
	If we go left, meaning 0-4; on the left root we again pick a rand from 0-4,(leftSize == 5)
	it is not necessary to pick again
	time: O(lg N) or O(Depth); space: O(depth)
	*/
	public Node randNode2(){
		int randInd = new Random()nextInt(this.size);
		return getIthNode(randInd);
	}
	public Node getIthNode(int index){
		if(index == left.size)
			return this;
		else if(index < left.size)
			return left.getIthNode(index);
		else{  //on the right side; ex: 6-10 
			//6-10 should become 0-4 on the right side, so minus the left size(5), and 1 root, 
			// ex: 7-5 = 2, 2-1 = 1; 7 becomes 1; since its 0 based, and root counts 1, 
			int rightInd = index - left.size - 1; 
			return right.getIthNode(rightInd);
		}
	}
}