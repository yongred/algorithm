/**
Partition List:
Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.

You should preserve the original relative order of the nodes in each of the two partitions.

Example
Given 1->4->3->2->5->2->null and x = 3,
return 1->2->2->4->3->5->null.
*/


import java.util.*;
import java.io.*;

public class PartitionList {
	public class ListNode {
	   	int val;
	    ListNode next;
	    ListNode(int val) {
	        this.val = val;
	        this.next = null;
	    }
	}

    /*
     * @param head: The first node of linked list
     * @param x: An integer
     * @return: A ListNode
     * Solution: Have 2 lists to contains leftpart and rightpart;
     * node < pivot goes to left; node >= pivot goes to right;
     * join them at the end;
     * Time: O(n); Space: O(1);
     */
    public ListNode partition(ListNode head, int x) {
        ListNode leftcur = null;
        ListNode lefthead = null;
        ListNode rightcur = null;
        ListNode righthead = null;

        ListNode cur = head;
        while(cur != null){
        	//if < pivot; goes to leftpart
        	if(cur.val < x){
        		//first element; initialize head
        		if(lefthead == null){
        			leftcur = cur;
        			lefthead = leftcur;
        		}else{
        			//if initialized, not null, assign next and iterate
        			leftcur.next = cur;
        			leftcur = leftcur.next;
        		}

        	}else{ // >= pivot; goes to rightpart
        		//first element; initialize head
        		if(righthead == null){
        			rightcur = cur;
        			righthead = rightcur;
        		}else{
        			//if initialized, not null, assign next and iterate
        			rightcur.next = cur;
        			rightcur = rightcur.next;
        		}
        	}	
        	cur = cur.next;
        }
        //if no leftpart, return rightpart
        if(lefthead == null)
        	return righthead;
        //joint leftpart with rightpart
        leftcur.next = righthead;
        return lefthead;
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
		PartitionList obj = new PartitionList();
		PartitionList.ListNode node1 = new PartitionList().new ListNode(1);
		PartitionList.ListNode node2 = new PartitionList().new ListNode(2);
		PartitionList.ListNode node3 = new PartitionList().new ListNode(3);
		PartitionList.ListNode node4 = new PartitionList().new ListNode(4);
		PartitionList.ListNode node5 = new PartitionList().new ListNode(5);
		PartitionList.ListNode node6 = new PartitionList().new ListNode(6);
		node2.next = node4;
		node4.next = node1;
		node1.next = node5;
		node5.next = node3;
		node3.next = node6;

		PartitionList.ListNode head = node2;
		obj.printList(head);
		
		System.out.println("ANS----");
		ListNode newhead = obj.partition(head, 4);
		obj.printList(newhead);
	}
}