/**
170. Rotate List
Given a list, rotate the list to the right by k places, where k is non-negative.

Example
Given 1->2->3->4->5 and k = 2, return 4->5->1->2->3.
*/

/**
Solution: Split and reconnect.
How to arrive:
* What we need to do is find the node to split, and connect lastNode to firstNode.
* **Key**: realize k could be > size; In that case we just need to take k == (k % size) rotation.
* Special case is k % size == 0; Which we don't need to rotate. Just return head.
* So we need the SIZE, to determine the actual k rotation.
* We want to get to the *Node just before split* So we can do cur.next = null; cut.
* The steps needed to go from head to Node just before split is totalSteps (size - 1) - (k % size);
* After cutting the list, connect lastNode to firstNode. And we done.
* Time: O(n);
* Space: O(1);
*/

import listpackage.*;
import java.util.*;
import java.io.*;

public class RotateList {
  private ListNode lastNode;
  
  /**
   * @param head: the List
   * @param k: rotate to the right k places
   * @return: the list after rotation
   */
  public ListNode rotateRight(ListNode head, int k) {
    if (head == null || k <= 0) {
      return head;
    }
    
    int length = countAndSetlast(head);
    int steps = length - (k % length) - 1;
    ListNode res = head;
    ListNode cur = head;
    
    if (steps >= length - 1) {
      return res;
    }
    
    for (int i = 0; i < steps; i++) {
      cur = cur.next;
    }
    
    ListNode newHead = cur.next;
    // cut off, cur is newLastNode
    cur.next = null;
    // after rotate, lastnode is behind first(head)
    this.lastNode.next = head;
    res = newHead;
    
    return res;
  }
  
  public int countAndSetlast(ListNode head) {
    int length = 1;
    while (head.next != null) {
      length++;
      head = head.next;
    }
    this.lastNode = head;
    
    return length;
  }
}