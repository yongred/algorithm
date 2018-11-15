/**
4.2 Minimal Tree: Given a sorted (increasing order) array with unique integer elements, write an algorithm
to create a binary search tree with minimal height.
Hints: #19, #73, #116

*/
import treepackage.*;
import java.util.*;

public class MinimalTree{

	//Time: O(n), Space: O(n) for recursive stack
	public BinarySearchTree.Node minHeightTree(int [] arr){
		int len = arr.length;
		if(len < 1)
			return null;

		BinarySearchTree.Node root = makeTree(0, len-1, arr);

		return root;
	}
	//O(n) b/c goes through every index, T(n) = 2T(n/2) + C
	public BinarySearchTree.Node makeTree(int first, int last, int [] arr){
		if(last - first == 0) // 1 element
			return new BinarySearchTree().new Node(arr[first]);

		int mid = (first + last + 1) / 2; //+1 b/c index start at 0
		BinarySearchTree.Node root = new BinarySearchTree().new Node(arr[mid]);
		BinarySearchTree.Node left = makeTree(first, mid -1 , arr);
		if(last - first == 1){ //2 elements, no need for rights
			root.setLeft(left);
			left.setParent(root);
			return root;
		}
		BinarySearchTree.Node right = makeTree(mid+1, last, arr);

		root.setLeft(left);
		left.setParent(root);
		root.setRight(right);
		right.setParent(root);

		return root;
	}


	public static void main(String [] args){
		MinimalTree obj = new MinimalTree();
		BinarySearchTree tree = new BinarySearchTree();

		int [] arr = {1, 3, 6, 10, 12, 19, 20};
		BinarySearchTree.Node root = obj.minHeightTree(arr);

		tree.inorderWalk(root);
		System.out.println();
	}


}






