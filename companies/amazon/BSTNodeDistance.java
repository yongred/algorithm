/**
1561. BST Node Distance
Given a list of numbers, construct a BST from it(you need to insert nodes one-by-one with the given order to get the BST) and find the distance between two given nodes.

Example
input:
numbers = [2,1,3]
node1 = 1
node2 = 3
output:
2
Notice
If two nodes do not appear in the BST, return -1
We guarantee that there are no duplicate nodes in BST
The node distance means the number of edges between two nodes
*/


public class BSTNodeDistance {

	class Node {
		int val;
		Node left;
		Node right;

		Node(int val) {
			this.val = val;
			left = right = null;
		}
	}
	
	/**
	 * Solution: Find LCA, then calc dist from lca to both nodes, add them.
	 * Time: O(n^2); to buildBST without rebalancing T(n^2), 
	 	* Find lca is O(lgN) on average, worst case O(n);
	 * Space: O(lgN)
	 */
	public int distBetweenNodes(int[] values, int node1, int node2) {
		if (values == null || values.length == 0) {
			return -1;
		}
		// build BST one val at a time.
		for (int i = 0; i < values.length; i++) {
			root = buildBST(root, values[i]);
		}
		// find dist
		return findDist(root, node1, node2);
	}

	private Node root = null;

	// construct a BST in given order, without to rebalance it.
	public Node buildBST(Node root, int val) {
		if (root == null) {
			return new Node(val);
		}
		if (root.val >= val) {
			// on the left
			root.left = buildBST(root.left, val);
		} else {
			// on the right
			root.right = buildBST(root.right, val);
		}
		return root;
	}

	public int findDist(Node root, int n1, int n2) {
		// find lca
		if (root == null) {
			return -1;
		}
		if (n1 == n2 && root.val == n1) {
			return 0;
		}
		// check left, right
		if (root.val > n1 && root.val > n2) {
			// on left.
			return findDist(root.left, n1, n2);
		}
		if (root.val < n1 && root.val < n2) {
			// on right
			return findDist(root.right, n1, n2);
		}
		// cur root is lca, find lcaDist for both, add them.
		int dist1 = lcaDist(root, n1);
		int dist2 = lcaDist(root, n2);
		return (dist1 == -1 || dist2 == -1) ? -1 : dist1 + dist2;
	}

	public int lcaDist(Node root, int val) {
		if (root == null) {
			return -1;
		}
		// curVal is root.
		if (root.val == val) {
			return 0;
		}
		// left
		if (root.val > val) {
			return 1 + lcaDist(root.left, val);
		}
		// right.
		int rightDist = lcaDist(root.right, val);
		return rightDist == -1 ? -1 : rightDist + 1;
	}

	// main
	public static void main(String[] args) {
		BSTNodeDistance obj = new BSTNodeDistance();
		int[] values = {5,6,3,1,2,4};
		int n1 = 6;
		int n2 = 2;
		int res = obj.distBetweenNodes(values, n1, n2);
		System.out.println(res);
	}


}