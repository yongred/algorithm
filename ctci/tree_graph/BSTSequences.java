/**
4.9 BST Sequences: A binary search tree was created by traversing through an array from left to right
and inserting each element. Given a binary search tree with distinct elements, print all possible
arrays that could have led to this tree.
EXAMPLE
	2
  /   \
 1	   3
Input:
Output: {2, 1, 3}, {2, 3, 1}
Hints: #39, #48, #66, #82

Solution: parent goes before children, and left or right children doesnt matter which one goes first
	input:    0
		   1     3
		    \     \ 
		     2     4

	{1,2} {3,4}
	weaved: {1,2,3,4}  {1,3,2,4}  {1,3,4,2}  {3,1,2,4}  {3,1,4,2}  {3,4,1,2}
	prepend 0 to all of them

	1 needs to be before 2, 3 needs to be before 4;
*/

import java.util.*;

public class BSTSequences{

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

	public ArrayList<LinkedList<Integer>> allSeq(Node root){
		ArrayList<LinkedList<Integer>> results = new ArrayList<LinkedList<Integer>>();
		if(root == null){
			//return empty list when root is null
			results.add(new LinkedList<Integer>());
			return results;
		}
		//prefix list to prepend to left and right weaved lists
		LinkedList<Integer> prefix = new LinkedList<Integer>();
		prefix.add(root.data);
		//get lists of left and right subtree
		ArrayList<LinkedList<Integer>> leftSeq = allSeq(root.left);
		ArrayList<LinkedList<Integer>> rightSeq = allSeq(root.right);
		//weave every left and right lists together 
		for(LinkedList<Integer> left : leftSeq){  
			for(LinkedList<Integer> right : rightSeq){ 
				//weaved var to store all the lists weaved, ans stored in weaved
				ArrayList<LinkedList<Integer>> weaved = new ArrayList<LinkedList<Integer>>();
				weaveLists(left, right, prefix, weaved);  //weaved root(prefix), left, right, together
				results.addAll(weaved); //add all lists to result
			}
		}
		return results;
	}

	/*
	weave the 2 lists in all possible ways with parents before children, 
	weave( {1,2} {3,4} )
		weave({2} {3,4} {1})
			...
		weave({1,2} {4} {3})
	worst time: 2^(lgN^2) = 1*2*4*8...,     n! or npx permutation with restrictions
	*/
	public void weaveLists(LinkedList<Integer> left, LinkedList<Integer> right, LinkedList<Integer> prefix, ArrayList<LinkedList<Integer>> results){
		//if one list is empty, postpend remaining list eles to prefix, and added to results
		if(left.size() == 0 || right.size() == 0){
			LinkedList<Integer> curResult = (LinkedList<Integer>) prefix.clone();
			curResult.addAll(left);  //atleast one of them is empty 
			curResult.addAll(right);
			results.add(curResult); //add 1 possible ans seq to results
			return;
		}
		//left side first ele(head) add to prefix, put head back when finish, since we still needed in previous steps
		int leftHead = left.removeFirst();
		prefix.addLast(leftHead);
		weaveLists(left, right, prefix, results); //recurse weave to add new seq
		prefix.removeLast();  //return to this state, since done weaving a seq
		left.addFirst(leftHead); //return to this state

		//do the same with right first ele(head)
		int rightHead = right.removeFirst();
		prefix.addLast(rightHead);
		weaveLists(left, right, prefix, results);
		prefix.removeLast();
		right.addFirst(rightHead);
	}

	public static void main(String [] args){
		BSTSequences obj = new BSTSequences();

		BSTSequences.Node node1 = obj.new Node(1);
		BSTSequences.Node node2 = obj.new Node(2);
		BSTSequences.Node node3 = obj.new Node(3);
		BSTSequences.Node node4 = obj.new Node(4);
		BSTSequences.Node node5 = obj.new Node(5);
		BSTSequences.Node node6 = obj.new Node(6);
		BSTSequences.Node node7 = obj.new Node(7);
		BSTSequences.Node node8 = obj.new Node(8);
		BSTSequences.Node node9 = obj.new Node(9);
		BSTSequences.Node node10 = obj.new Node(10);
		BSTSequences.Node node11 = obj.new Node(11);
		BSTSequences.Node node12 = obj.new Node(12);

		node5.left = node3;
		node5.right = node7;
		node3.left = node2;
		node3.right = node4;
		node7.left = node6;
		node7.right = node8;

		ArrayList<LinkedList<Integer>> results = obj.allSeq(node5);
		for(LinkedList<Integer> seq : results){
			System.out.println();
			for(Integer ele : seq){
				System.out.print(ele + ", ");
			}
		}
		System.out.println("count " + results.size());
	}
}