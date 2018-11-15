/**
72. Construct Binary Tree from Inorder and Postorder Traversal
Given inorder and postorder traversal of a tree, construct the binary tree.

Example
Given inorder [1,2,3] and postorder [1,3,2], return a tree:

  2
 / \
1   3
Notice
You may assume that duplicates do not exist in the tree.
*/

/**
Inorder and Postorder arrs becomes, a tree; 
Given inorder [1,2,3] and postorder [1,3,2], return a tree:
  2
 / \
1   3
You may assume that duplicates do not exist in the tree.
inorder: left, root, right;   postorder: left, right, root.

Solution 1: Recursive divide, Postorder last num is the ROOT. Inorder index of Root can divide into left and right branches.
* Key: Notice **Postorder last num is the ROOT**
* Key: Notice **Inorder root index is always between left and right** branch.
* Use the above infos we can divide the tree into subtrees, and **Divide and Conquer.**
* Doing the same with each sub tree. **Connect Sub Branch to upper root**
* Params Needed: inorder StartIndex, endIndex; postOrder startIndex, endIndex (root). Search for inorder rootIndex.
* Problem: how do we know the **middle index** (between left and right) of postOrder. Need it for determine next postEndIndex.
* **Way #1**: realize # of nodes in left and right nodes in postOrder and InOrder are the same.
	* Key: **inorderRootIndex - inStart = left nodes #** we can use that num to find out postOrder middle index.
	* postStart + left# - 1 = last left node on post, (middle part)
	* recurseLeft(inorder[inStart, inRoot-1], postorder[postStart, postStart+left#Nodes-1]); 
	* recurseRight(inorder[inRoot+1, inEnd], postorder[postStart+left#Nodes, postEnd-1]);
* **Way #2**: realize postEnd-1 is always the postorder next right sub branch's root.
	* We can keep a **Static Index** of where the the postorder cur root index is. And we Decrement it and each recursive call.
	* To do that we need to start with the Right branch (root.right = func()) first.
	* That way when we get to the left, the Static Index is already at the correct position.
	* **Don't need postStart**
	* Space: O(N) recursive Tree Height. Worst case height = nodes;
	* Time: O(N^2); Search for rootIndex every time.

Solution 2: Optimize with HashMap.
* We are searching for inRootIndex every time.
* replace the seach method with HashMap, storing all positions in inorder.
* Space: O(N);
* Time: O(N);
*/

import treepackage.*;
import java.util.*;
import java.io.*;

public class ConstructBinaryTreeInorderPostorder {
	/**
	 * @param inorder: A list of integers that inorder traversal of a tree
   * @param postorder: A list of integers that postorder traversal of a tree
   * @return: Root of a tree
   */
  public TreeNode buildTree(int[] inorder, int[] postorder) {
    if (inorder.length != postorder.length) {
      return null;
    }
    TreeNode res = construct(inorder, postorder, 0, inorder.length - 1,
        0, postorder.length - 1);
        
    return res;
  }
  
  public TreeNode construct(int[] inorder, int[] postorder, int inStart,
    int inEnd, int poStart, int poEnd) {
    if (inEnd < inStart) {
      return null;
    }    
    if (inEnd == inStart) {
      return new TreeNode(inorder[inStart]);
    }
    
    TreeNode root = new TreeNode(postorder[poEnd]);
    int inRoot = searchIndex(inorder, root.val, inStart, inEnd);
    // find left branch size, # of left and right nodes are the same in
    // inorder and postorder.
    int leftCount = inRoot - inStart;
    // pStart + leftCount = 1st right node; 1st right -1 = last left node.
    root.left = construct(inorder, postorder, inStart, inRoot - 1, poStart,
        poStart + leftCount - 1);
    root.right = construct(inorder, postorder, inRoot + 1, inEnd,
        poStart + leftCount, poEnd - 1);
    
    return root;
  }
  
  public int searchIndex(int[] arr, int val, int start, int end) {
    if (arr == null || arr.length == 0) {
      return -1;
    }
    
    for (int i = start; i <= end; i++) {
      if (arr[i] == val) {
        return i;
      }
    }
    
    return -1;
  }

  /**
   * @param inorder: A list of integers that inorder traversal of a tree
   * @param postorder: A list of integers that postorder traversal of a tree
   * @return: Root of a tree
   */
  public TreeNode buildTree2(int[] inorder, int[] postorder) {
    if (inorder.length != postorder.length) {
      return null;
    }
    
    HashMap<Integer, Integer> inPos = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
      inPos.put(inorder[i], i);
    }
    Index poIndex = new Index(postorder.length - 1);
    TreeNode res = construct2(inorder, postorder, inPos, 0, inorder.length - 1,
        poIndex);
        
    return res;
  }
  
  public TreeNode construct2(int[] inorder, int[] postorder, 
      HashMap<Integer, Integer> inPos, int inStart, int inEnd, Index poIndex) {
    if (inEnd < inStart) {
      return null;
    }    
    
    TreeNode root = new TreeNode(postorder[poIndex.index]);
    int inRoot = inPos.get(root.val);
    poIndex.index--;
    
    if (inEnd == inStart) {
      return root;
    }
    
    // right first, poIndex-- till reach middle, left.
    root.right = construct2(inorder, postorder, inPos, inRoot + 1, inEnd,
        poIndex);
    // right branch is constructed, poIndex in middle.
    root.left = construct2(inorder, postorder, inPos, inStart, inRoot - 1,
        poIndex);
    
    return root;
  }
  
  public class Index {
    public int index;
    
    public Index(int index) {
      this.index = index;
    }
  }
}