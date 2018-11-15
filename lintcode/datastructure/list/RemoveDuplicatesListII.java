/**
113. Remove Duplicates from Sorted List II
Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

Example
Given 1->2->3->3->4->4->5, return 1->2->5.
Given 1->1->1->2->3, return 2->3.
*/


/**
Solution:
How I arrive:
* Realizing that we are Skipping over duplicates, just connecting unique ones.
* **Key**: It is helpful to have a Dummy, to connect to all unqiue nodes.
	* Which means we need a iteration of UniqueNode = Dummy. And as we find next Uniq, UniqueNode.next = newUniq;
* How to differentiates between unique of dups?
	* **Option 1**: have a Count. if (count == 1) then UniqueNode will connect to it, and iterates. 
	* Otherwise, count > 1, UniqueNode points to nextVal or NULL. either is fine.
	* **Option 2**: have 2 pointers, cur and compareNode. Basically cur point at 1st of the val, compareNode goes forward to see if it's same.
	* *Use cur ><= compareNode.next* b/c if cur == compareNode, compareNode didn't move then we know it's Unique.
	* Then do the same with UniqueNode connect to new Uniq.
* Time: O(n);
* Space: O(1);
*/

import listpackage.*;
import java.util.*;
import java.io.*;

public class RemoveDuplicatesListII {
  /**
   * @param head: head is the head of the linked list
   * @return: head of the linked list
   * Solution: count and add to dummy.
   */
  public ListNode deleteDuplicates(ListNode head) {
    if (head == null) {
      return null;
    }
    ListNode dummy = new ListNode(0);
    ListNode newHead = dummy;
    ListNode cur = head;
    
    while (cur != null) {
      int count = 1;
      // loop till next is not same
      while (cur.next != null && cur.val == cur.next.val) {
        // count cur val.
        count++;
        cur = cur.next;
      }
      // cur at last node of same val.
      if (count == 1) {
        // if cur is unique
        newHead.next = cur;
        newHead = newHead.next;
      } else {
        // cut off duplicates
        newHead.next = null;
      }
      // iterates, count next val
      cur = cur.next;
    }
    return dummy.next;
  }

  /**
   * @param head: head is the head of the linked list
   * @return: head of the linked list
   */
  public ListNode deleteDuplicates2(ListNode head) {
    if (head == null) {
      return null;
    }
    ListNode dummy = new ListNode(0);
    ListNode uniqueNode = dummy;
    ListNode cur = head;
    ListNode compare = head;
    
    while (cur != null) {
      // loop until compare.next is diff from cur.
      while (compare.next != null && cur.val == compare.next.val) {
        compare = compare.next;
      }
      // compare didn't move, meaning unique node.
      if (cur == compare) {
        uniqueNode.next = cur;
        // cur unique is decided, iter to curUnique, waiting for next unique.
        uniqueNode = uniqueNode.next;
      } else {
        // skip duplicates node. Determines next val.
        // don't iterates uniqueNode yet, nextVal may/may not be unique.
        uniqueNode.next = compare.next;
      }
      cur = compare.next;
      compare = compare.next;
    }
    return dummy.next;
  }
}