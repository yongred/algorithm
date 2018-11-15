/**
2.8 Loop Detection: Given a circular linked list, implement an algorithm that returns the node at the
beginning of the loop.
DEFINITION
Circular linked list: A (corrupt) linked list in which a node's next pointer points to an earlier node, so
as to make a loop in the linked list.
EXAMPLE
Input: A -) B -) C -) 0 -) E - ) C [the same C as earlier ]
Output: C
Hints: #50, #69, #83, #90
*/
import linkedlistpackage.*;
import java.util.*;

/*
(b) (s)
 0-0-0-0-0
     |   |
     0-0-0
      (m)

 2dis(slow) = dis(fast); when meet
 slow = bs + sm;  fast = bs + sm + ms + sm;
 2(bs + sm) = bs + 2sm + ms;
 bs = ms;
*/
public class LoopDetection{

	public SinglyLinkedList.Node loopDetect(SinglyLinkedList.Node head){
		SinglyLinkedList.Node fast = head;
		SinglyLinkedList.Node slow = head;

		while(fast != null && fast.getNext() != null){
			//avoid compare head, str by next step
			fast = fast.getNext().getNext();
			slow = slow.getNext();
			if(fast == slow && fast != null)
				return slow;
		}
		return null;
	}

	public SinglyLinkedList.Node getLoopStr(SinglyLinkedList.Node head){
		if(head == null)
			return null;
		SinglyLinkedList.Node nodeMeet = loopDetect(head);
		if(nodeMeet == null) //no loop
			return null;
		SinglyLinkedList.Node cur = head;
		while(cur != null){
			if(cur == nodeMeet)
				return cur;
			cur = cur.getNext();
			nodeMeet = nodeMeet.getNext();
		}
		return null;
	}

	public static void main(String [] args){
		LoopDetection obj = new LoopDetection();
		SinglyLinkedList list1 = new SinglyLinkedList();
		for(int i=1; i<=8; i++){
			list1.add(i);
		}
		list1.search(8).setNext(list1.search(8));
		SinglyLinkedList.Node ans = obj.getLoopStr(list1.getHead());

		System.out.println(ans.getData());

	}
}