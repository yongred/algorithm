/**
Solution: Iterative or Recursive; either Traversal inorder, preorder, postorder.
How to Arrive:
* To know a binary tree is same order or not, just traverse the same way and compare each node.
* **Key**: Things to compare: Null, val.
* To do it Iteratively, we can use 2 Stack. 1 for each tree. LIFO
* There are 2 operations, when we traverse, we either 1*compare val* or  0 *traverse children*, operations.
	* We can create a new class for having members of operation, and Node.
	* **Key**: before checking operation, Compare and check for NULL first to avoid NullPointer. If one is null, other is not, return false.
	* ope = 1, we compare vals, and return false if not ==.
	* ope = 0; Add both node childrens in the same order to Stack1 ans Stack2.
* Time: O(n);
* Space: O(n);
*/

/**
Solution: Iterative or Recursive; either Traversal inorder, preorder, postorder.
How to Arrive:
* To know a binary tree is same order or not, just traverse the same way and compare each node.
* **Key**: Things to compare: Null, val.
* To do it Iteratively, we can use 2 Stack. 1 for each tree. LIFO
* There are 2 operations, when we traverse, we either 1*compare val* or  0 *traverse children*, operations.
	* We can create a new class for having members of operation, and Node.
	* **Key**: before checking operation, Compare and check for NULL first to avoid NullPointer. If one is null, other is not, return false.
	* ope = 1, we compare vals, and return false if not ==.
	* ope = 0; Add both node childrens in the same order to Stack1 ans Stack2.
* Time: O(n);
* Space: O(n);
*/

import treepackage.*;
import java.util.*;
import java.io.*;

public class SameTree {

  /**
   * @param a: the root of binary tree a.
   * @param b: the root of binary tree b.
   * @return: true if they are identical, or false.
   */
  public boolean isIdentical(TreeNode a, TreeNode b) {
    Stack<Guide> stack1 = new Stack<>();
    Stack<Guide> stack2 = new Stack<>();
    stack1.push(new Guide(0, a));
    stack2.push(new Guide(0, b));
    // since we pushing null nodes, each level will be same size.
    // ex: 3 # 4 #,  # # # #; As we compare the null to node, the comparison will end at this level.
    // no need to worry about 1 is empty, 1 is not empty.
    while (!stack1.isEmpty() && !stack2.isEmpty()) {
      Guide guide1 = stack1.pop();
      Guide guide2 = stack2.pop();
      // compare if node is null. 
      if (guide1.node == null || guide2.node == null) {
        if (guide1.node != null || guide2.node != null) {
          return false;
        } else {
          continue;
        }
      }
      
      if (guide1.ope == 1 && guide2.ope == 1) {
        if (guide1.node.val != guide2.node.val) {
          return false;
        }
      } else {
        stack1.push(new Guide(0, guide1.node.right));
        stack2.push(new Guide(0, guide2.node.right));
        stack1.push(new Guide(0, guide1.node.left));
        stack2.push(new Guide(0, guide2.node.left));
        stack1.push(new Guide(1, guide1.node));
        stack2.push(new Guide(1, guide2.node));
      }
    }
    
    return true;
  }
  
  public class Guide {
    public int ope; // 0 visit, 1 print
    public TreeNode node;
    
    public Guide(int ope, TreeNode node) {
      this.ope = ope;
      this.node = node;
    }
  }

  /**
   * @param a: the root of binary tree a.
   * @param b: the root of binary tree b.
   * @return: true if they are identical, or false.
   * Solution: recursive
   */
  public boolean isIdentical2(TreeNode a, TreeNode b) {
    if (a == null && b == null) {
      return true;
    }
    // if only one of them is null
    if (a == null || b == null) {
      return false;
    }
    
    if (a.val == b.val) {
      return isIdentical2(a.left, b.left) && isIdentical2(a.right, b.right);
    } else {
      return false;
    }
  }

  
}