/**
2.S Sum Lists: You have two numbers represented by a linked list, where each node contains a single
digit. The digits are stored in reverse order, such that the 1 's digit is at the head of the list. Write a
function that adds the two numbers and returns the sum as a linked list.
EXAMPLE
Input: (7-) 1 -) 6) + (5 -) 9 -) 2) .Thatis,617 + 295.
Output: 2 -) 1 -) 9. That is, 912.
FOLLOW UP
Suppose the digits are stored in forward order. Repeat the above problem.
EXAMPLE
Input: (6 -) 1 -) 7) + (2 -) 9 -) 5).Thatis,617 + 295.
Output: 9 -) 1 -) 2. That is, 912.
Hints: #7, #30, #71, #95, #109
*/
import linkedlistpackage.*;
import java.util.*;

public class SumLists{

	public SinglyLinkedList.Node sumReverse(SinglyLinkedList.Node node1, SinglyLinkedList.Node node2){
		int carry = 0;
		SinglyLinkedList.Node ansNode = null;
		SinglyLinkedList.Node ansHead = null;
		while(node1 != null || node2 != null){
			int sum = (node1 == null ? 0 : (int)node1.getData()) 
					+ (node2 == null ? 0 : (int)node2.getData()) + carry;
			int digit = sum % 10; // 24 % 10 = 4 digit
			carry = sum / 10; // ex: 24 / 10 = 2 carry over
			SinglyLinkedList.Node newItem = new SinglyLinkedList().new Node(digit);
			if(ansHead == null){
				ansHead = newItem;
				ansNode = ansHead;
			}
			else{
				ansNode.setNext(newItem);
				ansNode = newItem;
			}
			if(node1 != null)
				node1 = node1.getNext();
			if(node2 != null)
				node2 = node2.getNext();
		}
		
		if(carry > 0){
			ansNode.setNext( new SinglyLinkedList().new Node(carry));
		}
		return ansHead;
	}//function sumReverse

	// adding directly: 9-0-8, + , 1-0-0, = 1008
	//for keeping recursive callback carry and node
	public class PartialSum{
		public SinglyLinkedList.Node listNode = null; //head digit of current sum
		public int carry = 0;
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
	//insert node to the head
	public SinglyLinkedList.Node insertBefore(SinglyLinkedList.Node head, int value){
		SinglyLinkedList.Node newItem = new SinglyLinkedList().new Node(value);
		if(head != null)
			newItem.setNext(head);
		return newItem;
	}
	//insert 0 nodes before head to make lists length equal
	public SinglyLinkedList.Node prependZeros(SinglyLinkedList.Node head, int numOfZeros){
		SinglyLinkedList.Node res = head;
		for(int i=0; i< numOfZeros; i++)
			res = insertBefore(res, 0);
		return res;
	}

	//direct sum helper, recursive storing sum nodes and carry
	public PartialSum sumDirectHelper(SinglyLinkedList.Node list1, SinglyLinkedList.Node list2){
		//base case
		if(list1 == null && list2 == null){
			return new PartialSum();
		}
		//recurse to the end, gets a new partialSum obj, then pass back carry and digit one by one
		PartialSum curSum = sumDirectHelper(list1.getNext(), list2.getNext());
		//calculate sum from cur 2 node digits and stored carry
		int sum = (int)list1.getData() + (int)list2.getData() + curSum.carry;
		int digit = sum % 10;
		int carry = sum / 10;
		//insert new digit node
		SinglyLinkedList.Node curRes = insertBefore(curSum.listNode, digit);
		//store carry and new digit node
		curSum.carry = carry;
		curSum.listNode = curRes;

		return curSum;
	} 
	//direct adding function
	public SinglyLinkedList.Node sumDirect(SinglyLinkedList.Node list1, SinglyLinkedList.Node list2){
		int len1 = length(list1);
		int len2 = length(list2);
		//prepends the zeros to make list == length first
		if(len1 > len2){
			int zeros = len1 - len2;
			list2 = prependZeros(list2, zeros);
		}else if(len2 > len1){
			int zeros = len2 - len1;
			list1= prependZeros(list1, zeros);
		}
		//stored sum nodes and carry
		PartialSum sum = sumDirectHelper(list1, list2);

		if(sum.carry > 0){ //one more digit for last carrry

			sum.listNode = insertBefore(sum.listNode, sum.carry);
		}
		return sum.listNode;
	}



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
		SumLists obj = new SumLists();
		SinglyLinkedList list1 = new SinglyLinkedList();
		SinglyLinkedList list2 = new SinglyLinkedList();
		list1.add(9);
		list1.add(9);
		list1.add(9);
		//list1.add(0);

		list2.add(3);
		list2.add(3);
		list2.add(9);
		list2.add(1);
		SinglyLinkedList.Node ans = obj.sumReverse(list1.getHead(), list2.getHead());

		obj.printList(ans);
		System.out.println("direct");
		ans = obj.sumDirect(list1.getHead(), list2.getHead());
		obj.printList(ans);

	}

}//end sumlists class