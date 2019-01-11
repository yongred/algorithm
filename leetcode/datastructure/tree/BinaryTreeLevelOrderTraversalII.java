/**
107. Binary Tree Level Order Traversal II
Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).

For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its bottom-up level order traversal as:
[
  [15,7],
  [9,20],
  [3]
]
*/

/**
Solution 1: BFS
How I arrive:

Bottom up is the same as Top down, except reversed. So we just need to reverse the result list.
Find the Top-Down by using Queue
Adding cur level nodes to Queue, and find Level node # by counting queue size.
Loop Level Count # of times, So we know when cur level ended.
Adding cur level nodes' children into Queue (next level); then pop out cur level nodes from Queue.
Add cur level nodes to SubList. Add subList to Res List.
Time: O(n); Space: O(N);
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
class BinaryTreeLevelOrderTraversalII {

	/**
	 * Solution 1: BFS Bottom up is the same as Top down, except reversed. So we just need to reverse the result list.
	 * Time: O(n)
	 * Space: O(n)
	 */
  public List<List<Integer>> levelOrderBottom(TreeNode root) {
    // corner case, null root
    if (root == null) {
      return new ArrayList<>();
    }
    List<List<Integer>> res = new ArrayList<>();
    Deque<TreeNode> queue = new ArrayDeque<>();
    queue.addLast(root);
    // bfs. 
    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      // elms in curLevel.
      List<Integer> levelList = new ArrayList<>();
      for (int i = 0; i < levelSize; i++) {
        // FIFO
        TreeNode curNode = queue.pollFirst();
        // add to levelList
        levelList.add(curNode.val);
        // add left, right for next level
        if (curNode.left != null) {
          queue.addLast(curNode.left);
        }
        if (curNode.right != null) {
          queue.addLast(curNode.right);
        }
      }
      // add curLevel list to resList.
      res.add(levelList);
    }
    // return reslist reversed
    Collections.reverse(res);
    return res;
  }
}