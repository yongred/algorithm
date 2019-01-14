/**
366. Find Leaves of Binary Tree
Given a binary tree, collect a tree's nodes as if you were doing this: Collect and remove all leaves, repeat until the tree is empty.

Example
Given binary tree:

    1
   / \
  2   3
 / \     
4   5    
Returns [[4, 5, 3], [2], [1]].
*/

/**
Solution: Postorder, find height of the treeNode.
Ex:
    1
   / \
  2   3
 / \     
4   5 
     \ 
      6

Res:[4,6,3], [5], [2], [1]
return height.
1: L(2)= 3, R(3)= 1, 3 > 1; 3+1=4, add to res index3; DONE.
2: L(4)= 1, R(5)=2, 2>1, 2+1 = lv3, add to res index2. ret 3;
4: leaf add to res index0. ret 1;
5: R(6)= 1, 1+1 = lv2, add to res index1; ret 2;
6: leaf, add to res index0, ret 1;
3: leaf, add to res index0, ret 1;

How to arrive:
* The List that a nodeVal is added to is related to how far away the node is from the deepest leaf. In other word, related to its **height**.
* So we can have a function return the height of the tree, then base on the height, determine which list this node should be added to. (height - 1 is the index);
**Base case**:
  * A leafNode have height of 1, and is added to listIndex 1-1 = 0; list0;
**Recursive case**:
  * Find left and right heights.
  * TreeHeight = Max(left or right) + 1;
  * Check if reslist.size < treeHeight:
    * We need to create a new list if res size < height.
  * Then just add the nodeVal to res[treeHeight - 1] list;
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
public class FindLeavesBinaryTree {

  List<List<Integer>> res = new ArrayList<>();
  
  /**
   * @param root: the root of binary tree
   * @return: collect and remove all leaves
   * Solution: Postorder, find height of the treeNode.
   * Time: O(n);
   * Space: O(n);
   */
  public List<List<Integer>> findLeaves(TreeNode root) {
    helper(root);
    return res;
  }
  
  public int helper(TreeNode root) {
    // return the height.
    if (root == null) {
      return 0;
    }
    // leaf
    if (root.left == null && root.right == null) {
      // add this node to index0 list.
      // check if list0 exist.
      if (res.size() == 0) {
        // create new empty list.
        res.add(new ArrayList<>());
      }
      res.get(0).add(root.val);
      // return 1 as leaf height.
      return 1;
    }
    // not a leaf, check height of left and right, get max. Postorder.
    int leftHeight = helper(root.left);
    int rightHeight = helper(root.right);
    int height = Math.max(leftHeight, rightHeight) + 1;
    // check size of res, add to correct index list.
    if (res.size() < height) {
      // create new list
      res.add(new ArrayList<>());
    }
    // add root.val to correct index list. 
    // Its order is determine by how far away from the deepest leaf.
    res.get(height - 1).add(root.val);
    return height;
  }
  
}