/**
106. Convert Sorted List to Binary Search Tree
Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.

Example
               2
1->2->3  =>   / \
             1   3
*/

/**                                         
Solution 1: find mid, divide and conquer.
How I arrive:
* First realize, balanced BST, the root is always the middle node.
* So, that will be true for the main tree, and the sub trees. Which means, we need to find the mid node everytime.
* Find the root, connect to left subtree, connect to right subtree.
* We construct the left and right subtree recursively, returning their root, and connect to their parent root if there is one.
* Ex: [1->2->3->4->5->6]  =>  left[1->2->3]  root(4) right[5->6];
* Time: T(1/2n) * LgN = O(nLgn);
* Space: O(h)
​
Solution 2: Calc size for inorder traverse.
How to arrive:
* To construct a BST, we would like to traverse inorder.
* The linkedList is already left to right.
* **Key**: What we need to do is as we traverse and contructing the subtrees, the LinkedList iterates. Put that ListNode on cur TreeNode.
* Problem: to create a balanced BST, we need to know how to position the nodes correctly. Which is root, which is left and right.
  * **We need to know the size**. If we know the size, we can find out the positions of nodes.
  * And we can do the same with Subtrees. 
  * Ex: Left size is half of total. [1-2-3]-4-5-6;  right size = total - root(1) - left(n/2)  1-2-3-4-[5-6];
  * Leaf is size ==1; and null when size ==0;
* *We are setting the left nodes first, before the parent nodes, then the right nodes*
* We traverse to the leftmost, set it to list.head; Then it's parent, set to head.next; Then it's right sibling, head.next.next;
* Time: O(n); T(n) size counting + T(n) traverse = 2T(n);
* Space: O(h); O(lgN); (N / 2^i);
*/

import listpackage.*;
import java.util.*;
import java.io.*;

public class SortedListToBST {

  public class TreeNode {
    public int val;
    public TreeNode left, right;
    public TreeNode(int val) {
      this.val = val;
      this.left = this.right = null;
    } 
  }

  /*
   * @param head: The first node of linked list.
   * @return: a tree node
   */
  public TreeNode sortedListToBST(ListNode head) {
    if (head == null) {
      return null;
    }
    
    if (head.next == null) {
      return new TreeNode(head.val);
    }
    
    ListNode mid = null;
    ListNode midPrev = null;
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
      midPrev = slow;
      slow = slow.next;
      fast = fast.next.next;
    }
    
    mid = slow;
    // divide list to 2.
    midPrev.next = null;
    
    TreeNode root = new TreeNode(mid.val);
    root.left = sortedListToBST(head);
    root.right = sortedListToBST(mid.next);
    
    return root;
  }


  /**
   * Solution 2: O(n) time; inorder
   */
  private ListNode current;
  /*
   * @param head: The first node of linked list.
   * @return: a tree node
   */
  public TreeNode sortedListToBST2(ListNode head) {
    if (head == null) {
      return null;
    }
    int size = count(head);
    // for iterating inorder
    this.current = head;
    TreeNode root = helper(size);
    
    return root;
  }
  
  public TreeNode helper(int size) {
    if (size <= 0) {
      return null;
    }
    // inorder, left first. Left size is half of total. [1-2-3]-4-5-6
    TreeNode left = helper(size / 2);
    // inorder, root
    TreeNode root = new TreeNode(this.current.val);
    // list is going in inorder direction.
    current = current.next;
    root.left = left;
    // inorder, right. right size = total - root(1) - left(n/2);
    TreeNode right = helper(size - 1 - (size / 2));
    root.right = right;
    
    return root;
  }
  
  public int count(ListNode head) {
    if (head == null) {
      return 0;
    }
    int count = 0;
    while (head != null) {
      count++;
      head = head.next;
    }
    
    return count;
  }
}