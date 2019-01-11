/**
144. Binary Tree Preorder Traversal
Given a binary tree, return the preorder traversal of its nodes' values.

Example:

Input: [1,null,2,3]
   1
    \
     2
    /
   3

Output: [1,2,3]
Follow up: Recursive solution is trivial, could you do it iteratively?
*/

/**
Solution 1: iterative, helper class of ope: visit or print.
When print, just print. When visit, becomes 3 operations.
* [1,2,3,4,5] => [1,2,4,5,3] root, left, right
* How to arrive the answer:
*
* Realize there are 2 posible operations: print or visit;
* When visit, it becomes 3 calls -> print self, visit left, visit right;
* PROBLEM: what data structure to use that will allows me to print 4 first, despite 3 being in the data first.
* KEY: use a Stack. LIFO. When I visit node 2, the stack is [v2->push(p2, v4, v5), v3];
* Ex: I can push 4,5 to be pop first, despite node 3 being there first.
* 
* Time: O(N), visit nodes once.
* Space: O(N);
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
class BinaryTreePreorderTraversal {
  
  /**
   * Solution 1: iterative, helper class of ope: visit or print.
   * Time: O(n)
   * Space: O(lgN) average, O(n) in worst.
   */
  public List<Integer> preorderTraversal(TreeNode root) {
    // reslist of root, left, right
    List<Integer> res = new ArrayList<>();
    // iterative with stack.
    Stack<Guide> stack = new Stack<>();
    // add root to traverse preOrder. root, left, right
    stack.push(new Guide(root, 0));
    while (!stack.isEmpty()) {
      Guide guide = stack.pop();
      // check null
      if (guide.node == null) {
        continue;
      }
      // check traverse or print
      if (guide.ope == 1) {
        // print/add
        res.add(guide.node.val);
      } else {
        // traverse/visit, root, left, right. Stack LIFO.
        stack.push(new Guide(guide.node.right, 0));
        stack.push(new Guide(guide.node.left, 0));
        stack.push(new Guide(guide.node, 1));
      }
    }
    // return reslist
    return res;
  }
  
  class Guide {
    TreeNode node;
    int ope; // 0 traverse, 1 print/add
    
    Guide(TreeNode node, int ope) {
      this.node = node;
      this.ope = ope;
    }
  }
  
  /**
   * Solution 2: recursive
   * Time: O(n)
   * Space: O(lgN) average, O(n) worst
   */
  List<Integer> res = new ArrayList<>();
  
  public List<Integer> preorderTraversal(TreeNode root) {
    preorder(root);
    return res;
  }
  
  public void preorder(TreeNode root) {
    if (root == null) {
      return;
    }
    // root, left, right
    res.add(root.val);
    preorder(root.left);
    preorder(root.right);
  }
  
}