/**
Implement Queue with Limited Size of Array

Implement a queue with a number of arrays, in which each array has fixed size.
*/

/**
Solution 1: Use LinkedList, Create a Node class with int[fixedSize], keep a firstIndex
How to Arrive:
* Question ask for fixedSize arrays for a queue. So we want to populate these arrays 1 by 1;
* LinkedList/Deque is good for implementing a Queue, add to Last, poll from First.
* So we a LinkedList with ListNode class created.
* Add: O(1)
	* We add new elms to last Node of linkedlist.
	* Check if lastNode is fully filled, if yes, create a new ListNode with int[fixedSize]; And add elm to it.
	* For ListNode:
		* create an Add function, check if the elmsSize >= fixedSize. If not add array[size] = elm;
		* If FirstIndex = -1, means first elm, FirstIndex = 0;
		* size++, 1 more elm.
* Remove: O(1)
	* We elm from the first Node in linkedList.
	* If firstNode is empty after remove, linkedList removeFirst();
	* For ListNode:
		* RemoveFirst function, set array[firstIndex] = -1/null. firstIndex += 1, go to next elm (as the first);
		* size--, 1 less elm;
* 
*/

import java.io.*;
import java.util.*;

public class ImplementQueueLimitedSizeArray {

	class ListNode {
		// index of first added elm
		int first;
		// current elms count
		int size;
		int[] block;

		ListNode(int fixedSize) {
			block = new int[fixedSize];
			// use -1 as no elm
			Arrays.fill(block, -1);
			first = -1;
			size = 0;
		}

		public void add(int val) {
			// fixed size reached
			if (size >= block.length) {
				return;
			}
			// add next elm in next index, which is size. increment size
			block[size++] = val;
			// if first elm, firstIndex = 0;
			first = (size == 1) ? 0 : first;
		}

		public int getFirst() {
			if (size == 0) {
				return -1;
			} else {
				return block[first];
			}
		}

		public int removeFirst() {
			if (size == 0) {
				return -1;
			}
			// not empty
			int elm = block[first];
			// remove from the beginning, first = next
			block[first++] = -1;
			size--;
			return elm;
		}
	}
	
	class FixedArrayQueue {
		// array size, block size.
		public int fixedSize;
		// queue size, elms count.
		public int count;
		// linkedlist or deque to store all listNodes with int[]
		public LinkedList<ListNode> list = new LinkedList<>();

		public FixedArrayQueue(int fixedSize) {
			this.fixedSize = fixedSize;
			this.count = 0;
			list.add(new ListNode(fixedSize));
		}
		
		public boolean isEmpty() {
			return (count == 0);
		}

		public void add(int val) {
			// add to last/back
			// check last node array is full
			ListNode lastNode = list.getLast();
			if (lastNode.size >= fixedSize) {
				// full, no more space
				// create new ListNode
				ListNode newNode = new ListNode(fixedSize);
				newNode.add(val);
				list.addLast(newNode);
			} else {
				// not full yet
				lastNode.add(val);
			}
			count++;
		}

		public int poll() {
			// poll first/front
			// check isEmpty
			if (isEmpty()) {
				return -1;
			}
			// not empty
			ListNode firstNode = list.getFirst();
			// remove and get 1st elm in node's array
			int elm = firstNode.removeFirst();
			// check no elm left in node
			if (firstNode.size <= 0) {
				// remove firstNode
				list.removeFirst();
			}
			// decrement size
			count--;
			return elm;
		}

		public int peek() {
			// peek first/front
			if (isEmpty()) {
				return -1;
			}
			// not empty
			ListNode firstNode = list.getFirst();
			int elm = firstNode.getFirst();
			return elm;
		}
	}
	
	public static void main(String[] args) {
		ImplementQueueLimitedSizeArray obj = new ImplementQueueLimitedSizeArray();
		ImplementQueueLimitedSizeArray.FixedArrayQueue queue = obj.new FixedArrayQueue(5);
		queue.add(1);
		queue.add(2);
		queue.add(3);
		queue.add(4);
		queue.add(5);
		queue.add(6);
		while(!queue.isEmpty()) {
			System.out.println(queue.poll());
		}
	}

}