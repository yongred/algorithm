/**
921. Count Univalue Subtrees
Given a binary tree, count the number of uni-value subtrees.

A Uni-value subtree means all nodes of the subtree have the same value.

Example
Given root = {5,1,5,5,5,#,5}, return 4.

              5
             / \
            1   5
           / \   \
          5   5   5
*/

/**
Solution: Postorder traverse. Find left and right is Unival, then compare root.val to their vals.
How to Arrive:
* Univalue subtree is a subtree where all its nodes have same val.
* A leaf node is a unival subtree.
**2 conditions**:
  * In order to be a unival tree, your children left, right have to be Univals. (All left vals are same, and all right values are same);
  * And root.val == left.val == right.val; (all tree vals are same).
  * Meet these 2 conditions, then all nodes vals are same.
**Base case**: 
  * Root = null, return false. No val.
  * If is Leaf, Count + 1, return true;
* We use postorder to check if left and right are univals first.
**Key**: check if subtree is null first, before compare val and check leftUnival is true. B/c we want to ignore null subtree, we only interested in nodes that have vals.
* Then we compare if left.val = root.val = right.val.
* If both condition meets, Count + 1;
* Time: O(n);
* Space: O(n);
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

public class CountUnivalueSubtrees {
  private int count = 0;
  
  /**
   * @param root: the given tree
   * @return: the number of uni-value subtrees.
   * Solution: Postorder traverse. Find left and right is Unival, then compare root.val to their vals.
   * Time: O(n);
   * Space: O(n);
   */
  public int countUnivalSubtrees(TreeNode root) {
    helper(root);
    return count;
  }
  
  public boolean helper(TreeNode root) {
    if (root == null) {
      return false;
    }
    // leaf
    if (root.left == null && root.right == null) {
      // leaves are Univalue subtree.
      count++;
      return true;
    }
    // not leaf. Check left and right, both have to be unival and vals == root.val.
    // postorder, check left and right first before compare val with root.
    boolean leftUnival = helper(root.left);
    boolean rightUnival = helper(root.right);
    // ignore null subtree.
    if (root.left != null) {
      if (root.left.val != root.val || !leftUnival) {
        return false;
      }
    }
    if (root.right != null) {
      if (root.right.val != root.val || !rightUnival) {
        return false;
      }
    }
    // left = right = root. And left and right is unival.
    count++;
    return true;
  }
  
  
}