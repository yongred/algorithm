/**
Implement Queue with Limited Size of Array

Implement a queue with a number of arrays, in which each array has fixed size.
*/

/**
Solution 2: Connect List's last node -> next list's firstNode; ArrayLists with fixedSize
How to Arrive:
* We use fixedSize ArrayLists to connect to each other.
* The last index of a ArrayList is the nextList, so they are all connected as one list.
* FrontList: use to poll elms, FrontIndex as 1st elm in frontList.
	* When FrontIndex == fixedSize -1; we finished poll from cur frontList. FrontIndex is connect to nextList.
	* Update frontList to nextList; nextList = frontList.get(frontIndex);
* TailList: add elm to this list, TailIndex is index pos to add next elm.
	* When tailIndex = fixedSize -1; the current tailList is full;
	* We need a fixedSized newList to add next elm;
	* We create a newList, then connect curTailList to newList at lastIndex(the tailIndex now);
*/

import java.io.*;
import java.util.*;

public class ImplementQueueLimitedSizeArray2 {
	
	class FixedArrayQueue {
		// array size, block size.
		public int fixedSize;
		// queue size, elms count.
		public int count;
		// first elm of front poll from
		public int frontIndex;
		// last elm of tail add to
		public int tailIndex;
		// front list, poll from the first elm in frontList.
		public List<Object> frontList;
		// tail list, add last elm in last index here.
		public List<Object> tailList;

		public FixedArrayQueue(int fixedSize) {
			this.fixedSize = fixedSize;
			this.count = 0;
			this.frontIndex = 0;
			this.tailIndex = 0;
			// first list is the same for front and tail
			frontList = new ArrayList<>();
			tailList = frontList;
		}
		
		public boolean isEmpty() {
			return (count == 0);
		}

		public void add(int val) {
			// add to last, in tailList
			// check if this is lastNode in tailList
			if (tailIndex == fixedSize - 1) {
				// cur tailList is full, create new list.
				List<Object> newList = new ArrayList<>();
				newList.add(val);
				// connect tailList's lastIndex to newList.
				tailList.add(newList);
				// update new tailList
				tailList = newList;
				// cur elm index is 0 in newList;
				tailIndex = 0;
			} else {
				tailList.add(val);
			}
			// next open space, for next elm
			tailIndex++;
			// size increment
			count++;
		}

		public int poll() {
			if (isEmpty()) {
				return -1;
			}
			// get elm from frontList's first node
			int elm = (int) frontList.get(frontIndex);
			// front increment to next elm
			frontIndex++;
			// 1 less elm
			count--;
			// check if next is on nextList.
			if (frontIndex == fixedSize - 1) {
				// assign frontList to nextList, remove cur frontList.
				List<Object> nextList = (List<Object>) frontList.get(frontIndex);
				// delete clear cur frontList
				frontList.clear();
				frontList = nextList;
				// first elm in frontList.
				frontIndex = 0;
			}
			return elm;
		}

		public int peek() {
			if (isEmpty()) {
				return -1;
			}
			// get elm from frontList's first node
			int elm = (int) frontList.get(frontIndex);
			return elm;
		}
	}
	
	public static void main(String[] args) {
		ImplementQueueLimitedSizeArray2 obj = new ImplementQueueLimitedSizeArray2();
		ImplementQueueLimitedSizeArray2.FixedArrayQueue queue = obj.new FixedArrayQueue(5);
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