/**
112. Remove Duplicates from Sorted List
Given a sorted linked list, delete all duplicates such that each element appear only once.

Example
Given 1->1->2, return 1->2.
Given 1->1->2->3->3, return 1->2->3.
*/

/**
Solution: connect to the next unique val.
How I arrive:
* list is sorted, meaning all duplicates are together. 
* Therefore, we can just connect the cur node to the next node w/ diff val.
* **Key**: only iterates when next node is unique. Otherwise point the next to next.next node;
* No need to change duplicate nodes' pointer, our list will only connect to the unique ones.
* Time: O(n);
* Space: O(1);
*/

import listpackage.*;
import java.util.*;
import java.io.*;

public class RemoveDuplicatesList {
  /**
   * @param head: head is the head of the linked list
   * @return: head of linked list
   */
  public ListNode deleteDuplicates(ListNode head) {
    if (head == null) {
      return head;
    }
    ListNode prev = head;
    ListNode cur = head.next;
    
    while (cur != null) {
      if (prev.val == cur.val) {
        // connect to next node, del cur. prev is still this one
        prev.next = cur.next;
      } else {
        // if not equal, prev iterates
        prev = cur;
      }
      cur = cur.next;
    }
    return head;
  }

  /**
   * @param head: head is the head of the linked list
   * @return: head of linked list
   */
  public ListNode deleteDuplicates2(ListNode head) {
    if (head == null) {
      return null;
    }
    ListNode cur = head;
    while (cur.next != null) {
      // not iterating, when we find dupl.
      if (cur.val == cur.next.val) {
        // connect to next.next one, abandon next
        cur.next = cur.next.next;
      } else {
        // iterates when unique
        cur = cur.next;
      }
    }
    
    return head;
  }
}