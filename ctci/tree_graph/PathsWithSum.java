/**
4.12 Paths with Sum: You are given a binary tree in which each node contains an integer value (which might be positive or negative). 
Design an algorithm to count the number of paths that sum to a
given value. 
The path does not need to start or end at the root or a leaf, but it must go downwards
(traveling only from parent nodes to child nodes).
Hints: #6, #14, #52, #68, #77, #87, #94, #103, #108, #115
*/

import java.util.*;

public class PathsWithSum{

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
	bruteForce: to get all subtrees sums, traverse all nodes and getting child sums from each.
	ex:  123456; 1 {all paths}, 2 {all paths}, ..; lie finding all subarr of an arr
	Time: O(N lg N) in balanced tree, b/c max traverse each time is D (depth), depth is no more than lg N; 
	 we traverse N nodes, so N * D = N * lgN = N lg N;
	 	O(N^2) in unbalanced tree
	*/
	public int totalPaths(Node root, int targetSum){
		if(root == null)
			return 0;
		//find all paths starting from this root
		int rootPaths = pathsFromNode(root, targetSum, 0);
		//find all paths starting from left or right node, not include this parent root
		int leftPaths = totalPaths(root.left, targetSum);
		int rightPaths = totalPaths(root.right, targetSum);
		return rootPaths + leftPaths + rightPaths;
	}
	// every paths from this node to the bottom
	public int pathsFromNode(Node root, int targetSum, int curSum){
		if(root == null)
			return 0;
		int paths = 0;
		//add the cur val to sum and check
		curSum += root.data;
		if(curSum == targetSum) //record the path
			paths++;
		//go down to next level, find sums from this node to next and to bottom
		int leftPaths = pathsFromNode(root.left, targetSum, curSum);
		int rightPaths = pathsFromNode(root.right, targetSum, curSum);
		return paths + leftPaths + rightPaths;
	}

	/**
	ex: 8 is target; 
	nodes: 	 10, 5,  1,  2,  1,  -1,  7,  1,  2
	runSums: 10, 15, 16, 18, 17, 16, 23, 24, 26
	if curNode's runsum is 24, 24 - 8 = 16; there is 2 paths of 16
	optimized: runSum to current node - target = the val from ith node (8)vals away. 
			which means there is a path with 8, between curNode and ithNode;
			save runSums(parent nodes) in hash till this node. Hash[runSum - target] = (paths of 8 till curNode)
	*/
	public int countPathsWithSum(Node root, int targetSum){
		return countPaths(root, targetSum, 0, new HashMap<Integer, Integer>());
	}

	public int countPaths(Node node, int targetSum, int runningSum, HashMap<Integer, Integer> runSumCounts){
		if(node == null) return 0; //Base case
		//current runSum
		runningSum += node.data;
		int sum = runningSum - targetSum; //sum difference to find
		int totalPaths = runSumCounts.getOrDefault(sum, 0); //get paths with sum val
		//if cur runsum == target, then besides runSums from parents, count this one too
		if(runningSum == targetSum){
			totalPaths++;
		}

		incrementHash(runSumCounts, runningSum, 1); //hash[curRunSum]++, recording runsum
		//recurse left and right with the curRunSum val
		totalPaths += countPaths(node.left, targetSum, runningSum, runSumCounts); 
		totalPaths += countPaths(node.right, targetSum, runningSum, runSumCounts);
		//hash[curRunSum]--, b/c child should only have its parents/ancestors records of runsum
		incrementHash(runSumCounts, runningSum, -1); 

		return totalPaths;
	}
	

	public void incrementHash(HashMap<Integer, Integer> hash, int key, int delta){
		int newCount = hash.getOrDefault(key, 0) + delta;
		if( newCount <= 0){ //remove when no such key anymore
			hash.remove(key);
		} else{
			hash.put(key, newCount);
		}
	}

	public static void main(String [] args){
		PathsWithSum obj = new PathsWithSum();

		PathsWithSum.Node node1 = obj.new Node(1);
		PathsWithSum.Node node2 = obj.new Node(2);
		PathsWithSum.Node node_2 = obj.new Node(-2);
		PathsWithSum.Node node3 = obj.new Node(3);
		PathsWithSum.Node node3_2 = obj.new Node(3);
		PathsWithSum.Node node_3 = obj.new Node(-3);
		PathsWithSum.Node node4 = obj.new Node(4);
		PathsWithSum.Node node5 = obj.new Node(5);
		PathsWithSum.Node node6 = obj.new Node(6);
		PathsWithSum.Node node7 = obj.new Node(7);
		PathsWithSum.Node node8 = obj.new Node(8);
		PathsWithSum.Node node9 = obj.new Node(9);
		PathsWithSum.Node node10 = obj.new Node(10);
		PathsWithSum.Node node11 = obj.new Node(11);
		PathsWithSum.Node node12 = obj.new Node(12);

		node10.left = node5;
		node10.right = node_3;
		node5.left = node3;
		node5.right = node2;
		node_3.right = node11;
		node3.left = node3_2;
		node3.right = node_2;
		node2.right = node1;

		System.out.println(obj.totalPaths(node10, 8));
		System.out.println(obj.countPathsWithSum(node10, 8));
	}
}