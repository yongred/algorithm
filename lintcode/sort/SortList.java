/**
Sort List:
Sort a linked list in O(n log n) time using constant space complexity.
Example
Given 1->3->2->null, sort it to 1->2->3->null.

*/

import java.util.*;
import java.io.*;

public class SortList {
	public class ListNode {
	   	int val;
	    ListNode next;
	    ListNode(int val) {
	        this.val = val;
	        this.next = null;
	    }
	}

	 /*
     * @param head: The head of linked list.
     * @return: You should return the head of the sorted linked list, using constant space complexity.
     Solution1: using merge sort; find midNode, and make mid.next = null to slice 
     List into left and right; then merge;
     Time: O(n Lg n); Space: O(lgN);
     */

    public ListNode mergeSort(ListNode head){
    	if(head == null || head.next == null)
    		return head;
    	//find middle node O(lgN); n/2 => n/4 => n/8
    	ListNode mid = getMiddle(head);
    	//store midNext to use in rightpart
    	ListNode midNext = mid.next;
    	//mid.next to null so left side can end with null; slice list; ex: [2,4,5,3,1] => sort[2,4,5], sort[3,1]
    	mid.next = null;
    	ListNode left = mergeSort(head);
        ListNode right = mergeSort(midNext);
        return merge(left, right);
    }

    /*
	get middle node;

    */
    public ListNode getMiddle(ListNode head){
    	if(head == null)
    		return head;
    	ListNode cur = head;
        ListNode runner = head;
        //loop runner to last node; cur to mid node; T(n/2);
        while(runner.next != null){ 
        	//if even #; ex: [1,2,3,4,5,6] => i= 1->2->3; j= 1->3->5(->6);
        	//if odd #: ex: [1,2,3,4,5] => i= 1->2->3; j= 1->3->5;
        	if(runner.next.next != null){
        		runner = runner.next.next;
        		cur = cur.next;
        	}
        	else{ //one more element left
        		runner = runner.next;
        	}
        	
        }

        return cur;
    }

    /*
    merge 2 sorted lists 
    Time: O(n); Space: O(1)
    */
    public ListNode merge(ListNode list1, ListNode list2){
    	if(list1 == null)
    		return list2;
    	if(list2 == null)
    		return list1;
    	//head of the result for returning at the end
    	ListNode head = null;
    	if(list1.val <= list2.val){
    		head = list1;
    		list1 = list1.next;
    	}else{
    		head = list2;
    		list2 = list2.next;
    	}
		ListNode cur = head;

		//merge till one list is over iterating
    	while(list1 != null && list2 != null){
    		if(list1.val <= list2.val){
                cur.next = list1;
                list1 = list1.next;
            }else{
                cur.next = list2;
                list2 = list2.next;
            }

	    	cur = cur.next;
    	}

    	//merge the other list that has leftovers.
    	ListNode leftover = (list1 == null) ? list2 : list1;
    	cur.next = leftover;

    	return head;
    }

    public void printList(ListNode head){
    	ListNode cur1 = head;
		while(cur1 != null){
			System.out.print(cur1.val + "->");
			cur1 = cur1.next;
		}
		System.out.println();
    }


    public static void main(String [] args){
		SortList obj = new SortList();
		SortList.ListNode node1 = new SortList().new ListNode(1);
		SortList.ListNode node2 = new SortList().new ListNode(2);
		SortList.ListNode node3 = new SortList().new ListNode(3);
		SortList.ListNode node4 = new SortList().new ListNode(4);
		SortList.ListNode node5 = new SortList().new ListNode(5);
		SortList.ListNode node6 = new SortList().new ListNode(6);
		node2.next = node4;
		node4.next = node1;
		node1.next = node5;
		node5.next = node3;
		node3.next = node6;

		SortList.ListNode head = node2;
		obj.printList(head);
		
		System.out.println("ANS----");
		ListNode newhead = obj.mergeSort(head);
		obj.printList(newhead);
	}
}