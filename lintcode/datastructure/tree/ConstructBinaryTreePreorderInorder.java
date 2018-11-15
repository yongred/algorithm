/**
73. Construct Binary Tree from Preorder and Inorder Traversal
Given preorder and inorder traversal of a tree, construct the binary tree.

Example
Given in-order [1,2,3] and pre-order [2,1,3], return a tree:

  2
 / \
1   3
Notice
You may assume that duplicates do not exist in the tree.
*/

/**
Solution 1: Recursive divide, Preorder first num is the ROOT. Inorder index of Root can divide into left and right branches.
* Key: Notice **Preorder 1st num is the ROOT**
* Key: Notice **Inorder root index is always between left and right** branch.
* Use the above infos we can divide the tree into subtrees, and **Divide and Conquer.**
* Doing the same with each sub tree. **Connect Sub Branch to upper root**
* Params Needed: inorder StartIndex, endIndex; Preorder startIndex(root), endIndex. Search for inorder rootIndex.
* Problem: how do we know the **middle index** (between left and right) of Preorder. Need it for determine next preStartIndex.
* **Way #1**: realize # of nodes in left and right nodes in Preorder and InOrder are the same.
	* Key: **inorderRootIndex - inStart = left nodes #** we can use that num to find out Preorder middle index.
	* preStart + left# = last left node on Pre, (middle part); [root, l1, l2, l3, r1..] 0 + 3 = 3 = l3;
	* recurseLeft(inorder[inStart, inRoot-1], Preorder[postStart+1, postStart+left#Nodes]); 
	* recurseRight(inorder[inRoot+1, inEnd], Preorder[postStart+left#Nodes+1, postEnd]);
* **Way #2**: realize PreStart+1 is always the Preorder next left sub branch's root.
	* We can keep a **Static Index (define a object class)** of where the the Preorder cur root index is. And we Increment it and each recursive call.
	* To do that we need to start with the Left branch (root.left = func()) first.
	* That way when we get to the right, the Static Index is already at the correct position.
	* **Don't need postStart**
	* Space: O(N) recursive Tree Height. Worst case height = nodes;
	* Time: O(N^2); Search for rootIndex every time.

Solution 2: Optimize with HashMap.
* We are searching for inRootIndex every time.
* replace the seach method with HashMap, storing all positions in inorder.
* Space: O(N);
*/

import treepackage.*;
import java.util.*;
import java.io.*;

public class ConstructBinaryTreePreorderInorder {
  /**
   * @param inorder: A list of integers that inorder traversal of a tree
   * @param postorder: A list of integers that postorder traversal of a tree
   * @return: Root of a tree
   */
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    if (inorder.length != preorder.length) {
      return null;
    }
    
    TreeNode root = construct(inorder, preorder, 0, inorder.length - 1,
        0, preorder.length - 1);
    
    return root;
  }
  
  public TreeNode construct(int[] inorder, int[] preorder, 
      int inStart, int inEnd, int preStart,
      int preEnd) {
    if (inStart > inEnd) {
      return null;
    }
    
    TreeNode root = new TreeNode(preorder[preStart]);

    if (inStart == inEnd) {
      return root;
    }
    
    int inRoot = searchIndex(inorder, root.val, inStart, inEnd);
    // left branch node count
    int leftCount = inRoot - inStart;
    root.left = construct(inorder, preorder, inStart, inRoot - 1,
        preStart + 1, preStart + leftCount);
    root.right = construct(inorder, preorder, inRoot + 1, inEnd,
        preStart + leftCount + 1, preEnd);
    
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
  public TreeNode buildTree2(int[] preorder, int[] inorder) {
    if (inorder.length != preorder.length) {
      return null;
    }
    HashMap<Integer, Integer> inPos = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
      inPos.put(inorder[i], i);
    }
    Index preIndex = new Index(0);
    TreeNode root = construct2(inorder, preorder, inPos, 0, inorder.length - 1,
        preIndex);
    
    return root;
  }
  
  public TreeNode construct2(int[] inorder, int[] preorder, 
      HashMap<Integer, Integer> inPos, int inStart, int inEnd,
      Index preIndex) {
    if (inStart > inEnd) {
      return null;
    }
    
    TreeNode root = new TreeNode(preorder[preIndex.index]);
    preIndex.index++;
    if (inStart == inEnd) {
      return root;
    }
    
    int inRoot = inPos.get(root.val);
    root.left = construct2(inorder, preorder, inPos, inStart, inRoot - 1,
        preIndex);
    root.right = construct2(inorder, preorder, inPos, inRoot + 1, inEnd,
        preIndex);
    
    return root;
  }
  
  public class Index {
    public int index;
    
    public Index(int index) {
      this.index = index;
    }
  }
}