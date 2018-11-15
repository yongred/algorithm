/**
Reverse Linked List:
Example:
For linked list 1->2->3, the reversed linked list is 3->2->1

*/

import java.util.*;
import java.io.*;

public class ReverseLinkedList {

	public class ListNode {
	   	int val;
	    ListNode next;
	    ListNode(int val) {
	        this.val = val;
	        this.next = null;
	    }
	}
    /*
     * @param head: n
     * @return: The new head of reversed linked list.
     * Solution: loop throught the list, and let next node point to current node
     * current node points to prev node.
     * ex: [1 -> 2 -> 3 -> Null] becomes [Null <- 1 <- 2 <- 3]
     * Time: O(n)
     */
    public ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode next = null;
        //loop through list 
        while(cur != null){
        	//store next in the loop
        	next = cur.next; 
        	cur.next = prev; //cur points to prev; ex: 2 -> 1
        	prev = cur; //prev iterate to cur position
        	cur = next; //iterate to next node
        }
        head = prev; //at the end prev is at last position (head), cur is null;
        return head;
    }

    public static void main(String [] args){
		ReverseLinkedList obj = new ReverseLinkedList();
		ReverseLinkedList.ListNode node1 = new ReverseLinkedList().new ListNode(1);
		ReverseLinkedList.ListNode node2 = new ReverseLinkedList().new ListNode(2);
		ReverseLinkedList.ListNode node3 = new ReverseLinkedList().new ListNode(3);
		ReverseLinkedList.ListNode node4 = new ReverseLinkedList().new ListNode(4);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;

		ReverseLinkedList.ListNode cur = node1;
		while(cur != null){
			System.out.print(cur.val + "->");
			cur = cur.next;
		}
		System.out.println();

		cur = obj.reverse(node1);
		while(cur != null){
			System.out.print(cur.val + "->");
			cur = cur.next;
		}
		System.out.println();
	}


}