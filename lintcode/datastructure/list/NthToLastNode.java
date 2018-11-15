/**
166. Nth to Last Node in List
Find the nth to last element of a singly linked list. 

The minimum number of nodes in list is n.

Example
Given a List  3->2->1->5->null and n = 2, return node  whose value is 1.
*/

/**
Solution 1: count size; or reverse
How to arrive:
* This solution takes countsize: T(size) + T(size - n); or reverse: T(size) + T(n);
* to know how many steps we need from head to nth from last (size - n)th node, We need to know the size.
* After that we take size - n steps from head.
* Time: O(n);
* Space: O(1);

Solution 2: use 2 pointers. Better.
How to arrive:
* The distance from head to nth from last is size - n; Or (size -1) total steps - (n - 1) steps.
	* Ex: 1-2-3-4-(5-6-7) n = 3; target (5); it took n-1 steps from 7 to 5; And it took (totalSteps - (n-1)) steps from 1 to 5;
	* **Key**: we know the val of n-1; So if we took n-1 steps first, that means the distance from here to last is (totalSteps - (n-1)).
	* Now if we have 2 pointers, one goes n-1 steps first. Then have another pointer start from head. When Front pointer reaches lastNode, res pointer reaches target.
* Time: O(n) = T(size - n);
* Space: O(1);
*/

import listpackage.*;
import java.util.*;
import java.io.*;

public class NthToLastNode {
  /*
   * @param head: The first node of linked list.
   * @param n: An integer
   * @return: Nth to last node of a singly linked list. 
   */
  public ListNode nthToLast(ListNode head, int n) {
    if (head == null || n <= 0) {
      return null;
    }
    ListNode reverseHead = reverse(head);
    for (int i = 0; i < n - 1; i++) {
      reverseHead = reverseHead.next;
    }
    return reverseHead;
  }
  
  public ListNode reverse(ListNode head) {
    if (head == null) {
      return null;
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


  /*
   * @param head: The first node of linked list.
   * @param n: An integer
   * @return: Nth to last node of a singly linked list. 
   * Solution: better solution, using 2 pointers.
   */
  public ListNode nthToLast2(ListNode head, int n) {
    if (head == null || n <= 0) {
      return null;
    }
    ListNode res = head;
    ListNode forward = head;
    // move forward n - 1 distance away from res.
    for (int i = 0; i < n - 1; i++) {
      forward = forward.next;
    }
    // when forward reaches last node
    while (forward.next != null) {
      res = res.next;
      forward = forward.next;
    }
    
    return res;
  }
}