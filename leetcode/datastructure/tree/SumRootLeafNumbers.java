/**
129. Sum Root to Leaf Numbers
Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.

An example is the root-to-leaf path 1->2->3 which represents the number 123.

Find the total sum of all root-to-leaf numbers.

Note: A leaf is a node with no children.

Example:

Input: [1,2,3]
    1
   / \
  2   3
Output: 25
Explanation:
The root-to-leaf path 1->2 represents the number 12.
The root-to-leaf path 1->3 represents the number 13.
Therefore, sum = 12 + 13 = 25.
Example 2:

Input: [4,9,0,5,1]
    4
   / \
  9   0
 / \
5   1
Output: 1026
Explanation:
The root-to-leaf path 4->9->5 represents the number 495.
The root-to-leaf path 4->9->1 represents the number 491.
The root-to-leaf path 4->0 represents the number 40.
Therefore, sum = 495 + 491 + 40 = 1026.
*/

/**
Solution: DFS, preorder.
How to Arrive:
* Question ask for every root-to-leaf paths. So DFS. 
* Make each path a Num, nodeVal as digits 0-9;
* So we basically just need to traverse down to each leaf, and keep a pathNum from all the nodes(digits) in path. Add each digit as we go down. 
`pathNum = pathNum * 10 + root.val;`
* Update resSum when reached to a leaf.
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
class SumRootLeafNumbers {
  
  /**
  Solution: DFS, preorder.
  * Time: O(n);
  * Space: O(n);
  */
  private int resSum = 0;
  
  public int sumNumbers(TreeNode root) {
    helper(root, 0);
    return resSum;
  }
  
  public void helper(TreeNode root, int pathNum) {
    if (root == null) {
      return;
    }
    // leaf node
    if (root.left == null && root.right == null) {
      // left shift a decimal, add digit. 0-9; no negative.
      pathNum = pathNum * 10 + root.val;
      // resSum update.
      resSum += pathNum;
      return;
    }
    // preorder, root left right
    // update pathNum, add new digit.
    pathNum = pathNum * 10 + root.val;
    helper(root.left, pathNum);
    helper(root.right, pathNum);
  }
  
}