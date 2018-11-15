/**
67. Add Two Numbers
You have two numbers represented by a linked list, where each node contains a single digit. The digits are stored in reverse order, such that the 1's digit is at the head of the list. Write a function that adds the two numbers and returns the sum as a linked list.

Example
Given 7->1->6 + 5->9->2. That is, 617 + 295.

Return 2->1->9. That is 912.

Given 3->1->5 and 5->9->2, return 8->0->8.
*/

/**
Solution: Calc sum of 2 list into res list.
* Consider what we need: Dummy to return, a cur pointer to iterates dummy. Integer carry.
* We calc sum with L1 + L2 + carry. Store the ones decimal to the new Node. Tenth decimal to carry.
* Consider the situation when there is a carryover to new decimal, when the Carry still have val after last calc.
  * Ex: 1->9 + 9->2 = 91 + 29 = 120; 0->2->1; 
  * *Needs to check carry after both list is done adding*
* Time: O(m + n);
* Auxiliary space: O(1) not including the dummy return. O(m + n) if include.
*/

import listpackage.*;
import java.util.*;
import java.io.*;

public class AddTwoNumbers {
    /**
     * @param l1: the first list
     * @param l2: the second list
     * @return: the sum list of l1 and l2 
     */
    public ListNode addLists(ListNode l1, ListNode l2) {
      if (l1 == null) {
        return l2;
      }
      if (l2 == null) {
        return l1;
      }
      ListNode dummy = new ListNode(0);
      ListNode cur = dummy;
      int carry = 0;
      while (l1 != null && l2 != null) {
        int sum = l1.val + l2.val + carry;
        int digit = sum % 10;
        cur.next = new ListNode(digit);
        carry = (sum >= 10) ? 1 : 0;
        cur = cur.next;
        
        if (l1.next == null && l2.next == null && carry > 0) {
          cur.next = new ListNode(carry);
        }
        l1 = l1.next;
        l2 = l2.next;
      }
      // one list finished digit adding.
      ListNode leftover = (l1 == null) ? l2 : l1;
      while (leftover != null) {
        int sum = leftover.val + carry;
        int digit = sum % 10;
        cur.next = new ListNode(digit);
        carry = (sum >= 10) ? 1 : 0;
        cur = cur.next;
        
        if (leftover.next == null && carry > 0) {
          cur.next = new ListNode(carry);
        }
        leftover = leftover.next;
      }
      
      return dummy.next;
    }
}