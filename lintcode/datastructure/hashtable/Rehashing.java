
/**
129. Rehashing
The size of the hash table is not determinate at the very beginning. If the total size of keys is too large (e.g. size >= capacity / 10), we should double the size of the hash table and rehash every keys. Say you have a hash table looks like below:

size=3, capacity=4

[null, 21, 14, null]
       ↓    ↓
       9   null
       ↓
      null
The hash function is:

int hashcode(int key, int capacity) {
    return key % capacity;
}
here we have three numbers, 9, 14 and 21, where 21 and 9 share the same position as they all have the same hashcode 1 (21 % 4 = 9 % 4 = 1). We store them in the hash table by linked list.

rehashing this hash table, double the capacity, you will get:

size=3, capacity=8

index:   0    1    2    3     4    5    6   7
hash : [null, 9, null, null, null, 21, 14, null]
Given the original hash table, return the new hash table after rehashing .

Example
Given [null, 21->9->null, 14->null, null],

return [null, 9->null, null, null, null, 21->null, 14->null, null]

Notice
For negative integer in hash table, the position can be calculated as follow:

C++/Java: if you directly calculate -4 % 3 you will get -1. You can use function: a % b = (a % b + b) % b to make it is a non negative integer.
Python: you can directly use -1 % 3, you will get 2 automatically.

*/

import java.util.*;
import java.io.*;

public class Rehashing {
	public class ListNode {
		int val;
		ListNode next;
		ListNode(int x) {
		  val = x;
		  next = null;
		}
	}
    /**
     * @param hashTable: A list of The first node of linked list
     * @return: A list of The first node of linked list which have twice size
     * Solution:
     * Just follow the guided steps
     * time: O(N); space: O(N)
     */    
    public ListNode[] rehashing(ListNode[] hashTable) {
        int capacity = hashTable.length;
        ListNode[] newHash = new ListNode[capacity * 2];
        for(int i=0; i< capacity; i++)
        {
            ListNode curNode = hashTable[i];
            while(curNode != null)
            {
                int key = curNode.val;
                int newPos = hashcode(key, capacity * 2);
                ListNode newNode = new ListNode(curNode.val);
                if (newHash[newPos] == null)
                    newHash[newPos] = newNode;
                else
                {
                    ListNode curHead = newHash[newPos];
                    while(curHead.next != null)
                    {
                        curHead = curHead.next;
                    }
                    curHead.next = newNode;
                }
                curNode = curNode.next;
            }
        }
        return newHash;
    }
    //dealing with -#s, ex: -12 % 5 = (-12 % 5 + 5) % 5 = 2
    int hashcode(int key, int capacity) {
        return (key % capacity + capacity) % capacity;
    }
};
