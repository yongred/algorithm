/**
103. Linked List Cycle II
Given a linked list, return the node where the cycle begins.

If there is no cycle, return null.

Example
Given -21->10->4->5, tail connects to node index 1，return 10
Explanation：
The last node 5 points to the node whose index is 1, which is 10, so the entrance of the ring is 10

Challenge
Follow up:

Can you solve it without using extra space?
*/

/**
Solution 1: find the loop distance k. 
* after detect the loop using fast and slow pointers.
* If we knows the distance of the loop, then we can have 2 pointers on the same distance iterating on the list. They will meet at the start of the loop.
	* Ex: 1-2-3-4-5-6-7-(5);   loop (5-6-7-(5)) steps from 5 back to 5 is 3 steps; 
	* So, if we have 2 pointers w/ same distance, one from the head. (1), (4); when the head goes from (1) to (5); (4) will be at (5) as well.
	* since they have the same distance.
* Time: O(n) = 2T(n) + T(k)
* Space: O(1);

Solution 2: use the equation, m + k = (#) * c;
![](https://www.geeksforgeeks.org/wp-content/uploads/LinkedListCycle.jpg)
m = distance from head to loopStart,
k = from loopStart to the point slow and fast meet;
c = length of the loop circle.
slow travels = m + k + x(c) 
fast travels = 2(m + k + y(c))
x = how many times slow loop the circle;
y = how many times fast loop the circle;
m + k + x(c) = 2(m + k + y(c)); fast and slow meets
simplify we get m + k = (#) * c;
Which also means **m = (#)c - k**; 
**Key**: That means when we travelled from head to distance (m), travel the circle # times then goes back k steps. *Which is the loop start point*
Time: O(n)
Space: O(1)
*/

/**
 * Definition for ListNode
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

public class LinkedListCycleII {
  /**
   * @param head: The first node of linked list.
   * @return: The node where the cycle begins. if there is no cycle, return null
   * Solution: counting cycle distance
   */
  public ListNode detectCycle(ListNode head) {
    if (head == null) {
      return head;
    }
    ListNode meetNode = nodeMeet(head);
    // no loop
    if (meetNode == null) {
      return null;
    }
    // have loop
    int size = loopSize(meetNode);
    ListNode cur = head;
    for (int i = 0; i < size; i++) {
      cur = cur.next;
    }
    
    while (cur != head) {
      cur = cur.next;
      head = head.next;
    }
    
    return head;
  }
  
  public ListNode nodeMeet(ListNode head) {
    ListNode res = null;
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (fast == slow) {
        res = slow;
        break;
      }
    }
    return res;
  }
  
  public int loopSize(ListNode meetNode) {
    int size = 1;
    ListNode cur = meetNode;
    while (cur.next != meetNode) {
      cur = cur.next;
      size++;
    }
    return size;
  }


  /**
   * @param head: The first node of linked list.
   * @return: The node where the cycle begins. if there is no cycle, return null
   * Solution 2: using equation m = (#)c - k;
   */
  public ListNode detectCycle2(ListNode head) {
    if (head == null) {
      return head;
    }
    ListNode meetNode = nodeMeet(head);
    // no loop
    if (meetNode == null) {
      return null;
    }
    // have loop
    ListNode cur = meetNode;
    
    while (cur != head) {
      cur = cur.next;
      head = head.next;
    }
    
    return head;
  }
}