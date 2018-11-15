/**
Reverse Linked List II:
Reverse a linked list from position m to n.
Example
Given 1->2->3->4->5->NULL, m = 2 and n = 4, return 1->4->3->2->5->NULL.
*/

import java.util.*;
import java.io.*;

public class ReverseLinkedListII{

	public class ListNode {
	   	int val;
	    ListNode next;
	    ListNode(int val) {
	        this.val = val;
	        this.next = null;
	    }
	}
    /*
     * @param head: ListNode head is the head of the linked list 
     * @param m: An integer
     * @param n: An integer
     * @return: The head of the reversed ListNode
     * Solution: Isolates the sublist; ex: 1->2->[3->4->5]->6;
     	 reverse [5->4->3]; Then 2->[5->4->3]->6;
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode cur = head;
        ListNode before = null; //the node before reverse sublinkedlist
        ListNode after = null; //the node after reverse sublinkedlist
        int i = 1;
        //loop till node before m node; ex: 1->2->3(i=3)->[4(m=4)->5]
        while(i < m){ 
        	if(i == m-1){
        		before = cur;
        	}
        	cur = cur.next;
        	i++;
        }
        
        ListNode first = cur; //store first reverse node so we can assign its next to after
        ListNode next = null;
        ListNode prev = null;

        int j = m; // on first index of sublist
        //loop till reach out of reversed sublist; ex: 1->2->3->[4->5(n=5)]->6(j=6)
        while(j < n+1){
        	next = cur.next;
        	cur.next = prev;
        	prev = cur;
        	cur = next;
        	j++;
        }
        ListNode last = prev; // reversed head of sublist; ex: (4)->3->2->1->5->6
        after = cur; //cur is on the node after sublist
        if(before != null){
        	//prev is on last sublist node; ex: sublist=[5->4]; before=3; 3->[5->4];
        	before.next = prev; 
        }
        first.next = after; //ex: sublist=[5->4]; first=4; [5->4]->6;
        //if m=1 head is reversed to lastNode of sublist; 
        if(m != 1)
        	return head;
        else
        	return last;
    }

    public static void main(String [] args){
		ReverseLinkedListII obj = new ReverseLinkedListII();
		ReverseLinkedListII.ListNode node1 = new ReverseLinkedListII().new ListNode(1);
		ReverseLinkedListII.ListNode node2 = new ReverseLinkedListII().new ListNode(2);
		ReverseLinkedListII.ListNode node3 = new ReverseLinkedListII().new ListNode(3);
		ReverseLinkedListII.ListNode node4 = new ReverseLinkedListII().new ListNode(4);
		ReverseLinkedListII.ListNode node5 = new ReverseLinkedListII().new ListNode(5);
		ReverseLinkedListII.ListNode node6 = new ReverseLinkedListII().new ListNode(6);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;

		ReverseLinkedListII.ListNode cur = node1;
		while(cur != null){
			System.out.print(cur.val + "->");
			cur = cur.next;
		}
		System.out.println();
		
		cur = obj.reverseBetween(node1, 1, 4);
		while(cur != null){
			System.out.print(cur.val + "->");
			cur = cur.next;
		}
		System.out.println();
	}
}