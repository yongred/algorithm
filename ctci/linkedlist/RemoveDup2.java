
/**
2.1 Remove Dups: Write code to remove duplicates from an unsorted linked list.
FOLLOW UP
How would you solve this problem if a temporary buffer is not allowed?
Hints: #9, #40
*/
import java.util.*;
import linkedlistpackage.MyLinkedList;

public class RemoveDup2{

	public void deleteDup(MyLinkedList.Node head){
		MyLinkedList.Node cur = head;
		MyLinkedList.Node sentinal = head.getPrev();
		while(cur != sentinal){ // != sentinal
			MyLinkedList.Node runner = cur.getNext();
			while(runner != sentinal){
				if(runner.getData() == cur.getData()){
					//chain prev to next, delete cur
					runner.getPrev().setNext(runner.getNext());
					runner.getNext().setPrev(runner.getPrev());
				}
				runner = runner.getNext();
			}
			cur = cur.getNext();
		}
	}//end deleteDup
	public void printList(MyLinkedList.Node head){
		MyLinkedList.Node cur = head;
		while(cur != head.getPrev()){ //sentinal
			System.out.print(" " + cur.getData());
			cur = cur.getNext();	
		}
		System.out.println();
	}

	public static void main(String [] args){
		RemoveDup2 dupObj = new RemoveDup2();
		MyLinkedList obj = new MyLinkedList();
		obj.add(1);
		obj.add(2);
		obj.add(2);
		obj.add(4);
		obj.add(4);
		obj.add(5);
		obj.add(2);
		obj.add(2);
		MyLinkedList.Node sentinal = obj.getSentinal();
		dupObj.printList(sentinal.getNext()); //before deleteDup
		//after deleteDup
		dupObj.deleteDup(sentinal.getNext());
		dupObj.printList(sentinal.getNext());
	}
	
}