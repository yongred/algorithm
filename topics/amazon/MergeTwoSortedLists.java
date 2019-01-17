/**
21. Merge Two Sorted Lists
Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.

Example:

Input: 1->2->4, 1->3->4
Output: 1->1->2->3->4->4
*/

/**
Solution 1: Use a dummy to connect smallerVal nodes from l1 or l2.
How to Arrive:
* Have a DummyNode to connect to new merged list.
* Use a curNode for pointer. Init curNode = Dummy;
* compare l1 to l2, smallerNode assign to curNode.next;
* When l1 or l2 node = null (finished); just connect curNode.next to the otherNode.
* Return DummyNode.next;
* Time: O(n)
* Space: O(1)

Solution 2: Recursive, choose between 2 nodes, connect to the recursive rest, return choosedNode.
How to Arrive:
* What we are basically doing is choosing a Node between l1 and l2, Then use choosedNode to connect to next.
* The Next is choosing between the prevChoosedNode.next and NotChoosedNode.
* Until one of the Node = null, empty, Then we just go for the other node, return that node.
* Time: O(n + m)
* Space: O(n or m)
*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class MergeTwoSortedLists {
  
  /**
   * Solution 1: Use a dummy to connect smallerVal nodes from l1 or l2.
   * Time: O(n + m)
   * Space: O(1)
   */
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(-1);
    ListNode cur = dummy;
    while (l1 != null || l2 != null) {
      // if one of list is null, just connect to the other one, finished.
      if (l1 == null) {
        cur.next = l2;
        break;
      } else if (l2 == null) {
        cur.next = l1;
        break;
      }
      // compare 2 nodes
      if (l1.val <= l2.val) {
        cur.next = l1;
        l1 = l1.next;
      } else {
        cur.next = l2;
        l2 = l2.next;
      }
      cur = cur.next;
    }
    return dummy.next;
  }

  /**
   * Solution 2: Recursive, choose between 2 nodes, connect to the recursive rest, return choosedNode.
   * Time: O(n + m)
   * Space: O(n or m)
   */
  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    // base, if one Node is null, return the other to connect.
    if (l1 == null) {
      return l2;
    }
    if (l2 == null) {
      return l1;
    }
    // compare
    if (l1.val <= l2.val) {
      // choose l1 as cur
      // l1 is the currentNode, connect l1 to next choosing between l1.next and l2.
      l1.next = mergeTwoLists(l1.next, l2);
      return l1;
    } else {
      // l1 > l2. l2 as cur.
      // l2 is the currentNode, connect l2 to next choosing between l1 and l2.next.
      l2.next = mergeTwoLists(l1, l2.next);
      return l2;
    }
  }
  
}