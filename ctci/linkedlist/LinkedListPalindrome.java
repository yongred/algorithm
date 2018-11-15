/**
2.6 Palindrome: Implement a function to check if a linked list is a palindrome.
Hints: #5, #13, #29, #61, #101
*/

import linkedlistpackage.*;
import java.util.*;

public class LinkedListPalindrome{

	//compare the reversed first half of the list, to right half
	//32123
	public boolean isPalindrome(SinglyLinkedList.Node head){
		if(head == null)
			return false;
		if(head.getNext() == null)
			return true;

		//holding first half of reverse of the list, stack pop from last
		Stack tmpStack = new Stack(); 
		SinglyLinkedList.Node slow = head;
		SinglyLinkedList.Node runner = head;
		while(runner != null && runner.getNext() != null){
			tmpStack.push(slow.getData()); //first half
			
			runner = runner.getNext().getNext();
			slow = slow.getNext();
		}
		//has odd number of elements, even: [1]-2-[3]-4-[null],
		//odd: [1]-2-[3]-4-[5]
		if(runner != null){
			//dont need to compare the middle element
			slow = slow.getNext();
		}
		//loop again to check reverse, check right half to reversed left half
		while(slow != null){
			//System.out.println("tmpStack: " + tmpStack.peek());
			if(slow.getData() != tmpStack.pop())
				return false;
			slow = slow.getNext();
		}

		return true;
	}

	//recursive solution by calculating length
	//for keeping recursive callback node to compare and boolean isPalin
	public class PartList{
		public SinglyLinkedList.Node compareNode = null; 
		public boolean isPalin = true; //default to true
	}
	// length of a list
	public int length(SinglyLinkedList.Node node){
		int count = 0;
		while(node != null){
			node = node.getNext();
			count++;
		}
		return count;
	}
	//main solution function
	public boolean recurseIsPalin(SinglyLinkedList.Node head){
		if(head == null)
			return false;
		if(head.getNext() == null)
			return true;
		int len = length(head);
		PartList pl = isPalinHelper(head, len);
		return pl.isPalin;
	}
	//helper 
	public PartList isPalinHelper(SinglyLinkedList.Node cur, int len){
		
		if(len == 1){ //odd length middle is one node
			PartList pl = new PartList();
			// 2 -> [8] -> 6; 6 is the one for next compare, so (8)cur.next
			pl.compareNode = cur.getNext();
			return pl;
		}
		if(len == 0){ //even length -2 each time down to 0
			PartList pl = new PartList();
			// 2 -> [6]; 6 is for next compare, so cur
			pl.compareNode = cur;
			return pl;
		}
		//len -2, get rid of first and last, approaching middle
		PartList pl = isPalinHelper(cur.getNext(), len -2);
		if(pl.isPalin == false){ //no need to go furture
			return pl;
		}

		//System.out.println(pl.compareNode.getData() + " " + cur.getData());
		pl.isPalin =  ( (int)cur.getData() == (int)pl.compareNode.getData() );
		pl.compareNode = pl.compareNode.getNext();
		return pl;
	}

	public static void main(String [] args){
		LinkedListPalindrome obj = new LinkedListPalindrome();
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.add(9);
		list1.add(3);
		list1.add(1);
		list1.add(0);
		list1.add(3);
		list1.add(9);
		System.out.println(obj.isPalindrome(list1.getHead()));
		System.out.println(obj.recurseIsPalin(list1.getHead()));

	}
}