/**
165. Merge Two Sorted Lists
Merge two sorted (ascending) linked lists and return it as a new sorted list. The new sorted list should be made by splicing together the nodes of the two lists and sorted in ascending order.

Example
Given 1->3->8->11->15->null, 2->null , return 1->2->3->8->11->15->null.
*/

/**
Solution: Compare and add to dummy.
How I arrive:
* Merging 2 list to one. A dummy will help alot.
* we need to figure out the smallest next node, it's either on L1 or L2; dummy we connect the smaller one after compare.
* Iterates the list that is picked this time. Then compare again.
* Since all nodes need to be added to dummy, no need to disconnect any nodes. it will be connected to next smallest.
	* Ex: Think of it like traversing between 2 lists.
	* 1->2  8
				/    /  \
		 3->6    9->10
* Time: O(n);
* Space: O(1);
*/

import listpackage.*;
import java.util.*;
import java.io.*;

public class MergeTwoSortedList {
  /**
   * @param l1: ListNode l1 is the head of the linked list
   * @param l2: ListNode l2 is the head of the linked list
   * @return: ListNode head of linked list
   */
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null) {
      return l2;
    }
    if (l2 == null) {
      return l1;
    }
    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;
    
    while (l1 != null && l2 != null) {
      if (l1.val <= l2.val) {
        cur.next = l1;
        l1 = l1.next;
      } else {
        cur.next = l2;
        l2 = l2.next;
      }
      cur = cur.next;
    }
    
    ListNode nodeLefts = (l1 == null) ? l2 : l1;
    cur.next = nodeLefts;
    
    return dummy.next;
  }
}