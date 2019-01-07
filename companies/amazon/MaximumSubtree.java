
/**
628. Maximum Subtree
Given a binary tree, find the subtree with maximum sum. Return the root of the subtree.

Example
Given a binary tree:

     1
   /   \
 -5     2
 / \   /  \
0   3 -4  -5 
return the node with value 3.

Notice
LintCode will print the subtree which root is your return node.
It's guaranteed that there is only one subtree with maximum sum and the given binary tree is not an empty tree.
*/

/**
Solution: postorder traversal, keep a record of maxRoot and maxSum;
How to Arrive:
* Question ask to find subtree with vals sum > any other subtree.
* A tree can just be one node.
* We can find out the sum of vals by getting the left and right subtree sum, + root.val;
* So postorder traversal is fitting.
* Time: O(n);
* Space: O(lgN);
*/

/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */

public class MaximumSubtree {

  /**
   * @param root: the root of binary tree
   * @return: the maximum weight node
   */
  public TreeNode findSubtree(TreeNode root) {
    // subtree, can be just a leaf node.
    // postorder traversal, calc sum. left + right + root.
    maxRoot = root;
    sumPostOrder(root);
    return maxRoot;
  }
  
  TreeNode maxRoot;
  int maxSum = Integer.MIN_VALUE;
  
  public int sumPostOrder(TreeNode root) {
    if (root == null) {
      return 0;
    }
    // left + right + root. postorder.
    int leftSum = sumPostOrder(root.left);
    int rightSum = sumPostOrder(root.right);
    int sum = root.val + leftSum + rightSum;
    // update maxSum
    if (maxSum < sum) {
      maxRoot = root;
      maxSum = sum;
    }
    return sum;
  }
  
  
}