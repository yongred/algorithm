/**
Merge k Sorted Lists:
Merge k sorted linked lists and return it as one sorted list.
Analyze and describe its complexity.

Example
Given lists:

[
  2->4->null,
  null,
  -1->null
],
return -1->2->4->null.
*/

import java.util.*;
import java.io.*;

public class MergeKSortedLists{

	public class ListNode {
	   	int val;
	    ListNode next;
	    ListNode(int val) {
	        this.val = val;
	        this.next = null;
	    }
	}

    /**
     * @param lists: a list of ListNode
     * @return: The head of one sorted list.
     * Solution1: naive solution; use first list as base. Insert all other lists nodes into first list depending on their val.
     * Time: O(n^2)
     */
    public ListNode mergeKLists(List<ListNode> lists) {  
    	ListNode first = null;
    	//get first non null element
    	while(first == null && lists.size() > 0){
    		first = lists.remove(0); //get first list as base;
    	}
        
        for(ListNode head : lists){
        	ListNode cur = head;
        	ListNode next = null;
        	while(cur != null){
        		next = cur.next; //store to next, b/c cur changes in insert function.
        		first = insert(first, cur); //insert and return new head
        		cur = next; //iterates
        	}
        	
        }
        return first;
    }

    public ListNode insert(ListNode head, ListNode node){
    	ListNode cur = head;
    	ListNode prev = null;
    	//loop till (node > cur)
    	while(cur != null){
    		if(cur.val > node.val)
    			break;
    		prev = cur;
    		cur = cur.next;
    	}
    	//connect ex: 1->2(prev)->[3]->4(cur)
    	if(prev != null){
    		prev.next = node;
    	}
    	node.next = cur;
    	// return node if it is the new head (node < head)
    	return (head.val <= node.val) ? head : node;
    }

    /*
	Solution2: merge first on last list until only one list left;
	* ex: [1,2,5], [3,6] [7,11] => [1,2,5,7,11], [3,6] => [1,2,3,5,6,7,11];
	Time: O(nk Log k); k is the # lists, n is the total nodes.
	ex: if k=7 lists; 7 => 4 => 2 => 1; k => k/2 each time, so k Log(k);
    */
    public ListNode mergeKLists2(List<ListNode> lists){
    	if(lists.size() <= 0)
    		return null;
    	int last = lists.size()-1; //last index
    	//loop till only one list left
    	while(last > 0){
    		int i=0; //starting index
    		int j = last; //ending index
    		//merge right one to left one till they meet
    		while(i < j){
    			//merge lists[i] => lists[j];
    			ListNode mergedNode = merge(lists.get(i), lists.get(j));
    			lists.set(i, mergedNode);
    			i++;
    			j--;
    			//current merge finished; k => k/2; update new size;
    			if(i >= j)
    				last=j;
    		}
    	}
    	//everything merged to first list
    	return lists.get(0);
    }

    // merge 2 sorted lists
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
		MergeKSortedLists obj = new MergeKSortedLists();
		MergeKSortedLists.ListNode node1 = new MergeKSortedLists().new ListNode(1);
		MergeKSortedLists.ListNode node2 = new MergeKSortedLists().new ListNode(2);
		MergeKSortedLists.ListNode node3 = new MergeKSortedLists().new ListNode(3);
		MergeKSortedLists.ListNode node4 = new MergeKSortedLists().new ListNode(4);
		MergeKSortedLists.ListNode node5 = new MergeKSortedLists().new ListNode(5);
		MergeKSortedLists.ListNode node6 = new MergeKSortedLists().new ListNode(6);
		node2.next = node4;
		node4.next = node6;

		node3.next = node5;

		List<ListNode> lists = new ArrayList<ListNode>();
		lists.add(null);
		lists.add(node1);
		lists.add(node2);
		lists.add(node3);

		for(ListNode head : lists){
			ListNode cur = head;
			obj.printList(cur);
		}
		
		System.out.println("ANS----");
		ListNode cur1 = obj.mergeKLists2(lists);
		obj.printList(cur1);
	}
}

