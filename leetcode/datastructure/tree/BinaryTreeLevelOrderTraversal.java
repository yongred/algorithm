/**
102. Binary Tree Level Order Traversal
Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).

For example:
Given binary tree [3,9,20,null,null,15,7],
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

/**
Solution: 
* BFS using queue to add each level;
* traverse one level, add all children (next level) to queue, and remove all cur level nodes (visited);
* therefore cur level nodes == queue.size(); since only cur level nodes are in queue;
* loop till there is no more children;

* Time: O(n); Space: O(n);
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
class BinaryTreeLevelOrderTraversal {
  
  public List<List<Integer>> levelOrder(TreeNode root) {
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
    // return reslist
    return res;
  }
  
  
}