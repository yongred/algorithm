/**
113. Path Sum II
Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.

Note: A leaf is a node with no children.

Example:

Given the below binary tree and sum = 22,

      5
     / \
    4   8
   /   / \
  11  13  4
 /  \    / \
7    2  5   1
Return:

[
   [5,4,11,2],
   [5,8,4,5]
]
*/

/**
Solution: DFS/preorder, backtracking.
How to Arrive:
* This is a typical DFS, backtracking type of problem.
* We need to add every possible list to res. So we need to keep a curPathList.
* Also keep a pathSum to check pathSum == sum at the leaf node.
* When reached to a leaf node, check pathSum == sum, add finalNodeVal to list, and add list to reslist.
**Recursive case**:
* We pick cur nodeVal to add to list, and add to pathSum.
* Then we traverse Left and Right with new pathSum and list (added curNodeVal).
* As for typical DFS problem, after depth search on left and right we need to pop off curNode to pick its siblings, so we can check every path combo sums.
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
class PathSumII {

	/**
	 * Solution: DFS/preorder, backtracking.
	 * Time: O(n);
	 * Space: O(n);
	 */
  List<List<Integer>> res = new ArrayList<>();
  
  public List<List<Integer>> pathSum(TreeNode root, int sum) {
    helper(root, new ArrayList<>(), 0, sum);
    return res;
  }
  
  public void helper(TreeNode root, List<Integer> list, int pathSum, int sum) {
    if (root == null) {
      return;
    }
    // leaf
    if (root.left == null && root.right == null) {
      // check pathSum == sum
      if (pathSum + root.val == sum) {
        list.add(root.val);
        res.add(new ArrayList<>(list));
        // DFS, remove this choice to pick another.
        list.remove(list.size() - 1);
      }
      return;
    }
    // preorder, root left right
    list.add(root.val);
    helper(root.left, list, pathSum + root.val, sum);
    helper(root.right, list, pathSum + root.val, sum);
    // remove this choice to pick another nodeVal for this spot.
    list.remove(list.size() - 1);
  }
}