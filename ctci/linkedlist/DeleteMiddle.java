/**
2.3 Delete Middle Node: Implement an algorithm to delete a node in the middle (i.e., any node but the first and last node, not necessarily the exact middle) of a singly linked list, given only access to
that node.
EXAMPLE
Input: the node c from the linked list a - >b- >c - >d - >e- >f
Result: nothing is returned, but the new linked list looks like a - >b- >d - >e- >f
Hints: #72
*/
import linkedlistpackage.*;
import java.util.*;

public class DeleteMiddle{
	//make target node into next node
	public boolean deleteMiddle(SinglyLinkedList.Node target){
		if(target == null || target.getNext() == null){ //no next meaning its first or last
			return false;
		}
		SinglyLinkedList.Node frontNode = target.getNext();
		target.setData(frontNode.getData());
		target.setNext(frontNode.getNext());
		return true;
	}

	public void printList(SinglyLinkedList.Node head){
		SinglyLinkedList.Node cur = head;
		System.out.print(" " + cur.getData());
		while(cur.getNext() != null){
			cur = cur.getNext();
			System.out.print(" " + cur.getData());
		}
		System.out.println();
	}

	public static void main(String [] args){
		DeleteMiddle obj = new DeleteMiddle();
		SinglyLinkedList list = new SinglyLinkedList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		obj.printList(list.getHead());
		SinglyLinkedList.Node target = list.search(4);
		obj.deleteMiddle(target);
		obj.printList(list.getHead());
	}
}