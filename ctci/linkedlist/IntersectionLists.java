/**
2.7 Intersection: Given two (singly) linked lists, determine if the two lists intersect. Return the intersecting
node. Note that the intersection is defined based on reference, not value. That is, if the kth
node of the first linked list is the exact same node (by reference) as the jth node of the second
linked list, then they are intersecting.
Hints: #20, #45, #55, #65, #76, #93, #111, #120, #129
*/

import linkedlistpackage.*;
import java.util.*;

public class IntersectionLists{

	public class LenTail{
		public int len = 0;
		public SinglyLinkedList.Node tail = null;
		public LenTail(int len, SinglyLinkedList.Node tail){
			this.len = len;
			this.tail = tail;
		}
	}

	// length of a list
	public LenTail getLenTail(SinglyLinkedList.Node node){
		int count = 1;
		while(node.getNext() != null){
			node = node.getNext();
			count++;
		}
		return new LenTail(count, node);
	}

	/*
	Solution: 
	-find the length of both lists;
	-advance the longer list by the difference in length.
	-iterates both list and compare each Node.

	Time: O(n); T(2n+m)
	*/

	public SinglyLinkedList.Node intersect(SinglyLinkedList.Node node1, SinglyLinkedList.Node node2){
		if(node1 == null || node2 == null)
			return null;
		LenTail lenTail1 = getLenTail(node1);
		LenTail lenTail2 = getLenTail(node2);
		//if last elements not equal, then no intersection
		if(lenTail1.tail != lenTail2.tail)
			return null;
		//move more elements one to next diff position, so 2 list start at same position
		if(lenTail1.len > lenTail2.len){
			int diff = lenTail1.len - lenTail2.len;
			for(int i = 0; i< diff; i++)
				node1 = node1.getNext();
		}
		if(lenTail2.len > lenTail1.len){
			int diff = lenTail2.len - lenTail1.len;
			for(int i = 0; i< diff; i++)
				node1 = node1.getNext();
		}

		while(node1 != null && node2 != null){
			//equal by reference
			System.out.println("node1: " + node1 + " node2: " + node2);
			if(node1 == node2)
				return node1;
			node1 = node1.getNext();
			node2 = node2.getNext();
			
		}

		return null;
	}//end of intersect

	public void printList(SinglyLinkedList.Node head){
		SinglyLinkedList.Node cur = head;
		//System.out.print(" " + cur.getData());
		while(cur != null){ //sentinal
			System.out.print(" " + cur.getData());
			cur = cur.getNext();	
			
		}
		System.out.println();
	}

	public static void main(String [] args){
			IntersectionLists obj = new IntersectionLists();
			SinglyLinkedList.Node list1 = new SinglyLinkedList().new Node(1);
			SinglyLinkedList.Node list2 = new SinglyLinkedList().new Node(4);
			SinglyLinkedList.Node cur = list1;
			for(int i = 2; i< 8; i++){
				SinglyLinkedList.Node newItem = new SinglyLinkedList().new Node(i);
				cur.setNext(newItem);
				if(i == 5)
					list2.setNext(newItem);
				cur = cur.getNext();
			}
			obj.printList(list1);
			obj.printList(list2);
			SinglyLinkedList.Node ans = obj.intersect(list1, list2);

			System.out.println("intersect at: " + ans.getData());

		}

	}//end of intersectionlist