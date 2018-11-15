/**
102. Linked List Cycle
Given a linked list, determine if it has a cycle in it.

Example
Given -21->10->4->5, tail connects to node index 1, return true

Challenge
Follow up:
Can you solve it without using extra space?
*/

/**
102. Linked List Cycle
Given a linked list, determine if it has a cycle in it.
ex: 1->2->3
					  ^     |
						|      v
						5 <-4
						
Solution: use 2 pointers, 1 fast, 1 slow.
How to arrive:
* cycle meaning it will not points to null.
* It will reatedly run on a part of the list.
* **Key**: which means, if we use 2 pointers, running on diff speed, it will meet at some point. Fast will catch on to slow, like running circular track.
* So we have p1 goes one node at a time, p2 goes 2 nodes at a time.
* When they meet, we have a cycle.
* TIme: O(n);
* Space: O(1);
*/
import listpackage.*;
import java.util.*;
import java.io.*;

public class LinkedlistCycle {
  /**
   * @param head: The first node of linked list.
   * @return: True if it has a cycle, or false
   */
  public boolean hasCycle(ListNode head) {
    if (head == null) {
      return false;
    }
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        return true;
      }
    }
    return false;
  }
}