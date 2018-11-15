/**
Binary Tree Level Order Traversal:
Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
Example
Given binary tree {3,9,20,#,#,15,7},

    3
   / \
  9  20
    /  \
   15   7
 

return its level order traversal as:

[
  [3],
  [9,20],
  [15,7]
]
*/

import java.util.*;
import java.io.*;

public class BinaryTreeLevelTraverse {

	public class TreeNode {
	    public int val;
	    public TreeNode left, right;
	    public TreeNode(int val) {
	        this.val = val;
	        this.left = this.right = null;
	    }
	}
    /*
     * @param root: A Tree
     * @return: Level order a list of lists of integer
     * Solution: 
     	-BFS using queue to add each level;
   		-traverse one level, add all children (next level) to queue, and remove all cur level nodes (visited);
   		-therefore cur level nodes == queue.size(); since only cur level nodes are in queue;
   		-loop till there is no more children;

     * Time: O(n); Space: O(n);
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
    	List<List<Integer>> res = new LinkedList<List<Integer>>();
    	Queue<TreeNode> queue = new LinkedList<TreeNode>();

    	if(root == null) 
    		return res;

    	queue.add(root);
    	while(!queue.isEmpty()){
    		//queue only have cur level elements left; since all upper level is traversed and removed.
    		int levelwidth = queue.size();
    		List<Integer> sublist = new LinkedList<Integer>();
    		//traverse and remove all elements in this level
    		for (int i=0; i<levelwidth; i++){
    			//add children for next level traverse
    			if(queue.peek().left != null)
    				queue.add(queue.peek().left);
    			if(queue.peek().right != null) 
    				queue.add(queue.peek().right);
    			//remove cur level nodes and add to sublist
    			sublist.add(queue.poll().val);
    		}
    		//add this level sublist to res
    		res.add(sublist);
    	}

    	return res;

    }


	public static void main(String [] args){
		BinaryTreeLevelTraverse obj = new BinaryTreeLevelTraverse();
		BinaryTreeLevelTraverse.TreeNode node1 = obj.new TreeNode(1);
		BinaryTreeLevelTraverse.TreeNode node2 = obj.new TreeNode(2);
		BinaryTreeLevelTraverse.TreeNode node3 = obj.new TreeNode(3);
		BinaryTreeLevelTraverse.TreeNode node4 = obj.new TreeNode(4);
		BinaryTreeLevelTraverse.TreeNode node5 = obj.new TreeNode(5);
		BinaryTreeLevelTraverse.TreeNode node6 = obj.new TreeNode(6);
		BinaryTreeLevelTraverse.TreeNode node7 = obj.new TreeNode(7);
		BinaryTreeLevelTraverse.TreeNode node8 = obj.new TreeNode(8);
		BinaryTreeLevelTraverse.TreeNode node9 = obj.new TreeNode(9);
		BinaryTreeLevelTraverse.TreeNode node10 = obj.new TreeNode(10);
		BinaryTreeLevelTraverse.TreeNode node11 = obj.new TreeNode(11);
		BinaryTreeLevelTraverse.TreeNode node12 = obj.new TreeNode(12);

		node1.left = node2;
		node1.right = node3;
		node2.left = node4;
		node2.right = node5;
		node3.left = node6;
		node3.right = node7;
		node7.right = node9;
		node7.left = node8;
		node9.right = node10;
		node10.right = node11;
		node11.right = node12;

		List<List<Integer>> res = obj.levelOrder(node1);

		for(List<Integer> list : res){
			Integer [] arr = list.toArray(new Integer[list.size()]);
			System.out.println(Arrays.toString(arr));
		}

	}


}






