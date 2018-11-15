/**
Search Range in Binary Search Tree:
Given two values k1 and k2 (where k1 < k2) and a root pointer to a Binary Search Tree. Find all the keys of tree in range k1 to k2. i.e. print all x such that k1<=x<=k2 and x is a key of given BST. Return all the keys in ascending order.

If k1 = 10 and k2 = 22, then your function should return [12, 20, 22].
	20
   /  \
  8   22
 / \
4   12
*/
import java.util.*;
import java.io.*;

public class SearchRangeBinary{

	public class TreeNode {
     	public int val;
     	public TreeNode left, right;
     	public TreeNode(int val) {
         	this.val = val;
         	this.left = this.right = null;
     	}
 	}

 	public void connect(TreeNode parent, TreeNode child, boolean isLeft){
		//child.parent = parent;
		if(isLeft)
			parent.left = child;
		else
			parent.right = child;
	}
		
	/*
     * @param root: param root: The root of the binary search tree
     * @param k1: An integer
     * @param k2: An integer
     * @return: return: Return all keys that k1<=key<=k2 in ascending order
     * Time: O(n), Space: O(n)  nodes between (k2 - k1);
     */
   	public List<Integer> searchRange(TreeNode root, int k1, int k2) {
        List<Integer> list = new ArrayList<Integer>();
        preorderRange(root, k1, k2, list);
        return list;
    }

    public void preorderRange(TreeNode root, int k1, int k2, List<Integer> list){
    	if(root != null){
    		//if current val is larger than k1, check left/smaller node
    		if(k1 < root.val)
    			preorderRange(root.left, k1, k2, list);
    		//if inside range, add val to list
    		if(k1 <= root.val && root.val <= k2)
    			list.add(root.val);
    		//if current val smaller than k2, check right/larger node
    		if(k2 > root.val)
    			preorderRange(root.right, k1, k2, list);
    	}
    }

    public static void main(String[] args){
		SearchRangeBinary obj = new SearchRangeBinary();
		SearchRangeBinary.TreeNode node1 = obj.new TreeNode(1);
		SearchRangeBinary.TreeNode node2 = obj.new TreeNode(2);
		SearchRangeBinary.TreeNode node3 = obj.new TreeNode(3);
		SearchRangeBinary.TreeNode node4 = obj.new TreeNode(4);
		SearchRangeBinary.TreeNode node5 = obj.new TreeNode(5);
		SearchRangeBinary.TreeNode node6 = obj.new TreeNode(6);
		SearchRangeBinary.TreeNode node7 = obj.new TreeNode(7);
		SearchRangeBinary.TreeNode node8 = obj.new TreeNode(8);
		SearchRangeBinary.TreeNode node9 = obj.new TreeNode(9);
		SearchRangeBinary.TreeNode node10 = obj.new TreeNode(10);
		SearchRangeBinary.TreeNode node11 = obj.new TreeNode(11);
		SearchRangeBinary.TreeNode node12 = obj.new TreeNode(12);

		//node8 root
		obj.connect(node8, node5, true);//node8.left = node5;
		obj.connect(node8, node10, false);//node8.right = node10;
		obj.connect(node5, node4, true);//node5.left = node4;
		obj.connect(node5, node6, false);//node5.right = node6;
		obj.connect(node4, node2, true);//node4.left = node2;
		obj.connect(node2, node1, true);//node2.left = node1;
		obj.connect(node2, node3, false);//node2.right = node3;
		obj.connect(node6, node7, false);//node6.right = node7;
		obj.connect(node10, node9, true);//node10.left = node9;
		obj.connect(node10, node11, false);//node10.right = node11;
		obj.connect(node11, node12, false);//node11.right = node12;

		List<Integer> list = obj.searchRange(node8, 3, 10);
		System.out.println(Arrays.toString(list.toArray()));
		
	}
}