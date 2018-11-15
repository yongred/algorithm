/**
2.2 Return Kth to Last: Implement an algorithm to find the kth to last element of a singly linked list.
Hints: #8, #25, #41, #67, # 126
*/

import java.util.*;
import linkedlistpackage.*;

public class KthToLast{

	//when you know the size, but too easy, interviewer not looking for that
	public Object knownSizeSolution(SinglyLinkedList list, int k){
		int moveNum = list.getSize() - k;
		SinglyLinkedList.Node cur = list.getHead();
		for(int i = 0; i< moveNum; i++){
			cur = cur.getNext();
		}
		return cur.getData();
 	}

 	//recursive solution
 	public int recursiveKthToLast(SinglyLinkedList.Node head, int k){
 		if(head == null) //reached end
 			return 0;	//index
 		//counting from the end of tail: recurse back
 		int index = recursiveKthToLast(head.getNext(), k) + 1;
 		if(index == k){
 			System.out.println("recursiveKthToLast: " + k + "th is " + head.
 				getData());
 		}
 		return index;
 	}

 	//counting size solution, loop twice
 	public Object countSizeKthToLast(SinglyLinkedList.Node head, int k){
 		if(head == null || k <= 0)
 			return null;
 		int size = 1;
 		SinglyLinkedList.Node cur = head;
 		while(cur.getNext() != null){
 			cur = cur.getNext();
 			size++;
 		}
 		int moveNum = size - k;
 		cur = head;
 		for(int i=0; i< moveNum; i++){
 			cur = cur.getNext();
 		}

 		return cur.getData();
 	}

 	//best optimized solution, two pointer
 	public Object runnerKthToLast(SinglyLinkedList.Node head, int k){
 		if(head == null || k <= 0){
 			return null;
 		}
 		//total move - (k-1) moves = num of moves needed
 		SinglyLinkedList.Node cur = head;
 		SinglyLinkedList.Node runner = head; //second pointer
 		for(int i = 0; i < k-1; i++){
 			//move k-1 moves, what is left is how many moves needed
 			if(cur == null) //if k is out of bounce
 				return null;
 			cur = cur.getNext();
	 	}
	 	//loop rest of the moves
 		while(cur.getNext() != null){ //when loop to tail, next is null
 			runner = runner.getNext();
 			cur = cur.getNext();
 		}
 		return runner.getData();
 	}


 	public static void main(String [] args){
 		KthToLast obj = new KthToLast();
 		SinglyLinkedList list = new SinglyLinkedList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		System.out.println("known size: 3rd is" + obj.knownSizeSolution(list, 3));
		obj.recursiveKthToLast(list.getHead(), 5);
		System.out.println("count size: 6th is" + obj.countSizeKthToLast(list.getHead(), 6));
		System.out.println("runner 2points: 4th is" + obj.runnerKthToLast(list.getHead(), 4));


 	}
}



