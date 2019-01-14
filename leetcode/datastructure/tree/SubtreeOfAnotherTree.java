/**
572. Subtree of Another Tree
Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. The tree s could also be considered as a subtree of itself.

Example 1:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
Given tree t:
   4 
  / \
 1   2
Return true, because t has the same structure and node values with a subtree of s.
Example 2:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
    /
   0
Given tree t:
   4
  / \
 1   2
Return false.
*/

/**
Solution: Check if s and t is same tree, if not check s.left or s.right == t.
How to Arrive:
* To check if T is == S subtree, we need a helper function to check if 2 trees are the same.
* So we first implement SameTree helper function to check if 2 trees are the same, Using any order traverse. (preorder faster, compare root.val first);
* Now, we just need to check if S is same tree as T, if not, we check S.left or S.right is same tree as T.
* Time: O(S * T);
* Space: O(S);
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
class SubtreeOfAnotherTree {

  /**
  Solution: Check if s and t is same tree, if not check s.left or s.right == t.
  * Time: O(S * T);
  * Space: O(S);
  */
  public boolean isSubtree(TreeNode s, TreeNode t) {
    if (s == null && t == null) {
      return true;
    }
    if (s == null || t == null) {
      return false;
    }
    // check for same tree.
    if (sameTree(s, t)) {
      return true;
    }
    // not same root val, check S tree's left and right
    if (isSubtree(s.left, t)) {
      return true;
    }
    // right check
    return isSubtree(s.right, t);
  }
  
  public boolean sameTree(TreeNode s, TreeNode t) {
    // both are null
    if (s == null && t == null) {
      return true;
    }
    // only one is null
    if (s == null || t == null) {
      return false;
    }
    // preorder.
    // compare root val, And if left and right are same.
    return (s.val == t.val) && sameTree(s.left, t.left)
        && sameTree(s.right, t.right);
  }
  
  
}