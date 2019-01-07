

/**
Solution 1: Merge sort, merge layer by layer, m(0, n-1), m(1, n-2), m(2, n-3) etc.
Ex:
merge 2 list at a time, until 1 list left.
1->4->5
1->3->4
2->5
2->6;
merge: (1->4->5, 2->6) => 1->2->4->5->6;
1->2->4->5->6
1->3->4
2->5
merge: (1->3->4, 2->5) = 1->2->3->4->5;
1->2->4->5->6
1->2->3->4->5
merge: (1->2->4->5->6, 1->2->3->4->5) = 1->1->2->2->3->4->5->5->6;
1->1->2->2->3->4->5->5->6; = ANS;

How to Arrive:
* This problem builds on top of Merge2SortedLists.
**Algorithm**:
* While sizeOfList n > 1: (merge until only 1 list left)
	* startIdx = 0 (we merge to startList, remove endList)
	* endIdx = n-1;
	* While startIdx < endIdx: (merge 2 list util idxes meet)
		* Get the mergedList = merge(startList, endList);
		* Store/replace mergedList in lists[startIdx] (startList replaced);
		* Remove endList, decrease size n-1;
		* Update startIdx, endIdx for next merge.
* Return last one List left in lists. Everything is merged into 1st List, index0;
* Time: O(nk * lgN); n= sizeOfLists; k= list.len; 
	* lgN b/c list is halved every time.
* Space: O(1);

Solution 2: Use minheap to choose from cur choices of nodes.
Ex:
1->4->5
1->3->4
2->5
2->6;
Heap: 1-,1-,2-,2-,3-,4-,4-,5-,5-,6-
cur: 1->1->2->3->3->4->4->5->5->6  ANS;
1(->4->5) add 4 to heap.
1(->3->4) add 3 to heap.
2(->5) add 5 to heap
2(->6) add 6 to heap
3(->4) add 4 to heap
4(->5) add 5 to heap
4(->null) add nothing.
5(->null) add nothing.
5(->null) add nothing.
6(->null) add nothing.

How to Arrive:
* To merge k lists, we are picking the smallest unpicked node from all k lists (k nodes at a time).
* We can use minheap to pick the minNode from all choices.
* The initial choices are all 1st nodes from k lists. Add them to minheap. **Check if node is Null first**;
* We use a dummy and curNode to connect the nextMinNode.
* curNode connect to minheap.poll(), which is the nextMinNode.
* Now we need to update the choices, nextMinNode is picked, removed out of minheap, the next choice is nextMinNode.next, pickedList's next unpicked node.
* Check if nextMinNode.next == null, if not add it to minheap.
* Return dummy.next, all connected sorted reslist.

* Time: O(nlgk); k= # of lists, n = # of nodes.
	* we choose from at most k nodes at a time. minheap size k, logK to heapify. heapify n times.
* Space: O(n)
*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class MergeKSortedLists {

	/**
	 * Solution 2: Use minheap to choose from cur choices of nodes.
	 * Time: O(nlgk); k= # of lists, n = # of nodes.
	 	* we choose from at most k nodes at a time. minheap size k, logK to heapify. heapify n times.
	 * Space: O(n)
	 */
	public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) {
      return null;
    }
    // minheap, pick curNode from all lists' nextNode;
    // use minheap to pick the smallestVal from all choices.
    PriorityQueue<ListNode> minheap = new PriorityQueue<>(
        (node1, node2) -> {
          return node1.val - node2.val;
        }
    );
    // add 1st choices to minheap, (all lists' 1stNode);
    for (ListNode firstNode : lists) {
      // check empty list.
      if (firstNode != null) {
        minheap.add(firstNode);
      }
    }
    // Pick minNode from choices, add to resList.
    ListNode dummy = new ListNode(-1);
    ListNode cur = dummy;
    while (!minheap.isEmpty()) {
      ListNode nextMinNode = minheap.poll();
      cur.next = nextMinNode;
      cur = cur.next;
      // add nextMinNode picked its list incremented to nextMinNode.next,
      // add that to next choices
      if (nextMinNode.next != null) {
        minheap.add(nextMinNode.next);
      }
    }
    // return reslist.
    return dummy.next;
  }
  
  /**
   * Solution 1: Merge sort, merge layer by layer, m(0, n-1), m(1, n-2), m(2, n-3) etc.
   * Time: O(nk * lgk); k= sizeOfLists; n= # of nodes;
   * Space: O(1);
   */
  public ListNode mergeKLists(ListNode[] lists) {
    int k = lists.length;
    if (k == 0) {
      return null;
    }
    // merge until only 1 list left, n/2 each time, T(lgK)
    while (k > 1) {
      // 2 pointers.
      // always starting with 0 (1st index);
      int i = 0;
      // last index.
      int j = k - 1;
      // merge startList with endList, T(k) * mergeTime T(n), n = # nodes;
      while (i < j) {
        ListNode mergedList = merge(lists[i], lists[j]);
        // replace ithList with new mergedList.
        lists[i] = mergedList;
        // remove endList from lists. size array -1;
        k--;
        // next layer merge.
        i++;
        j--;
      }
    }
    // return last one List left in lists. Everything is merged into 1st List, index0;
    return lists[0];
  }
  
  public ListNode merge(ListNode l1, ListNode l2) {
    // base case
    if (l1 == null) {
      return l2;
    }
    if (l2 == null) {
      return l1;
    }
    // compare and assign curNode.
    if (l1.val <= l2.val) {
      // curNode is l1, connect l1 to nextNode choose from l1.next or l2.
      l1.next = merge(l1.next, l2);
      return l1;
    } else {
      // l2 < l1
      // curNode is l2, connect l2 to nextNode choose from l2.next or l1.
      l2.next = merge(l1, l2.next);
      return l2;
    }
  }
  
  
}