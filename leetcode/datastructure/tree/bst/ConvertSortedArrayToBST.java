/**
108. Convert Sorted Array to Binary Search Tree
Given an array where elements are sorted in ascending order, convert it to a height balanced BST.

For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

Example:

Given the sorted array: [-10,-3,0,5,9],

One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

      0
     / \
   -3   9
   /   /
 -10  5
 */

/**
Solution: Divide and Conquer. Use mid index as rootNode.
How to Arrive:
* Array is sorted, means that we can use the middle index as the root of BST. So the tree will become balanced BST.
* We calc the mid = start + (end - start) / 2; Create root = nums[mid];
* Then we form them left and right subtrees, and connect to root.left and right.
* Time: O(n);
* Space: O(lgn);
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
class ConvertSortedArrayToBST {

	/**
	Solution: Divide and Conquer. Use mid index as rootNode.
	* Time: O(n);
	* Space: O(lgn); balanced tree
	*/
  public TreeNode sortedArrayToBST(int[] nums) {
    // mid is the root. divide and conquer.
    TreeNode root = helper(nums, 0, nums.length - 1);
    return root;
  }
  
  public TreeNode helper(int[] nums, int start, int end) {
    if (start > end) {
      return null;
    }
    // mid as root
    int mid = start + (end - start) / 2;
    TreeNode root = new TreeNode(nums[mid]);
    // connect mid to left and right
    root.left = helper(nums, start, mid - 1);
    root.right = helper(nums, mid + 1, end);
    return root;
  }
}