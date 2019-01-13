/**
226. Invert Binary Tree
Invert a binary tree.

Example:

Input:

     4
   /   \
  2     7
 / \   / \
1   3 6   9
Output:

     4
   /   \
  7     2
 / \   / \
9   6 3   1
Trivia:
This problem was inspired by this original tweet by Max Howell:

Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary tree on a whiteboard so f*** off.
*/

/**
Solution: Invert subtree first, then return root.
Ex:
       4
      /   \
     2     7
    / \  /   \
   1   3 6   nil

Swap the deepest subtree, them return root.
4.left = (7), 7.left = nil, 7.right = 6;
        4
       /
     7
    / \
   nil 6
4.right = (2), 2.right = 1, 2.left = 3;
         4
       /   \
     7      2
    / \    / \
   nil 6  3   1
	 
How to arrive:
**Key**: Cannot use val swap, root.left.val = root.right.val, because we have Null nodes, and Tree may not be symmetric. Root have to connect to swapped subtrees.
Ex: You have to connect parent to child node, 2 = null doesn't make 4.right connect to null;
		4
		/ \
nil   2
* This problem is simple, don't over think it.
* To invert the whole tree, we just need to invert the subtrees first
* then connect left = invertedRight, right = invertedLeft.
* Then return this inverted tree to connect to parent.
**Base case**: 
	* if root == null: then return null;
	* if root is the only node, return root.
**Recursive case**:
 * get the inverted Right and Left subtree.
 * Assign root.left to invertedRight.
 * Assign root.right to invertedLeft.

* Time: O(n);
* Space: O(n) in worst.


*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class InvertBinaryTree {

	/**
	 * Solution: Invert subtrees (left and right) first, connect to opposite, then return root.
	 * Time: O(n);
	 * Space: O(n) in worst.
	 */
  public TreeNode invertTree(TreeNode root) {
    // root left right <-> root right left; Swap.
    if (root == null) {
      return root;
    }
    // inverted right, left subtrees.
    TreeNode invertedRight = invertTree(root.right);
    TreeNode invertedLeft = invertTree(root.left);
    // reconnect to opposite.
    root.left = invertedRight;
    root.right = invertedLeft;
    return root;
  }

  
}