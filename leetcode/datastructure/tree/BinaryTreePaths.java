/**
257. Binary Tree Paths
Given a binary tree, return all root-to-leaf paths.

Note: A leaf is a node with no children.

Example:

Input:

   1
 /   \
2     3
 \
  5

Output: ["1->2->5", "1->3"]

Explanation: All root-to-leaf paths are: 1->2->5, 1->3
*/

/**
Solution: preorder traverse, add nodeVal to pathStr. When reach leaf, add pathStr to res.
How to Arrive:
* Every time we reach to the leaf node, there is a new path. 1 leaf = 1 path.
* So we just do preorder/dfs traverse untiil gets to leaf node.
* We keep a pathStr to pass down next level to add new nodeVal.
* **Key**: passing a new String of pathstr every level, If use reference like StringBuilder then you will have to remove current added "->num" which is alot of work.
* Time: O(n)
* Space: O(n)
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
class BinaryTreePaths {

	/**
	 * Solution: preorder traverse, add nodeVal to pathStr. When reach leaf, add pathStr to res.
	 * Time: O(n)
	 * Space: O(n)
	 */
  List<String> res = new ArrayList<>();
  
  public List<String> binaryTreePaths(TreeNode root) {
    preorder(root, "");
    return res;
  }
  
  public void preorder(TreeNode root, String path) {
    // preorder, dfs, until leaf node.
    if (root == null) {
      return;
    }
    // check leaf node
    if (root.left == null && root.right == null) {
      // is leaf, add root to string
      // then, add to reslist.
      res.add(path + root.val);
      return;
    }
    // not a leaf. root, left, right
    // left
    preorder(root.left, path + root.val + "->");
    // right
    preorder(root.right, path + root.val + "->");
  }
  
  
}