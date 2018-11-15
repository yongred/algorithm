/**
2.4 Partition: Write code to partition a linked list around a value x, such that all nodes less than x come
before all nodes greater than or equal to x. If x is contained within the list, the values of x only need
to be after the elements less than x (see below). The partition element x can appear anywhere in the
"right partition"; it does not need to appear between the left and right partitions.
EXAMPLE
Input: 3 -) 5 -) 8 -) 5 -) 10 -) 2 -) 1 [partition = 5]
Output: 3 -) 1 -) 2 -) 10 -) 5 -) 5 -) 8
Hints: #3, #24
*/
import linkedlistpackage.*;
import java.util.*;

public class PartitionList{
	public SinglyLinkedList.Node partition(SinglyLinkedList.Node head, int pivot){
		SinglyLinkedList.Node rightHead = null, leftHead  = null, leftTail = null;
		
		SinglyLinkedList.Node cur = head;
		while(cur != null){
			
			SinglyLinkedList.Node newNode = new SinglyLinkedList().new Node(cur.getData());
			//node < pivot goes to leftpart
			if((int)cur.getData() < pivot){
				// first element in leftpart
				if(leftHead == null){
					leftHead = newNode;
					leftTail = leftHead;
				}
				else if(leftHead.getNext() == null)
					leftHead.setNext(newNode);
				else
					leftTail.setNext(newNode);
				leftTail = newNode;
			}
			else{
				// first element in rightpart
				if(rightHead == null)
					rightHead = newNode;
				else{
					newNode.setNext(rightHead);
					rightHead = newNode;
				}
			}
			cur = cur.getNext();
		}
		//if not left part, everything >= pivot
		if(leftHead == null) 
			return rightHead;
		leftTail.setNext(rightHead); //join together
		return leftHead;
	}//end partition function

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
		PartitionList obj = new PartitionList();
		SinglyLinkedList list = new SinglyLinkedList();
		list.add(9);
		list.add(4);
		list.add(6);
		list.add(8);
		list.add(4);
		list.add(9);
		list.add(1);
		list.add(2);
		obj.printList(list.getHead());
		SinglyLinkedList.Node newHead = obj.partition(list.getHead(), 9);
		obj.printList(newHead);
	}
}//end partition list class