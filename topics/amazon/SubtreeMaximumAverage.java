/**
597. Subtree with Maximum Average
Given a binary tree, find the subtree with maximum average. Return the root of the subtree.

Example
Given a binary tree:

     1
   /   \
 -5     11
 / \   /  \
1   2 4    -2 
return the node 11.

Notice
LintCode will print the subtree which root is your return node.
It's guaranteed that there is only one subtree with maximum average.
*/

/**
Solution: Postorder traverse, create ResultType of sum, root, size.
How to Arrive:
* We want a subtree of maxAvg. avg = sum / totalNodes;
* So we have to return 2 things, sum and size.
* We can create a class ResultType with sum, size, and root.
* To get the avg of the root, we need root's children left, right's sum and size. 
* Sum = left.sum + right.sum + root.val; Size = left.size + right.size + 1;
* To calc the average, int / int is approximated. 5/2 = 2; same as 4/2=2;
* We can use Double as Sum. And do Double.compare();
* Or we can transform (cursum / size) ><= (maxAvg.sum / maxAvg.size) to (cursum * maxAvg.size) > (maxAvg.sum * size);
Ex: (a/b = c/d) <-> (a * d = c * b); 
9/3 > 8/4; 9 * 4 > 8 * 3;
* We keep a member var ResultType, when new maxAvg occur, update it.
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

public class SubtreeMaximumAverage {
  /**
   * @param root: the root of binary tree
   * @return: the root of the maximum average of subtree
   */
  public TreeNode findSubtree2(TreeNode root) {
    if (root == null) {
      return root;
    }
    helper(root);
    return maxAvg.root;
  }
  
  // result sum, size, root.
  ResultType maxAvg = null;
  
  class ResultType {
    // sum.
    int sum;
    // root.
    TreeNode root;
    // size.
    int size;
    
    ResultType(TreeNode root, int sum, int size) {
      this.root = root;
      this.sum = sum;
      this.size = size;
    }
  }
  
  public ResultType helper(TreeNode root) {
    if (root == null) {
      return new ResultType(null, 0, 0);
    }
    // postorder, left, right, root. get their avg.
    ResultType left = helper(root.left);
    ResultType right = helper(root.right);
    // calc cur root's sum and size.
    ResultType cur = new ResultType(
        root,
        left.sum + right.sum + root.val,
        left.size + right.size + 1
    );
    // compare avg to maxAvg; We can use Double.compare(sum/size, maxsum/maxsize);
    // (cursum / size) ><= (maxAvg.sum / maxAvg.size); Or
    // which is = to (cursum * maxAvg.size) > (maxAvg.sum * size); 
    // a/b = c/d <-> a * d = c * b; 9/3 > 8/4; 9 * 4 > 8 * 3;
    if (maxAvg == null || (cur.sum * maxAvg.size > maxAvg.sum * cur.size)) {
      maxAvg = cur;
    }
    return cur;
  }
  
}