/**
199. Binary Tree Right Side View
Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

Example:

Input: [1,2,3,null,5,null,4]
Output: [1, 3, 4]
Explanation:

   1            <---
 /   \
2     3         <---
 \     \
  5     4       <---
*/

/**
Solution: BFS level traverse reversely. Add rightmost node.

                1
              /   \
             2     3
           /  \   / 
          4   5  6   
         / \    
        8  9   
[1,3,6,9] res.
lv: 0-, 1-, 2-, 3-
Q: l0(1-), l1(3-, 2-), l2(6-, 5-, 4-), l3(9, 8)

How to Arrive:
* Question is asking for the rightmost Node on every level. So we can do a level traverse reversely R L R L, and add rightmost node on that level to reslist.
* We need a levelFlag to indicates whether cur level rightmost node has found.
* If not, add curNode.val to reslist.
* Then, we add the children R L of curNode. **Key**: we still want to add leftNodes' children for next level traverse, b/c they are all candidates of rightmost node.
* Example above: Node4->right node9 is the rightmost node, eventhough node4 is the leftmost on its level. **So we do want to traverse every node on every level.**;
* Time: O(n);
* Space: O(n);
     
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
class BinaryTreeRightSideView {

	/**
	 * Solution: BFS level traverse reversely. Add rightmost node.
	 * Time: O(n);
	 * Space: O(n);
   */
  public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    // reverse level traverse, stop after 1st rightmost node.
    // Always R L R L.
    Deque<TreeNode> queue = new ArrayDeque<>();
    queue.addLast(root);
    boolean levelflag = false;
    // BFS, level traverse
    while (!queue.isEmpty()) {
      int levelsize = queue.size();
      for (int i = 0; i < levelsize; i++) {
        TreeNode curNode = queue.pollFirst();
        // check levelflag. If cur level val is picked or not.
        if (!levelflag) {
          // no node picked for cur level yet.
          res.add(curNode.val);
          levelflag = true;
        }
        // Add children in reversed (R L R L) order.
        if (curNode.right != null) {
          queue.addLast(curNode.right);
        }
        if (curNode.left != null) {
          queue.addLast(curNode.left);
        }
      }
      // reset flag
      levelflag = false;
    }
    // return res
    return res;
  }
  
  
}