/**
109. Convert Sorted List to Binary Search Tree
Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.

For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

Example:

Given the sorted linked list: [-10,-3,0,5,9],

One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:

      0
     / \
   -3   9
   /   /
 -10  5
*/

/**
Solution 1: find mid using 2 pointers, divide and conquer.
* Time: O(nlgn)
* Space: O(lgn) balanced tree

Solution 2: convert list to sorted array, using divide and conquer find mid as root.
* Time: O(n);
* Space: O(n); Array size.
*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
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
class ConvertSortedListToBST {

	/**
	 * Solution 1: find mid using 2 pointers, divide and conquer.
	 * Time: O(nlgn)
	 * Space: O(lgn) balanced tree
	 */
  public TreeNode sortedListToBST(ListNode head) {
    // find mid of list using 2 pointers, then use mid as root.
    return helper(head);
  }
  
  public TreeNode helper(ListNode head) {
    if (head == null) {
      return null;
    }
    // find mid
    ListNode slow = head;
    ListNode fast = head.next;
    ListNode prev = null;
    while (fast != null && fast.next != null) {
      prev = slow;
      slow = slow.next;
      fast = fast.next.next;
    }
    ListNode mid = slow;
    // disconnect list on mid. Split into 2 list head->mid-1; mid+1->end;
    if (prev != null) {
      prev.next = null;
    } else {
      head = null;
    }
    ListNode right = mid.next;
    mid.next = null;
    // mid = root
    TreeNode root = new TreeNode(mid.val);
    root.left = helper(head);
    root.right = helper(right);
    return root;
  }
  
  
}