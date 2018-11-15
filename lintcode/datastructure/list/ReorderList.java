/**
99. Reorder List
Given a singly linked list L: L0 → L1 → … → Ln-1 → Ln

reorder it to: L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → …

Example
Given 1->2->3->4->null, reorder it to 1->4->2->3->null.

Challenge
Can you do this in-place without altering the nodes' values?
*/

/**
Solution: See it as 2 list, merge into one. And list2 needs to reverse first.
How to arrive:
* First realize the list is being divided into first halft and sec half.
* And first half will merge with the reverse of 2nd half.
* **Key**: So we need to find the middle. And cut them into 2 list.
* reverse the 2nd List.
  * Using prev, cur, oldNext; prev init  = null; prev <- cur, cur = oldNext;
  * *Remember to return the lastNode as head2*
* Merge the head1 and head2;
  * **Key**: take turns to iterates, term1: firstHead -> secHead,  term2: secHead -> firstHead, term3: firstHead -> secHead ...
  * Ex: head1: 1->2->3; head2: 5->4;  1->5; 5->2; 2->4; 3->4;
* Time: O(n)
* Auxiliary space: O(1);
*/

import listpackage.*;
import java.util.*;
import java.io.*;

public class ReorderList {

    /**
     * @param head: The head of linked list.
     * @return: nothing
     * Solution: Reverse sec half list
     * Better space. No need for stack
     * Time: O(n)
     * space: O(1)
     */
    public void reorderList(ListNode head) {
      if (head == null) {
        return;
      }
      ListNode slow = head;
      ListNode fast = head.next;
      // get slow to mid
      while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
      }
      ListNode mid = slow;
      // reverse the sec half, Ln -> Ln-1 ...
      ListNode head2 = reverse(mid.next);
      // divide into 2 list
      mid.next = null;
      merge(head, head2);
    }
    
    public void merge(ListNode head1, ListNode head2) {
      if (head1 == null && head2 == null) {
        return;
      }
      boolean term1 = true; // true head1, false head2
      
      while (head1 != null && head2 != null) {
        if (term1) {
          ListNode oldNext1 = head1.next;
          head1.next = head2;
          // iterates
          head1 = oldNext1;
        } else {
          ListNode oldNext2 = head2.next;
          head2.next = head1;
          head2 = oldNext2;
        }
        term1 = !term1;
      }
    }
    
    public ListNode reverse(ListNode head) {
      if (head == null) {
        return head;
      }
      ListNode prev = null;
      ListNode cur = head;
      
      while (cur != null) {
        ListNode oldNext = cur.next;
        cur.next = prev;
        prev = cur;
        cur = oldNext;
      }
      return prev;
    }

    /**
     * @param head: The head of linked list.
     * @return: nothing
     * Solution: store sec half to Stack
     * Time: O(n)
     * Space: O(n)
     */
    public void reorderList2(ListNode head) {
      if (head == null) {
        return;
      }
      Stack<ListNode> stack = new Stack<>();
      ListNode slow = head;
      ListNode fast = head.next;
      
      while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
      }
      ListNode mid = slow;
      // slow in the mid
      while (slow.next != null) {
        stack.push(slow.next);
        slow = slow.next;
      }
      // divide into 2 lists, then connect
      mid.next = null;
      ListNode cur = head;
      while (cur != null && !stack.isEmpty()) {
        ListNode newNext = stack.pop();
        ListNode curNext = cur.next;
        cur.next = newNext;
        newNext.next = curNext;
        cur = curNext;
      }
    }
}