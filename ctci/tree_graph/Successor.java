/**
4.6 Successor: Write an algorithm to find the "next" node (i .e., in-order successor) of a given node in a
binary search tree. You may assume that each node has a link to its parent.
Hints: #79, #91
*/

public class Successor{

	public class Node{
		public int data = Integer.MAX_VALUE;
		public Node parent = null;
		public Node left = null;
		public Node right = null;

		public Node(int data){
			this.data = data;
		}
	}
	/*Time: O(H) worst case traverse the height of the tree
	space: O(1)
	*/
	public Node successor(Node root){
		if(root == null)
			return null;
		if(root.right != null)
			return minNode(root.right);

		Node parent = root.parent;
		while(parent.left != root){
			root = parent;
			parent = root.parent;
		}
		return parent;
	}

	public Node minNode(Node root){
		if(root == null)
			return null;
		Node cur = root;
		while(cur.left != null){
			cur = cur.left;
		}
		return cur;
	}

	public void setRelation(Node parent, Node child){
		if(parent.data >= child.data)
			parent.left = child;
		else
			parent.right = child;
		child.parent = parent;
	}

	public static void main(String [] args){
		Successor obj = new Successor();

		Successor.Node node1 = obj.new Node(1);
		Successor.Node node2 = obj.new Node(2);
		Successor.Node node3 = obj.new Node(3);
		Successor.Node node4 = obj.new Node(4);
		Successor.Node node5 = obj.new Node(5);
		Successor.Node node6 = obj.new Node(6);
		Successor.Node node7 = obj.new Node(7);
		Successor.Node node8 = obj.new Node(8);
		Successor.Node node9 = obj.new Node(9);
		Successor.Node node10 = obj.new Node(10);
		Successor.Node node11 = obj.new Node(11);
		Successor.Node node12 = obj.new Node(12);

		obj.setRelation(node7, node4);
		obj.setRelation(node7, node10);
		obj.setRelation(node4, node2);
		obj.setRelation(node4, node5);
		obj.setRelation(node2, node1);
		obj.setRelation(node2, node3);
		obj.setRelation(node5, node6);
		obj.setRelation(node10, node8);
		obj.setRelation(node10, node12);
		obj.setRelation(node8, node9);
		obj.setRelation(node12, node11);

		System.out.println(obj.successor(node6).data);
	}

}